package com.example.ticketbookingapp.screens;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.adapters.FoodAdapter;
import com.example.ticketbookingapp.models.BookingOrder;
import com.example.ticketbookingapp.models.Food;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;

public class FoodComboActivity extends AppCompatActivity {
    private BookingOrder order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_combo);

        order = (BookingOrder) getIntent().getSerializableExtra("order_data");
        MaterialButton btnNext = findViewById(R.id.btnNext);
        findViewById(R.id.btnBackFood).setOnClickListener(v -> finish());

        List<Food> foods = new ArrayList<>();
        foods.add(new Food("Solo Combo", "1 Popcorn + 1 Coke", 1500));
        foods.add(new Food("Couple Combo", "1 Popcorn + 2 Coke", 2500));

        RecyclerView rv = findViewById(R.id.recyclerFood);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new FoodAdapter(foods, item -> {
            order.totalAmount += item.price;
            order.foodSelected += (order.foodSelected.isEmpty() ? "" : ", ") + item.name;
            btnNext.setText("Continue • " + order.totalAmount + " ₸");
        }));

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(this, PreviewOrderActivity.class);
            intent.putExtra("order_data", order);
            startActivity(intent);
        });
    }
}