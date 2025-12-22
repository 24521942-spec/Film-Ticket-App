package com.example.ticketbookingapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.adapters.MovieAdapter;
import com.example.ticketbookingapp.models.Movie;
import com.example.ticketbookingapp.utils.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView rvMovies;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 1. Ánh xạ View
        rvMovies = findViewById(R.id.rvMovies);
        btnLogin = findViewById(R.id.btnLogin);

        // 2. Thiết lập RecyclerView Grid 2 cột
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        rvMovies.setLayoutManager(manager);

        // 3. Thêm khoảng cách giữa các item (16dp)
        int spacing = (int) (16 * getResources().getDisplayMetrics().density);
        rvMovies.addItemDecoration(new GridSpacingItemDecoration(2, spacing, false));

        // 4. Tạo dữ liệu giả
        List<Movie> demo = new ArrayList<>();
        demo.add(new Movie("The Batman", "Action", "https://example.com/poster1.jpg", 8.1f));
        demo.add(new Movie("Uncharted", "Adventure", "https://example.com/poster2.jpg", 7.9f));
        demo.add(new Movie("Spider-Man", "Action", "", 8.5f));
        demo.add(new Movie("Doctor Strange", "Sci-Fi", "", 7.5f));
        // Thêm các phim khác vào đây...

        // 5. Gắn Adapter
        MovieAdapter adapter = new MovieAdapter(demo);
        rvMovies.setAdapter(adapter);

        // 6. Xử lý nút Login
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}