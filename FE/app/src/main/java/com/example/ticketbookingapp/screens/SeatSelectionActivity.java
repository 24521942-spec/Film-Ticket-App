package com.example.ticketbookingapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.adapters.SeatAdapter;
import com.example.ticketbookingapp.models.Seat;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class SeatSelectionActivity extends AppCompatActivity {
    private RecyclerView recyclerSeats;
    private SeatAdapter adapter;
    private List<Seat> seatList;
    private int totalPrice = 0;
    private MaterialButton btnBuy;
    private ImageButton btnBack; // Thêm nút back

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        // 1. Ánh xạ View
        btnBuy = findViewById(R.id.btnBuy);
        btnBack = findViewById(R.id.btnBack); // Đảm bảo ID này trùng với XML
        recyclerSeats = findViewById(R.id.recyclerSeats);

        // 2. Sự kiện nút Back
        btnBack.setOnClickListener(v -> finish());

        updateTotalPrice(0);

        // Khởi tạo dữ liệu ghế
        seatList = new ArrayList<>();
        for (int i = 1; i <= 64; i++) {
            int status = (Math.random() > 0.8) ? 1 : 0;
            seatList.add(new Seat(String.valueOf(i), status));
        }

        recyclerSeats.setLayoutManager(new GridLayoutManager(this, 8));

        adapter = new SeatAdapter(seatList, seat -> {
            if (seat.getStatus() == 0) {
                TicketTypeBottomSheet sheet = new TicketTypeBottomSheet(seat.getId(), (type, price) -> {
                    seat.setStatus(2);
                    totalPrice += price;
                    updateTotalPrice(totalPrice);
                    adapter.notifyDataSetChanged();
                });
                sheet.show(getSupportFragmentManager(), "TicketType");

            } else if (seat.getStatus() == 2) {
                seat.setStatus(0);
                // Lưu ý: Logic trừ tiền cần khớp với loại vé đã chọn,
                // ở đây tạm trừ giá cố định hoặc bạn cần lưu giá vào đối tượng Seat
                totalPrice -= 2200;
                if (totalPrice < 0) totalPrice = 0;
                updateTotalPrice(totalPrice);
                adapter.notifyDataSetChanged();
            }
        });

        recyclerSeats.setAdapter(adapter);

        // 3. Sự kiện nút Thanh toán (Chuyển sang màn hình Bắp nước/Preview)

    }

    private void updateTotalPrice(int price) {
        btnBuy.setText("Pay for tickets • " + price + " ₸");
    }
}