package com.example.ticketbookingapp.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ticketbookingapp.R;

public class MovieDetailActivity extends AppCompatActivity {
    private LinearLayout containerAbout, containerSessions;
    private TextView tabAbout, tabSessions;
    private View lineAbout, lineSessions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initViews();
        setupTabs();
    }

    private void initViews() {
        containerAbout = findViewById(R.id.containerAbout);
        containerSessions = findViewById(R.id.containerSessions);
        tabAbout = findViewById(R.id.tabAbout);
        tabSessions = findViewById(R.id.tabSessions);
        lineAbout = findViewById(R.id.lineAbout);
        lineSessions = findViewById(R.id.lineSessions);

        // Mặc định hiện tab About
        showAbout();
    }

    private void setupTabs() {
        findViewById(R.id.tabAboutContainer).setOnClickListener(v -> showAbout());
        findViewById(R.id.tabSessionsContainer).setOnClickListener(v -> showSessions());
    }

    private void showAbout() {
        containerAbout.setVisibility(View.VISIBLE);
        containerSessions.setVisibility(View.GONE);
        tabAbout.setTextColor(getResources().getColor(R.color.primary_main));
        lineAbout.setVisibility(View.VISIBLE);
        tabSessions.setTextColor(getResources().getColor(R.color.text_muted));
        lineSessions.setVisibility(View.INVISIBLE);
    }

    private void showSessions() {
        containerAbout.setVisibility(View.GONE);
        containerSessions.setVisibility(View.VISIBLE);
        tabSessions.setTextColor(getResources().getColor(R.color.primary_main));
        lineSessions.setVisibility(View.VISIBLE);
        tabAbout.setTextColor(getResources().getColor(R.color.text_muted));
        lineAbout.setVisibility(View.INVISIBLE);
    }
}