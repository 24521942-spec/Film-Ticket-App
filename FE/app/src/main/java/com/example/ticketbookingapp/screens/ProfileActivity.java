package com.example.ticketbookingapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.adapters.TicketHistoryAdapter;
import com.example.ticketbookingapp.network.ApiClient;
import com.example.ticketbookingapp.network.ApiService;
import com.example.ticketbookingapp.network.dto.TicketHistoryDto;
import com.example.ticketbookingapp.network.dto.UserProfileDto;
import com.example.ticketbookingapp.utils.SessionManager;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    // ✅ bỏ tvId nếu XML không có
    private TextView tvUsername, tvEmail;
    private MaterialButton btnLogout;
    private ImageView btnBack;

    private RecyclerView rvHistory;
    private TextView tvEmptyHistory;
    private View progressHistory;

    private TicketHistoryAdapter historyAdapter;

    private SessionManager session;
    private ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        session = new SessionManager(this);
        api = ApiClient.getClient(this).create(ApiService.class);

        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);

        btnLogout = findViewById(R.id.btnLogout);
        btnBack = findViewById(R.id.btnBack);

        rvHistory = findViewById(R.id.rvTicketHistory);
        tvEmptyHistory = findViewById(R.id.tvEmptyHistory);
        progressHistory = findViewById(R.id.progressHistory);

        // ✅ Adapter có click -> chỉ mở TicketActivity theo bookingId
        historyAdapter = new TicketHistoryAdapter(item -> {
            Intent i = new Intent(ProfileActivity.this, BookingHistoryDetailActivity.class);
            i.putExtra("bookingId", item.getBookingId());
            i.putExtra("qrCode", item.getQrCode());   // ✅ thêm dòng này
            startActivity(i);
        });


        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        rvHistory.setAdapter(historyAdapter);

        btnBack.setOnClickListener(v -> finish());

        btnLogout.setOnClickListener(v -> {
            session.clear();
            Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
            finish();
        });

        int userId = session.getUserId();
        if (userId == -1) {
            Toast.makeText(this, "Bạn chưa đăng nhập", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadProfile(userId);
        loadTicketHistory(userId);
    }

    private void loadProfile(int userId) {
        api.getUserProfile(userId).enqueue(new Callback<UserProfileDto>() {
            @Override
            public void onResponse(Call<UserProfileDto> call, Response<UserProfileDto> res) {
                if (!res.isSuccessful() || res.body() == null) {
                    Toast.makeText(ProfileActivity.this,
                            "Không tải được profile (" + res.code() + ")",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                UserProfileDto u = res.body();
                tvUsername.setText("Username: " + u.getUsername());
                tvEmail.setText("Email: " + u.getEmail());

            }

            @Override
            public void onFailure(Call<UserProfileDto> call, Throwable t) {
                Toast.makeText(ProfileActivity.this,
                        "Lỗi tải profile: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadTicketHistory(int userId) {
        progressHistory.setVisibility(View.VISIBLE);
        tvEmptyHistory.setVisibility(View.GONE);

        api.getTicketHistory(userId).enqueue(new Callback<List<TicketHistoryDto>>() {
            @Override
            public void onResponse(Call<List<TicketHistoryDto>> call, Response<List<TicketHistoryDto>> res) {
                progressHistory.setVisibility(View.GONE);

                if (!res.isSuccessful()) {
                    showHistoryError("Không tải được lịch sử vé (" + res.code() + ")");
                    historyAdapter.setData(null);
                    return;
                }

                List<TicketHistoryDto> data = res.body();
                Log.d("ProfileActivity", "history size=" + (data == null ? 0 : data.size()));

                if (data == null || data.isEmpty()) {
                    tvEmptyHistory.setVisibility(View.VISIBLE);
                    tvEmptyHistory.setText("Chưa có vé nào.");
                    historyAdapter.setData(null);
                } else {
                    tvEmptyHistory.setVisibility(View.GONE);
                    historyAdapter.setData(data);
                }
            }

            @Override
            public void onFailure(Call<List<TicketHistoryDto>> call, Throwable t) {
                progressHistory.setVisibility(View.GONE);
                showHistoryError("Lỗi: " + t.getMessage());
                historyAdapter.setData(null);
            }
        });
    }

    // ✅ CHỈ MỞ QR
    private void openTicketQrOnly(int bookingId) {
        Intent i = new Intent(ProfileActivity.this, BookingHistoryDetailActivity.class);
        i.putExtra("bookingId", bookingId);
        startActivity(i);
    }


    private void showHistoryError(String msg) {
        tvEmptyHistory.setVisibility(View.VISIBLE);
        tvEmptyHistory.setText(msg);
    }
}
