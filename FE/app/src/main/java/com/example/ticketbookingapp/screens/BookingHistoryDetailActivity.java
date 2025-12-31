package com.example.ticketbookingapp.screens;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ticketbookingapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.OutputStream;

public class BookingHistoryDetailActivity extends AppCompatActivity {

    private int bookingId;

    private TextView txtBooking;
    private ImageView imgQr;
    private MaterialButton btnSave, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        bookingId = getIntent().getIntExtra("bookingId", 0);

        txtBooking = findViewById(R.id.txtBooking);
        imgQr = findViewById(R.id.imgQr);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        txtBooking.setText("Booking #" + bookingId);

        // Generate QR
        Bitmap qr = generateQrBitmap("BOOKING_ID=" + bookingId, 700, 700);
        if (qr != null) imgQr.setImageBitmap(qr);

        btnBack.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            // chụp card trắng để lưu
            Bitmap cardBitmap = captureView(findViewById(R.id.ticketCard));
            if (cardBitmap == null) {
                Toast.makeText(this, "Không chụp được ảnh", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean ok = saveBitmapToGallery(cardBitmap, "booking_" + bookingId + "_" + System.currentTimeMillis() + ".png");
            Toast.makeText(this, ok ? "Đã lưu vào thư viện ảnh" : "Lưu thất bại", Toast.LENGTH_SHORT).show();
        });
    }

    // ===== QR =====
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

    // ===== Capture View =====
    private Bitmap captureView(android.view.View v) {
        try {
            Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            v.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ===== Save to Gallery (Android 10+ không cần permission) =====
    private boolean saveBitmapToGallery(Bitmap bitmap, String fileName) {
        try {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/TicketBookingApp");
                values.put(MediaStore.Images.Media.IS_PENDING, 1);
            }

            Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri == null) return false;

            OutputStream out = getContentResolver().openOutputStream(uri);
            if (out == null) return false;

            boolean ok = bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.clear();
                values.put(MediaStore.Images.Media.IS_PENDING, 0);
                getContentResolver().update(uri, values, null, null);
            }
            return ok;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
