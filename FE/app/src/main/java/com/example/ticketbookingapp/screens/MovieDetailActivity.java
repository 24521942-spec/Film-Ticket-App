package com.example.ticketbookingapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
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
import com.example.ticketbookingapp.models.Session;
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
    private SessionAdapter adapter;
    private List<Session> sessionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initViews();
        setupMovieInfo();     // THÊM: Gọi hàm đổ dữ liệu cho About
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

    // --- PHẦN BỔ SUNG: DỮ LIỆU GIẢ CHO TAB ABOUT ---
    private void setupMovieInfo() {
        // Tên phim
        txtMovieTitle.setText("The Batman");

        // Đổ dữ liệu vào các hàng thông tin dựa trên ID trong layout của bạn
        setInfoRow(findViewById(R.id.infoDirector), "Director", "Matt Reeves");
        setInfoRow(findViewById(R.id.infoCast), "Cast", "Robert Pattinson, Zoë Kravitz, Paul Dano");
        setInfoRow(findViewById(R.id.infoGenre), "Genre", "Action, Crime, Drama");
        setInfoRow(findViewById(R.id.infoRuntime), "Runtime", "176 min");
        setInfoRow(findViewById(R.id.infoRelease), "Release", "March 4, 2022");
    }

    // Hàm trợ giúp để gán text cho các item info_row
    private void setInfoRow(View view, String label, String value) {
        if (view != null) {
            TextView txtLabel = view.findViewById(R.id.lblInfoTitle);
            TextView txtValue = view.findViewById(R.id.lblInfoValue);
            if (txtLabel != null) txtLabel.setText(label);
            if (txtValue != null) txtValue.setText(value);
        }
    }
    // -----------------------------------------------

    private void setupRecyclerView() {
        // Thêm dữ liệu mẫu (Lưu ý: Bạn nên cập nhật Model Session để có 6 tham số nếu báo lỗi)
        sessionList.add(new Session("14:40", "IMAX", "Eng", 2200, 1000, 1500));
        sessionList.add(new Session("10:00", "2D", "Rus", 1800, 800, 1200));
        sessionList.add(new Session("20:00", "3D", "Eng", 2500, 1200, 1800));

        adapter = new SessionAdapter(sessionList, session -> {
            startActivity(new Intent(this, SeatSelectionActivity.class));
        });

        rvSessions.setLayoutManager(new LinearLayoutManager(this));
        rvSessions.setAdapter(adapter);
    }

    private void setupEventListeners() {
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        findViewById(R.id.tabAboutContainer).setOnClickListener(v -> showAboutTab());
        findViewById(R.id.tabSessionsContainer).setOnClickListener(v -> showSessionsTab());
        findViewById(R.id.btnSelectSession).setOnClickListener(v -> showSessionsTab());

        txtSortTime.setOnClickListener(v -> showSortBottomSheet());

        Switch switchSort = findViewById(R.id.switchSort);
        switchSort.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Collections.sort(sessionList, (s1, s2) -> s1.getFormat().compareTo(s2.getFormat()));
            } else {
                Collections.sort(sessionList, (s1, s2) -> s1.getTime().compareTo(s2.getTime()));
            }
            adapter.notifyDataSetChanged();
        });
    }

    private void showSortBottomSheet() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_sort_sessions, null);

        view.findViewById(R.id.sortTime).setOnClickListener(v -> {
            Collections.sort(sessionList, (s1, s2) -> s1.getTime().compareTo(s2.getTime()));
            adapter.notifyDataSetChanged();
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