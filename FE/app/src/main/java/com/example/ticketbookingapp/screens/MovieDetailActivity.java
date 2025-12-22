package com.example.ticketbookingapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import com.example.ticketbookingapp.R;

public class MovieDetailActivity extends AppCompatActivity {

    private ConstraintLayout containerAbout;
    private LinearLayout containerSessions;
    private TextView tabAbout, tabSessions, txtMovieTitle;
    private View lineAbout, lineSessions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initViews();
        handleIntentData();
        setupInfoRows(); // Thiết lập nội dung cho Director, Cast...
        setupEventListeners();
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

    private void setupInfoRows() {
        // Ánh xạ và set text cho từng dòng dựa trên ID trong file activity_movie_detail.xml
        setRowData(findViewById(R.id.infoDirector), "Director", "Matt Reeves");
        setRowData(findViewById(R.id.infoCast), "Cast", "Robert Pattinson, Zoë Kravitz");
        setRowData(findViewById(R.id.infoGenre), "Genre", "Action, Crime, Drama");
        setRowData(findViewById(R.id.infoRuntime), "Runtime", "176 min");
        setRowData(findViewById(R.id.infoRelease), "Release", "March 4, 2022");
        setRowData(findViewById(R.id.infoCert), "Certificate", "PG-13");
    }

    // Hàm phụ để set text cho các dòng dùng chung layout item_info_row
    private void setRowData(View rowView, String title, String value) {
        if (rowView != null) {
            TextView lblTitle = rowView.findViewById(R.id.lblInfoTitle);
            TextView lblValue = rowView.findViewById(R.id.lblInfoValue);
            lblTitle.setText(title);
            lblValue.setText(value);
        }
    }

    private void handleIntentData() {
        String movieTitle = getIntent().getStringExtra("movie_title");
        if (movieTitle != null) {
            txtMovieTitle.setText(movieTitle);
        }
    }

    private void setupEventListeners() {
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        findViewById(R.id.tabAboutContainer).setOnClickListener(v -> showAboutTab());
        findViewById(R.id.tabSessionsContainer).setOnClickListener(v -> showSessionsTab());
        findViewById(R.id.btnSelectSession).setOnClickListener(v -> showSessionsTab());

        // Mở màn hình chọn ghế
        View rvSessions = findViewById(R.id.rvSessionsByTime);
        if (rvSessions != null) {
            rvSessions.setOnClickListener(v -> {
                Intent intent = new Intent(this, SeatSelectionActivity.class);
                startActivity(intent);
            });
        }
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