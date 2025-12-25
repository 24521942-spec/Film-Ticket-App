package com.example.ticketbookingapp.screens;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.models.BookingOrder;

public class TicketActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        BookingOrder order = (BookingOrder) getIntent().getSerializableExtra("order_data");

        TextView txtMovie = findViewById(R.id.txtFinalMovie);
        TextView txtCinema = findViewById(R.id.txtFinalCinema);
        TextView txtSeats = findViewById(R.id.txtFinalSeats);
        TextView txtTotal = findViewById(R.id.txtFinalTotal);
        Button btnHome = findViewById(R.id.btnHome);

        if (order != null) {
            txtMovie.setText(order.movieName);
            txtCinema.setText(order.cinemaName);
            txtSeats.setText("Seats: " + order.seats + "\nFoods: " + order.foodSelected);
            txtTotal.setText("Total: " + order.totalAmount + " ₸ (Paid)");
        }

        btnHome.setOnClickListener(v -> finish());
    }
}