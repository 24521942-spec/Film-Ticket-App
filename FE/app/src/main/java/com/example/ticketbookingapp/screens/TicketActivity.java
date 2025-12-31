package com.example.ticketbookingapp.screens;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.models.BookingDraft;
import com.example.ticketbookingapp.models.FoodDraftItem;
import com.example.ticketbookingapp.network.ApiClient;
import com.example.ticketbookingapp.network.ApiService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.OutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketActivity extends AppCompatActivity {

    private static final int REQ_WRITE_STORAGE = 900;

    private BookingDraft draft;
    private int bookingId;

    private View ticketCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        // ====== GET DATA ======
        draft = (BookingDraft) getIntent().getSerializableExtra("bookingDraft");
        bookingId = getIntent().getIntExtra("bookingId", 0);

        // ====== VIEW ======
        TextView txtMovie  = findViewById(R.id.txtFinalMovie);
        TextView txtCinema = findViewById(R.id.txtFinalCinema);
        TextView txtSeats  = findViewById(R.id.txtFinalSeats);
        TextView txtTotal  = findViewById(R.id.txtFinalTotal);
        ImageView imgQr    = findViewById(R.id.imgQr);

        Button btnSave     = findViewById(R.id.btnSaveTicket);
        Button btnHome     = findViewById(R.id.btnHome);

        ticketCardView = findViewById(R.id.ticketCard);

        // ====== DATA BIND ======
        if (draft == null) {
            txtMovie.setText("No ticket data");
            txtCinema.setText("");
            txtSeats.setText("");
            txtTotal.setText("");
        } else {

            // Movie
            txtMovie.setText(safe(draft.filmTitle));

            // Cinema • Room \n Date Time \n BookingId
            StringBuilder cinemaLine = new StringBuilder();

            if (!safe(draft.cinemaName).isEmpty())
                cinemaLine.append(draft.cinemaName);

            if (!safe(draft.roomName).isEmpty()) {
                if (cinemaLine.length() > 0) cinemaLine.append(" • ");
                cinemaLine.append(draft.roomName);
            }

            if (!safe(draft.dateText).isEmpty() || !safe(draft.timeText).isEmpty()) {
                cinemaLine.append("\n")
                        .append(safe(draft.dateText))
                        .append(" ")
                        .append(safe(draft.timeText));
            }

            if (bookingId > 0) {
                cinemaLine.append("\nBooking #").append(bookingId);
            }

            txtCinema.setText(cinemaLine.toString());

            // Seats + Foods
            String seatsText = buildSeatIdsText(draft.seatIds);
            String foodsText = buildFoodsText(draft.foods);

            txtSeats.setText(
                    "Seats: " + (seatsText.isEmpty() ? "None" : seatsText) +
                            "\nFoods: " + (foodsText.isEmpty() ? "None" : ("\n" + foodsText))
            );

            // Total
            txtTotal.setText("Total: " + draft.getGrandTotal() + " ₸ (Paid)");

            // ====== QR CODE ======
            String qrContent = "BOOKING_ID=" + bookingId;
            Bitmap qrBitmap = generateQrBitmap(qrContent, 700, 700);
            if (qrBitmap != null) {
                imgQr.setImageBitmap(qrBitmap);
            }
        }

        // ====== SAVE TICKET ======
        btnSave.setOnClickListener(v -> {
            if (ticketCardView == null) {
                Toast.makeText(this, "Cannot find ticket card view", Toast.LENGTH_SHORT).show();
                return;
            }

            if (needStoragePermission()) {
                requestStoragePermission();
            } else {
                saveTicketToGallery();
            }
        });

        // ====== HOME ======
        btnHome.setOnClickListener(v -> {
            if (bookingId > 0) {
                callPaySuccessAndGoHome(bookingId);
            } else {
                goHome();
            }
        });
    }

    // ================= QR =================
    private Bitmap generateQrBitmap(String content, int width, int height) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height);

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ================= SAVE IMAGE =================
    private boolean needStoragePermission() {
        // Android 10+ (API 29) dùng MediaStore không cần WRITE_EXTERNAL_STORAGE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) return false;

        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQ_WRITE_STORAGE
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_WRITE_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveTicketToGallery();
            } else {
                Toast.makeText(this, "Permission denied. Cannot save image.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveTicketToGallery() {
        // đảm bảo view đã vẽ xong
        ticketCardView.post(() -> {
            Bitmap bitmap = captureView(ticketCardView);
            if (bitmap == null) {
                Toast.makeText(this, "Cannot capture ticket", Toast.LENGTH_SHORT).show();
                return;
            }

            String fileName = "ticket_" + (bookingId > 0 ? bookingId : "draft") + "_" + System.currentTimeMillis() + ".png";

            try {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    values.put(MediaStore.Images.Media.RELATIVE_PATH,
                            Environment.DIRECTORY_PICTURES + "/TicketBookingApp");
                    values.put(MediaStore.Images.Media.IS_PENDING, 1);
                }

                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                if (uri == null) {
                    Toast.makeText(this, "Save failed", Toast.LENGTH_SHORT).show();
                    return;
                }

                OutputStream os = getContentResolver().openOutputStream(uri);
                if (os == null) {
                    Toast.makeText(this, "Save failed", Toast.LENGTH_SHORT).show();
                    return;
                }

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    values.clear();
                    values.put(MediaStore.Images.Media.IS_PENDING, 0);
                    getContentResolver().update(uri, values, null, null);
                }

                Toast.makeText(this, "Saved to Gallery ✅", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Save error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Bitmap captureView(View view) {
        if (view == null) return null;

        int width = view.getWidth();
        int height = view.getHeight();
        if (width <= 0 || height <= 0) return null;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    // ================= API =================
    private void callPaySuccessAndGoHome(int bookingId) {
        ApiService api = ApiClient.getClient().create(ApiService.class);
        api.paySuccess(bookingId).enqueue(new Callback<Void>() {
            @Override public void onResponse(Call<Void> call, Response<Void> response) {
                goHome();
            }
            @Override public void onFailure(Call<Void> call, Throwable t) {
                goHome();
            }
        });
    }

    private void goHome() {
        Intent i = new Intent(this, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    // ================= UTILS =================
    private String safe(String s) {
        return s == null ? "" : s.trim();
    }

    private String buildSeatIdsText(ArrayList<Integer> seatIds) {
        if (seatIds == null || seatIds.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (Integer id : seatIds) {
            if (id == null) continue;
            if (sb.length() > 0) sb.append(", ");
            sb.append(id);
        }
        return sb.toString();
    }

    private String buildFoodsText(ArrayList<FoodDraftItem> foods) {
        if (foods == null || foods.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (FoodDraftItem f : foods) {
            if (f == null || f.quantity <= 0) continue;

            String name = safe(f.name);
            if (name.isEmpty()) continue;

            if (sb.length() > 0) sb.append("\n");
            sb.append("- ").append(name).append(" x").append(f.quantity);
        }
        return sb.toString();
    }
}
