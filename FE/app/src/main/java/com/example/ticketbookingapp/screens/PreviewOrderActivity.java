package com.example.ticketbookingapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.models.BookingOrder;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

public class PreviewOrderActivity extends AppCompatActivity {
    private BookingOrder order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_order);

        // 1. Nhận dữ liệu từ Intent
        order = (BookingOrder) getIntent().getSerializableExtra("order_data");

        // 2. Ánh xạ View
        ImageView btnBack = findViewById(R.id.btnBackPreview);
        TextView txtMovie = findViewById(R.id.txtPrevMovie);
        TextView txtCinema = findViewById(R.id.txtPrevCinema);
        TextView txtSeats = findViewById(R.id.txtPrevSeats);
        TextView txtFood = findViewById(R.id.txtPrevFood);
        TextView txtTotal = findViewById(R.id.txtPrevTotal);
        MaterialButton btnPay = findViewById(R.id.btnPayMoMo);

        // 3. Xử lý nút Back (Quay lại FoodComboActivity)
        btnBack.setOnClickListener(v -> finish());

        // 4. Đổ dữ liệu
        if (order != null) {
            txtMovie.setText(order.movieName);
            txtCinema.setText(order.cinemaName);
            txtSeats.setText("Seats: " + order.seats);
            if (!order.foodSelected.isEmpty()) {
                txtFood.setText(order.foodSelected);
            }
            txtTotal.setText(order.totalAmount + " ₸");
        }

        // 5. Nút Thanh toán
        btnPay.setOnClickListener(v -> showPaymentDialog());
    }

    private void showPaymentDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_momo_payment, null);

        view.findViewById(R.id.btnConfirmPaid).setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(this, TicketActivity.class);
            intent.putExtra("order_data", order);
            // Xóa stack để không Back lại được trang thanh toán sau khi có vé
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        dialog.setContentView(view);
        dialog.show();
    }
}