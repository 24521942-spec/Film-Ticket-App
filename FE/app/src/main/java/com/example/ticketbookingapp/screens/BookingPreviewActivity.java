package com.example.ticketbookingapp.screens;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.models.BookingDraft;
import com.example.ticketbookingapp.models.FoodDraftItem;
import com.example.ticketbookingapp.network.ApiClient;
import com.example.ticketbookingapp.network.ApiService;
import com.example.ticketbookingapp.network.dto.FakeBookingRequestDto;
import com.example.ticketbookingapp.network.dto.FakeBookingResponse;
import com.example.ticketbookingapp.utils.SessionManager;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingPreviewActivity extends AppCompatActivity {

    private static final String EXTRA_BOOKING_DRAFT = "bookingDraft";

    private BookingDraft draft;
    private ApiService api;

    private TextView txtPrevMovie, txtPrevCinema, txtPrevSeats, txtPrevFood, txtPrevTotal;
    private MaterialButton btnPayMoMo;

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_order);

        session = new SessionManager(this);

        // Retrofit (để interceptor attach token nếu có)
        api = ApiClient.getClient(this).create(ApiService.class);

        draft = (BookingDraft) getIntent().getSerializableExtra(EXTRA_BOOKING_DRAFT);
        if (draft == null) {
            finish();
            return;
        }

        bindViews();
        findViewById(R.id.btnBackPreview).setOnClickListener(v -> finish());

        bindData();

        // Pay -> gọi fake booking -> lấy bookingId + qrUrl -> show QR
        btnPayMoMo.setOnClickListener(v -> onClickPayFake());
    }

    private void bindViews() {
        txtPrevMovie  = findViewById(R.id.txtPrevMovie);
        txtPrevCinema = findViewById(R.id.txtPrevCinema);
        txtPrevSeats  = findViewById(R.id.txtPrevSeats);
        txtPrevFood   = findViewById(R.id.txtPrevFood);
        txtPrevTotal  = findViewById(R.id.txtPrevTotal);
        btnPayMoMo    = findViewById(R.id.btnPayMoMo);
    }

    private void bindData() {
        // Movie
        String movieLine = safe(draft.filmTitle);
        txtPrevMovie.setText(movieLine.isEmpty() ? "Movie" : movieLine);

        // Cinema + Room + DateTime
        String cinema = safe(draft.cinemaName);
        String room   = safe(draft.roomName);
        String date   = safe(draft.dateText);
        String time   = safe(draft.timeText);

        StringBuilder cinemaLine = new StringBuilder();
        if (!cinema.isEmpty()) cinemaLine.append(cinema);
        if (!room.isEmpty()) {
            if (cinemaLine.length() > 0) cinemaLine.append(" • ");
            cinemaLine.append(room);
        }
        if (!date.isEmpty() || !time.isEmpty()) {
            if (cinemaLine.length() > 0) cinemaLine.append("\n");
            cinemaLine.append(date);
            if (!time.isEmpty()) cinemaLine.append(" ").append(time);
        }
        txtPrevCinema.setText(cinemaLine.length() == 0 ? "Cinema" : cinemaLine.toString());

        // Seats + seat total
        int seatCount = (draft.seatIds == null) ? 0 : draft.seatIds.size();
        int seatPrice = draft.seatTotalPrice;

        String seatsText = buildSeatIdsText(draft.seatIds);
        String seatsLine = "Seats (" + seatCount + "): " + (seatsText.isEmpty() ? "None" : seatsText)
                + "\nSeat total: " + seatPrice + " ₸";
        txtPrevSeats.setText(seatsLine);

        // Foods
        String foodsLine = buildFoodsText(draft.foods);
        if (foodsLine.isEmpty()) foodsLine = "None";
        txtPrevFood.setText(foodsLine);

        // Total
        int total = draft.getGrandTotal();
        txtPrevTotal.setText("Total: " + total + " ₸");
        btnPayMoMo.setText("Pay with MoMo QR • " + total + " ₸");
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
        for (FoodDraftItem it : foods) {
            if (it == null) continue;
            String name = safe(it.name);
            if (name.isEmpty()) continue;
            if (it.quantity <= 0) continue;

            if (sb.length() > 0) sb.append("\n");
            sb.append(name).append(" x").append(it.quantity);
        }
        return sb.toString();
    }

    private String safe(String s) {
        return s == null ? "" : s.trim();
    }

    /**
     * Flow FAKE:
     * 1) POST /api/bookings/fake (có body) -> BE trả { bookingId, qrUrl }
     * 2) Show dialog QR bằng qrUrl
     */
    private void onClickPayFake() {
        int userId = getUserIdFromSession();
        if (userId <= 0) {
            Toast.makeText(this, "Bạn chưa đăng nhập!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate tối thiểu
        if (draft.showtimeId <= 0) {
            Toast.makeText(this, "showtimeId không hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (draft.seatIds == null || draft.seatIds.isEmpty()) {
            Toast.makeText(this, "Bạn chưa chọn ghế!", Toast.LENGTH_SHORT).show();
            return;
        }

        btnPayMoMo.setEnabled(false);
        btnPayMoMo.setText("Processing...");

        // ✅ Build body gửi BE
        FakeBookingRequestDto req = new FakeBookingRequestDto();
        req.userId = userId;
        req.showtimeId = draft.showtimeId;
        req.seatIds = new ArrayList<>(draft.seatIds);

        // foods: chỉ add món có qty > 0
        if (draft.foods != null) {
            for (FoodDraftItem it : draft.foods) {
                if (it == null) continue;
                if (it.quantity <= 0) continue;

                // ⚠️ FoodDraftItem của bạn cần có foodId để gửi lên BE.
                // Nếu field không phải it.foodId, bạn đổi lại cho đúng tên field.
                req.foods.add(new FakeBookingRequestDto.FoodItem(it.foodId, it.quantity));
            }
        }

        api.createFakeBooking(req).enqueue(new Callback<FakeBookingResponse>() {
            @Override
            public void onResponse(Call<FakeBookingResponse> call, Response<FakeBookingResponse> res) {
                resetPayButton();

                if (!res.isSuccessful() || res.body() == null) {
                    Toast.makeText(BookingPreviewActivity.this,
                            "Fake booking fail: " + res.code(),
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                int bookingId = res.body().bookingId;
                String qrUrl = res.body().qrUrl;

                if (bookingId <= 0) {
                    Toast.makeText(BookingPreviewActivity.this,
                            "bookingId fake không hợp lệ",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (qrUrl == null || qrUrl.trim().isEmpty()) {
                    Toast.makeText(BookingPreviewActivity.this,
                            "BE không trả qrUrl (link ảnh QR)",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                showQrDialogFake(bookingId, qrUrl);
            }

            @Override
            public void onFailure(Call<FakeBookingResponse> call, Throwable t) {
                resetPayButton();
                Toast.makeText(BookingPreviewActivity.this,
                        "Network error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getUserIdFromSession() {
        try {
            return session.getUserId();
        } catch (Exception e) {
            return 0;
        }
    }

    private void resetPayButton() {
        int total = draft.getGrandTotal();
        btnPayMoMo.setEnabled(true);
        btnPayMoMo.setText("Pay with MoMo QR • " + total + " ₸");
    }

    private void showQrDialogFake(int bookingId, String qrUrl) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_momo_payment);
        dialog.setCancelable(true);

        ImageView ivQr = dialog.findViewById(R.id.ivQrMomo);
        android.widget.Button btnConfirm = dialog.findViewById(R.id.btnConfirmPaid);

        Glide.with(this)
                .load(qrUrl)
                .error(R.drawable.ic_launcher_foreground)
                .into(ivQr);

        btnConfirm.setOnClickListener(v -> {
            dialog.dismiss();

            Intent i = new Intent(this, TicketActivity.class);
            i.putExtra("bookingDraft", draft);
            i.putExtra("bookingId", bookingId);
            startActivity(i);
            finish();
        });

        dialog.show();
    }
}
