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
import com.example.ticketbookingapp.network.ApiClient;
import com.example.ticketbookingapp.network.ApiService;
import com.example.ticketbookingapp.network.dto.ApiMovie;
import com.example.ticketbookingapp.utils.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvMovies;
    private Button btnLogin;

    private final List<Movie> movies = new ArrayList<>();
    private final List<ApiMovie> apiMovies = new ArrayList<>();
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rvMovies = findViewById(R.id.rvMovies);
        btnLogin = findViewById(R.id.btnLogin);

        rvMovies.setLayoutManager(new GridLayoutManager(this, 2));
        int spacing = (int) (16 * getResources().getDisplayMetrics().density);
        rvMovies.addItemDecoration(new GridSpacingItemDecoration(2, spacing, false));

        adapter = new MovieAdapter(movies, apiMovies);
        rvMovies.setAdapter(adapter);

        btnLogin.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));

        fetchMovies();
    }

    private void fetchMovies() {
        ApiService api = ApiClient.getClient().create(ApiService.class);

        api.getMovies().enqueue(new Callback<List<ApiMovie>>() {
            @Override
            public void onResponse(Call<List<ApiMovie>> call, Response<List<ApiMovie>> response) {
                if (!response.isSuccessful() || response.body() == null) return;

                apiMovies.clear();
                apiMovies.addAll(response.body());

                movies.clear();
                for (ApiMovie m : apiMovies) {
                    movies.add(new Movie(m.getTitle(), m.getGenre(), m.getPosterUrl(), m.getRating()));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ApiMovie>> call, Throwable t) { }
        });
    }
}
