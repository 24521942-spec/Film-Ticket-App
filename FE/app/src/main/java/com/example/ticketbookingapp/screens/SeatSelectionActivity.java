package com.example.ticketbookingapp.screens;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        btnBuy = findViewById(R.id.btnBuy);
        recyclerSeats = findViewById(R.id.recyclerSeats);

        updateTotalPrice(0);

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

                totalPrice -= 2200;
                if (totalPrice < 0) totalPrice = 0;

                updateTotalPrice(totalPrice);
                adapter.notifyDataSetChanged();
            }
        });

        recyclerSeats.setAdapter(adapter);

        btnBuy.setOnClickListener(v -> {
            if (totalPrice > 0) {
                Toast.makeText(this, "Payment successful: " + totalPrice + " ₸", Toast.LENGTH_LONG).show();
                finish(); // Đóng màn hình chọn ghế, quay lại màn hình phim
            } else {
                Toast.makeText(this, "Please select at least one seat", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateTotalPrice(int price) {
        btnBuy.setText("Pay for tickets • " + price + " ₸");
    }
}