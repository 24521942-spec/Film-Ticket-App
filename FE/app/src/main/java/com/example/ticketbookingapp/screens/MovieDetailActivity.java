package com.example.ticketbookingapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.adapters.SessionAdapter;
import com.example.ticketbookingapp.adapters.CinemaAdapter; // Đảm bảo đã import CinemaAdapter
import com.example.ticketbookingapp.models.Session;
import com.example.ticketbookingapp.models.Cinema; // Import Model Cinema
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    private ConstraintLayout containerAbout;
    private LinearLayout containerSessions;
    private TextView tabAbout, tabSessions, txtMovieTitle, txtSortTime;
    private View lineAbout, lineSessions;
    private RecyclerView rvSessions;

    private SessionAdapter sessionAdapter;
    private CinemaAdapter cinemaAdapter;
    private List<Session> sessionList = new ArrayList<>();
    private List<Cinema> cinemaList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initViews();
        setupMovieInfo();
        setupRecyclerView();
        setupEventListeners();
        showAboutTab();
    }

    private void initViews() {
        txtMovieTitle = findViewById(R.id.txtMovieTitle);
        txtSortTime = findViewById(R.id.txtSortTime);
        containerAbout = findViewById(R.id.containerAbout);
        containerSessions = findViewById(R.id.containerSessions);
        tabAbout = findViewById(R.id.tabAbout);
        tabSessions = findViewById(R.id.tabSessions);
        lineAbout = findViewById(R.id.lineAbout);
        lineSessions = findViewById(R.id.lineSessions);
        rvSessions = findViewById(R.id.rvSessionsByTime);
    }

    private void setupMovieInfo() {
        // SỬA: Lấy tên phim thực tế từ Intent
        String movieName = getIntent().getStringExtra("MOVIE_NAME");
        if (movieName == null) movieName = "The Batman";
        txtMovieTitle.setText(movieName);

        setInfoRow(findViewById(R.id.infoDirector), "Director", "Matt Reeves");
        setInfoRow(findViewById(R.id.infoCast), "Cast", "Robert Pattinson, Zoë Kravitz");
        setInfoRow(findViewById(R.id.infoGenre), "Genre", "Action, Crime");
        setInfoRow(findViewById(R.id.infoRuntime), "Runtime", "176 min");
        setInfoRow(findViewById(R.id.infoRelease), "Release", "March 4, 2022");
    }

    private void setInfoRow(View view, String label, String value) {
        if (view != null) {
            TextView txtLabel = view.findViewById(R.id.lblInfoTitle);
            TextView txtValue = view.findViewById(R.id.lblInfoValue);
            if (txtLabel != null) txtLabel.setText(label);
            if (txtValue != null) txtValue.setText(value);
        }
    }

    private void setupRecyclerView() {
        sessionList.clear();
        // SỬA: Thêm tên rạp vào cuối mỗi Session để hiển thị ở mục "By Time"
        sessionList.add(new Session("14:40", "IMAX", "Рус", 2200.0, 1000.0, 1500.0, "•", "Eurasia Cinema 7"));
        sessionList.add(new Session("15:10", "IMAX", "Рус", 3500.0, 1500.0, 2500.0, "4000 ₸", "Eurasia Cinema 7"));
        sessionList.add(new Session("16:05", "Laser", "Eng", 2700.0, 900.0, 1700.0, "•", "Arman Asia Park"));

        sessionAdapter = new SessionAdapter(sessionList, session -> {
            openSeatSelection(txtMovieTitle.getText().toString(), session.getCinemaName());
        });

        cinemaList.clear();
        cinemaList.add(new Cinema("Eurasia Cinema 7", "ул. Петрова, 24", "1.5km", new ArrayList<>(sessionList)));
        cinemaList.add(new Cinema("Arman Asia Park", "пр. Кабанбай 21", "5km", new ArrayList<>(sessionList)));

        // GIẢI QUYẾT LỖI: Cung cấp đủ 2 đối số (List và Listener)
        cinemaAdapter = new CinemaAdapter(cinemaList, (session, cinemaName) -> {
            openSeatSelection(txtMovieTitle.getText().toString(), cinemaName);
        });

        rvSessions.setLayoutManager(new LinearLayoutManager(this));
        rvSessions.setAdapter(sessionAdapter);
    }

    private void openSeatSelection(String movie, String cinema) {
        Intent intent = new Intent(this, SeatSelectionActivity.class);
        intent.putExtra("movieName", movie);
        intent.putExtra("cinemaName", cinema);
        startActivity(intent);
    }

    private void setupEventListeners() {
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        findViewById(R.id.tabAboutContainer).setOnClickListener(v -> showAboutTab());
        findViewById(R.id.tabSessionsContainer).setOnClickListener(v -> showSessionsTab());
        findViewById(R.id.btnSelectSession).setOnClickListener(v -> showSessionsTab());

        txtSortTime.setOnClickListener(v -> showSortBottomSheet());

        Switch switchSort = findViewById(R.id.switchSort);
        if (switchSort != null) {
            switchSort.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    rvSessions.setAdapter(cinemaAdapter);
                } else {
                    rvSessions.setAdapter(sessionAdapter);
                }
                // GIẢI QUYẾT: Không ẩn txtSortTime
                txtSortTime.setVisibility(View.VISIBLE);
            });
        }
    }

    private void showSortBottomSheet() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_sort_sessions, null);

        // Sort by Time
        view.findViewById(R.id.sortTime).setOnClickListener(v -> {
            Collections.sort(sessionList, (s1, s2) -> s1.getTime().compareTo(s2.getTime()));
            sessionAdapter.notifyDataSetChanged();
            txtSortTime.setText("Time");
            dialog.dismiss();
        });

        // Sort by Price (Price Adult)
        view.findViewById(R.id.sortPrice).setOnClickListener(v -> {
            Collections.sort(sessionList, (s1, s2) -> Double.compare(s1.getPriceAdult(), s2.getPriceAdult()));
            sessionAdapter.notifyDataSetChanged();
            txtSortTime.setText("Price");
            dialog.dismiss();
        });

        // Order Descending
        view.findViewById(R.id.orderDescending).setOnClickListener(v -> {
            Collections.reverse(sessionList);
            sessionAdapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        view.findViewById(R.id.btnApplySort).setOnClickListener(v -> dialog.dismiss());

        dialog.setContentView(view);
        dialog.show();
    }

    private void showAboutTab() {
        containerAbout.setVisibility(View.VISIBLE);
        containerSessions.setVisibility(View.GONE);
        updateTabUI(true);
    }

    private void showSessionsTab() {
        containerAbout.setVisibility(View.GONE);
        containerSessions.setVisibility(View.VISIBLE);
        updateTabUI(false);
    }

    private void updateTabUI(boolean isAbout) {
        int primary = ContextCompat.getColor(this, R.color.primary_main);
        int muted = ContextCompat.getColor(this, R.color.text_muted);
        tabAbout.setTextColor(isAbout ? primary : muted);
        lineAbout.setVisibility(isAbout ? View.VISIBLE : View.INVISIBLE);
        tabSessions.setTextColor(isAbout ? muted : primary);
        lineSessions.setVisibility(isAbout ? View.INVISIBLE : View.VISIBLE);
    }
}