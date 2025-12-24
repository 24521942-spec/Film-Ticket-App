package com.example.ticketbookingapp.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.network.ApiClient;
import com.example.ticketbookingapp.network.ApiService;
import com.example.ticketbookingapp.network.dto.ApiFilmDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    private ConstraintLayout containerAbout;
    private LinearLayout containerSessions;
    private TextView tabAbout, tabSessions, txtMovieTitle;
    private View lineAbout, lineSessions;

    private int filmId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initViews();
        readIntent();
        setupEventListeners();

        // hiển thị tạm title nếu có (optional)
        String movieTitle = getIntent().getStringExtra("movie_title");
        if (movieTitle != null) txtMovieTitle.setText(movieTitle);

        // gọi BE lấy detail
        if (filmId != -1) {
            fetchFilmDetail(filmId);
        } else {
            Toast.makeText(this, "Missing film_id", Toast.LENGTH_SHORT).show();
        }
    }

    private void initViews() {
        txtMovieTitle = findViewById(R.id.txtMovieTitle);
        containerAbout = findViewById(R.id.containerAbout);
        containerSessions = findViewById(R.id.containerSessions);
        tabAbout = findViewById(R.id.tabAbout);
        tabSessions = findViewById(R.id.tabSessions);
        lineAbout = findViewById(R.id.lineAbout);
        lineSessions = findViewById(R.id.lineSessions);

        showAboutTab();
    }

    private void readIntent() {
        filmId = getIntent().getIntExtra("film_id", -1);
    }

    private void fetchFilmDetail(int id) {
        ApiService api = ApiClient.getClient().create(ApiService.class);

        api.getFilmDetail(id).enqueue(new Callback<ApiFilmDetail>() {
            @Override
            public void onResponse(Call<ApiFilmDetail> call, Response<ApiFilmDetail> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(MovieDetailActivity.this,
                            "Load detail failed: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                ApiFilmDetail f = response.body();
                bindFilmToUI(f);
            }

            @Override
            public void onFailure(Call<ApiFilmDetail> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this,
                        "Error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bindFilmToUI(ApiFilmDetail f) {
        txtMovieTitle.setText(f.getTitle());

        // plot
        TextView txtPlot = findViewById(R.id.txtPlot);
        if (txtPlot != null) txtPlot.setText(safe(f.getDescription()));

        // backdrop/poster (nếu bạn muốn dùng imgBackdrop)
        ImageView imgBackdrop = findViewById(R.id.imgBackdrop);

        String posterUrl = f.getPosterUrl();
        if (posterUrl == null || posterUrl.trim().isEmpty()) {
            imgBackdrop.setImageResource(android.R.drawable.ic_menu_report_image);
        } else {
            Glide.with(this)
                    .load(posterUrl)
                    .placeholder(android.R.drawable.ic_menu_report_image)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(imgBackdrop);
        }


        setRowData(findViewById(R.id.infoGenre), "Genre", safe(f.getGenre()));
        setRowData(findViewById(R.id.infoRuntime), "Runtime", f.getDuration() + " min");
        setRowData(findViewById(R.id.infoCert), "Certificate", safe(f.getAgeRating()));

        // BE chưa có thì tạm N/A
        setRowData(findViewById(R.id.infoDirector), "Director", safe(f.getDirector()));
        setRowData(findViewById(R.id.infoCast), "Cast", safe(f.getCast()));
        setRowData(findViewById(R.id.infoRelease), "Release", safe(f.getRelease()));

    }

    private String safe(String s) {
        return (s == null || s.trim().isEmpty()) ? "N/A" : s;
    }

    private void setRowData(View rowView, String title, String value) {
        if (rowView != null) {
            TextView lblTitle = rowView.findViewById(R.id.lblInfoTitle);
            TextView lblValue = rowView.findViewById(R.id.lblInfoValue);
            lblTitle.setText(title);
            lblValue.setText(value);
        }
    }

    private void setupEventListeners() {
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        findViewById(R.id.tabAboutContainer).setOnClickListener(v -> showAboutTab());
        findViewById(R.id.tabSessionsContainer).setOnClickListener(v -> showSessionsTab());
        findViewById(R.id.btnSelectSession).setOnClickListener(v -> showSessionsTab());
    }

    private void showAboutTab() {
        containerAbout.setVisibility(View.VISIBLE);
        containerSessions.setVisibility(View.GONE);
        tabAbout.setTextColor(ContextCompat.getColor(this, R.color.primary_main));
        lineAbout.setVisibility(View.VISIBLE);
        tabSessions.setTextColor(ContextCompat.getColor(this, R.color.text_muted));
        lineSessions.setVisibility(View.INVISIBLE);
    }

    private void showSessionsTab() {
        containerAbout.setVisibility(View.GONE);
        containerSessions.setVisibility(View.VISIBLE);
        tabSessions.setTextColor(ContextCompat.getColor(this, R.color.primary_main));
        lineSessions.setVisibility(View.VISIBLE);
        tabAbout.setTextColor(ContextCompat.getColor(this, R.color.text_muted));
        lineAbout.setVisibility(View.INVISIBLE);
    }

}
