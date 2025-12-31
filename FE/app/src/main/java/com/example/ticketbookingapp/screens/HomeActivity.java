package com.example.ticketbookingapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.adapters.MovieAdapter;
import com.example.ticketbookingapp.models.Movie;
import com.example.ticketbookingapp.network.ApiClient;
import com.example.ticketbookingapp.network.ApiService;
import com.example.ticketbookingapp.network.dto.FilmDto;
import com.example.ticketbookingapp.utils.GridSpacingItemDecoration;
import com.example.ticketbookingapp.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvMovies;
    private Button btnLogin;
    private ImageView imgAvatar;

    private MovieAdapter adapter;
    private ApiService api;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        session = new SessionManager(this);

        rvMovies = findViewById(R.id.rvMovies);
        btnLogin = findViewById(R.id.btnLogin);
        imgAvatar = findViewById(R.id.imgAvatar);

        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        });

        imgAvatar.setOnClickListener(v -> {
            // nếu muốn chặt chẽ:
            if (!session.isLoggedIn()) {
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                return;
            }
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
        });

        GridLayoutManager manager = new GridLayoutManager(this, 2);
        rvMovies.setLayoutManager(manager);

        int spacing = (int) (14 * getResources().getDisplayMetrics().density);
        rvMovies.addItemDecoration(new GridSpacingItemDecoration(2, spacing, true));

        adapter = new MovieAdapter(this, movie -> {
            Intent i = new Intent(HomeActivity.this, MovieDetailActivity.class);
            i.putExtra(MovieDetailActivity.EXTRA_FILM_ID, movie.getFilmId());
            i.putExtra(MovieDetailActivity.EXTRA_TITLE, movie.getTitle());
            i.putExtra(MovieDetailActivity.EXTRA_GENRE, movie.getGenre());
            i.putExtra(MovieDetailActivity.EXTRA_POSTER, movie.getPosterUrl());
            i.putExtra(MovieDetailActivity.EXTRA_RATING, movie.getRating());
            startActivity(i);
        });
        rvMovies.setAdapter(adapter);

        api = ApiClient.getClient(this).create(ApiService.class);

        updateLoginUI();
        loadMovies();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLoginUI();
    }

    private void updateLoginUI() {
        if (session.isLoggedIn()) {
            btnLogin.setVisibility(View.GONE);
            imgAvatar.setVisibility(View.VISIBLE);
        } else {
            btnLogin.setVisibility(View.VISIBLE);
            imgAvatar.setVisibility(View.GONE);
        }
    }

    private void loadMovies() {
        api.getAllFilms().enqueue(new Callback<List<FilmDto>>() {
            @Override
            public void onResponse(Call<List<FilmDto>> call, Response<List<FilmDto>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(HomeActivity.this, "API lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("HomeActivity", "Response error: " + response.code());
                    return;
                }

                List<FilmDto> dtos = response.body();
                if (dtos == null) dtos = new ArrayList<>();

                List<Movie> movies = new ArrayList<>();
                for (FilmDto f : dtos) {
                    Integer filmId = f.getFilmId();
                    String title = f.getTitle() != null ? f.getTitle() : "";
                    String genre = f.getGenre() != null ? f.getGenre() : "";
                    String posterUrl = f.getPosterUrl() != null ? f.getPosterUrl() : "";
                    float rating = (f.getRating() != null) ? f.getRating() : 0f;

                    movies.add(new Movie(filmId, title, genre, posterUrl, rating));
                }

                adapter.setData(movies);
            }

            @Override
            public void onFailure(Call<List<FilmDto>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Call API failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("HomeActivity", "Call API failed", t);
            }
        });
    }
}
