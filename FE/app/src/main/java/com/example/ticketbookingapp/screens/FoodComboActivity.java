package com.example.ticketbookingapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.adapters.FoodAdapter;
import com.example.ticketbookingapp.models.BookingDraft;
import com.example.ticketbookingapp.models.Food;
import com.example.ticketbookingapp.models.FoodDraftItem;
import com.example.ticketbookingapp.network.ApiClient;
import com.example.ticketbookingapp.network.ApiService;
import com.example.ticketbookingapp.network.dto.FoodDto;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodComboActivity extends AppCompatActivity {

    private static final String EXTRA_BOOKING_DRAFT = "bookingDraft";

    private BookingDraft draft;
    private MaterialButton btnNext;
    private RecyclerView rv;

    private ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_combo);

        draft = (BookingDraft) getIntent().getSerializableExtra(EXTRA_BOOKING_DRAFT);
        if (draft == null) {
            finish();
            return;
        }
        if (draft.foods == null) draft.foods = new ArrayList<>();

        api = ApiClient.getClient().create(ApiService.class);

        btnNext = findViewById(R.id.btnNext);
        findViewById(R.id.btnBackFood).setOnClickListener(v -> finish());

        rv = findViewById(R.id.recyclerFood);
        rv.setLayoutManager(new LinearLayoutManager(this));

        updateNextButton();

        btnNext.setOnClickListener(v -> {
            Intent i = new Intent(FoodComboActivity.this, BookingPreviewActivity.class);
            i.putExtra(EXTRA_BOOKING_DRAFT, draft);
            startActivity(i);
        });

        loadFoodsFromBE();
    }

    private void updateNextButton() {
        btnNext.setText("Continue • " + draft.getGrandTotal() + " ₸");
    }

    private void loadFoodsFromBE() {
        api.getFoods().enqueue(new Callback<List<FoodDto>>() {
            @Override
            public void onResponse(Call<List<FoodDto>> call, Response<List<FoodDto>> res) {
                if (!res.isSuccessful() || res.body() == null) {
                    Toast.makeText(FoodComboActivity.this,
                            "Load foods failed: " + res.code(),
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Food> foods = new ArrayList<>();
                for (FoodDto dto : res.body()) {
                    Food f = new Food();
                    f.id = dto.foodId;
                    f.name = dto.foodName;
                    f.description = dto.foodType;

                    int price = (dto.unitPrice == null) ? 0 : (int) Math.round(dto.unitPrice);
                    f.price = price;

                    foods.add(f);
                }

                // ✅ SỬA: truyền đúng foodId vào draft
                rv.setAdapter(new FoodAdapter(foods, food -> {
                    addFoodToDraft(food.id, food.name, food.price);
                    updateNextButton();
                }));
            }

            @Override
            public void onFailure(Call<List<FoodDto>> call, Throwable t) {
                Toast.makeText(FoodComboActivity.this,
                        "Network error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ✅ add theo foodId để sau này gửi BE payload {foodId, quantity}
    private void addFoodToDraft(int foodId, String name, int unitPrice) {
        if (draft.foods == null) draft.foods = new ArrayList<>();
        if (foodId <= 0) return;
        if (name == null) return;

        // Nếu đã có món này rồi -> tăng quantity
        for (FoodDraftItem it : draft.foods) {
            if (it != null && it.foodId == foodId) {
                it.quantity += 1;
                draft.foodTotalPrice += unitPrice;
                return;
            }
        }

        // Nếu chưa có -> thêm mới
        draft.foods.add(new FoodDraftItem(foodId, name, unitPrice, 1));
        draft.foodTotalPrice += unitPrice;
    }


}
