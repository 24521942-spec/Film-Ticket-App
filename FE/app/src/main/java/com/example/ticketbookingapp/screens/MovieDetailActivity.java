package com.example.ticketbookingapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.adapters.SessionAdapter;
import com.example.ticketbookingapp.models.Session;
import com.example.ticketbookingapp.models.ShowtimeItem;
import com.example.ticketbookingapp.network.ApiClient;
import com.example.ticketbookingapp.network.ApiService;
import com.example.ticketbookingapp.network.dto.MovieDetailResponse;
import com.example.ticketbookingapp.network.dto.ShowtimeDetailDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_FILM_ID = "filmId";
    public static final String EXTRA_TITLE    = "extra_title";
    public static final String EXTRA_POSTER   = "extra_poster";
    public static final String EXTRA_PLOT     = "extra_plot";
    public static final String EXTRA_IMDB     = "extra_imdb";
    public static final String EXTRA_KINO     = "extra_kino";
    public static final String EXTRA_RATING   = "extra_rating";
    public static final String EXTRA_RUNTIME  = "extra_runtime";
    public static final String EXTRA_RELEASE  = "extra_release";
    public static final String EXTRA_GENRE    = "extra_genre";
    public static final String EXTRA_DIRECTOR = "extra_director";
    public static final String EXTRA_CAST     = "extra_cast";

    private RecyclerView rvSessionsByTime;
    private final List<ShowtimeItem> sessionList = new ArrayList<>();
    private SessionAdapter sessionAdapter;


    private boolean sessionsLoaded = false;
    private int currentFilmId = -1;


    private ImageView btnBack, imgBackdrop;
    private TextView txtMovieTitle, txtPlot, txtImdb, txtKino;

    // Tabs
    private LinearLayout tabAboutContainer, tabSessionsContainer;
    private TextView tabAbout, tabSessions;
    private View lineAbout, lineSessions;

    // Divider
    private View divider;

    // Containers
    private View containerAbout, containerSessions;

    // Include roots
    private View infoCert, infoRuntime, infoRelease, infoGenre, infoDirector, infoCast;

    // Button
    private View btnSelectSession;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // Bind views
        btnBack = findViewById(R.id.btnBack);
        txtMovieTitle = findViewById(R.id.txtMovieTitle);
        imgBackdrop = findViewById(R.id.imgBackdrop);
        txtPlot = findViewById(R.id.txtPlot);
        txtImdb = findViewById(R.id.txtImdb);
        txtKino = findViewById(R.id.txtKino);

        tabAboutContainer = findViewById(R.id.tabAboutContainer);
        tabSessionsContainer = findViewById(R.id.tabSessionsContainer);
        tabAbout = findViewById(R.id.tabAbout);
        tabSessions = findViewById(R.id.tabSessions);
        lineAbout = findViewById(R.id.lineAbout);
        lineSessions = findViewById(R.id.lineSessions);

        divider = findViewById(R.id.divider);

        containerAbout = findViewById(R.id.containerAbout);
        containerSessions = findViewById(R.id.containerSessions);

        infoCert = findViewById(R.id.infoCert);
        infoRuntime = findViewById(R.id.infoRuntime);
        infoRelease = findViewById(R.id.infoRelease);
        infoGenre = findViewById(R.id.infoGenre);
        infoDirector = findViewById(R.id.infoDirector);
        infoCast = findViewById(R.id.infoCast);

        btnSelectSession = findViewById(R.id.btnSelectSession);
        rvSessionsByTime = findViewById(R.id.rvSessionsByTime);

        rvSessionsByTime.setLayoutManager(new LinearLayoutManager(this));

        sessionAdapter = new SessionAdapter(sessionList, session -> {

            Intent i = new Intent(MovieDetailActivity.this, SeatSelectionActivity.class);

            // 1) showtimeId
            i.putExtra(SeatSelectionActivity.EXTRA_SHOWTIME_ID, session.getShowtimeId());

            // 2) basePrice (dto.getBasePrice() thường là Double -> phải ép int)
            int basePriceInt = 0;
            try {
                // session ở đây thực tế là ShowtimeItem (vì sessionList là List<ShowtimeItem>)
                // nên session.getBasePrice() sẽ là Double hoặc double tùy class bạn
                double bp = session.getBasePrice(); // nếu getBasePrice() trả Double thì vẫn ok (auto unbox)
                basePriceInt = (int) Math.round(bp);
            } catch (Exception e) {
                basePriceInt = 0;
            }
            i.putExtra(SeatSelectionActivity.EXTRA_BASE_PRICE, basePriceInt);

            // 3) header info
            i.putExtra(SeatSelectionActivity.EXTRA_FILM_TITLE, txtMovieTitle.getText().toString());

            // cinemaName/roomName lấy từ session item
            i.putExtra(SeatSelectionActivity.EXTRA_CINEMA_NAME, session.getCinemaName());
            i.putExtra(SeatSelectionActivity.EXTRA_ROOM_NAME, session.getRoomName());

            // 4) date/time từ startTime
            // startTime dạng "2025-03-01T18:00"
            String startTime = session.getStartTime();
            String dateText = "";
            String timeText = "";
            if (startTime != null) {
                int tIndex = startTime.indexOf('T');
                if (tIndex > 0) {
                    dateText = startTime.substring(0, tIndex);
                    if (tIndex + 1 < startTime.length()) {
                        String tPart = startTime.substring(tIndex + 1);
                        timeText = tPart.length() >= 5 ? tPart.substring(0, 5) : tPart;
                    }
                }
            }

            i.putExtra(SeatSelectionActivity.EXTRA_DATE_TEXT, dateText);
            i.putExtra(SeatSelectionActivity.EXTRA_TIME_TEXT, timeText);

            startActivity(i);
        });


        rvSessionsByTime.setAdapter(sessionAdapter);


        // Back
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        // Default show About
        showAbout();

        // Tab click
        if (tabAboutContainer != null) tabAboutContainer.setOnClickListener(v -> showAbout());
        if (tabSessionsContainer != null) tabSessionsContainer.setOnClickListener(v -> showSessions());

        // Button "Select Session" -> mở tab Sessions luôn
        if (btnSelectSession != null) btnSelectSession.setOnClickListener(v -> showSessions());

        // Get filmId
        Intent intent = getIntent();
        int filmId = intent != null ? intent.getIntExtra(EXTRA_FILM_ID, -1) : -1;
        if (filmId <= 0) {
            Toast.makeText(this, "Missing filmId", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        currentFilmId = filmId;
        // Call API detail
        fetchFilmDetail(filmId);
    }

    private void fetchFilmDetail(int filmId) {
        ApiService api = ApiClient.getClient(this).create(ApiService.class);


        api.getFilmDetail(filmId).enqueue(new Callback<MovieDetailResponse>() {
            @Override
            public void onResponse(Call<MovieDetailResponse> call, Response<MovieDetailResponse> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(MovieDetailActivity.this,
                            "Load detail failed: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                    Log.e("MovieDetail", "Detail failed code=" + response.code());
                    return;
                }
                bindMovie(response.body());
            }

            @Override
            public void onFailure(Call<MovieDetailResponse> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this,
                        "Network error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
                Log.e("MovieDetail", "Network error", t);
            }
        });
    }

    private void bindMovie(MovieDetailResponse m) {
        // Title
        if (txtMovieTitle != null) txtMovieTitle.setText(nvl(m.title));

        // Backdrop/Poster
        if (imgBackdrop != null) {
            Glide.with(this)
                    .load(m.posterUrl)
                    .placeholder(android.R.drawable.ic_menu_report_image)
                    .error(android.R.drawable.ic_menu_report_image)
                    .centerCrop()
                    .into(imgBackdrop);
        }

        // Plot
        if (txtPlot != null) txtPlot.setText(nvl(m.description));

        // Rating: ưu tiên avgRating nếu có
        double rVal = (m.avgRating != null) ? m.avgRating : (m.rating != null ? m.rating : 0.0);
        String ratingText = rVal > 0 ? String.format(Locale.getDefault(), "%.1f", rVal) : "N/A";
        if (txtImdb != null) txtImdb.setText(ratingText);
        if (txtKino != null) txtKino.setText(ratingText); // nếu bạn chỉ có 1 rating

        // Rows
        setInfoRow(infoCert, "Certificate", nvl(m.ageRating));
        setInfoRow(infoRuntime, "Runtime", formatDuration(m.duration));
        setInfoRow(infoRelease, "Release", year(m.release));
        setInfoRow(infoGenre, "Genre", nvl(m.genre));
        setInfoRow(infoDirector, "Director", nvl(m.director));
        setInfoRow(infoCast, "Cast", nvl(m.cast));
    }

    private void setInfoRow(View rowRoot, String title, String value) {
        if (rowRoot == null) return;
        TextView t = rowRoot.findViewById(R.id.lblInfoTitle);
        TextView v = rowRoot.findViewById(R.id.lblInfoValue);
        if (t != null) t.setText(title);
        if (v != null) v.setText(value != null ? value : "");
    }

    // duration phút -> "02:56" giống hình mẫu
    private String formatDuration(Integer minutes) {
        if (minutes == null || minutes <= 0) return "";
        int h = minutes / 60;
        int m = minutes % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", h, m);
    }

    private String year(String isoDate) {
        if (isoDate == null || isoDate.length() < 4) return "";
        return isoDate.substring(0, 4);
    }

    private String nvl(String s) {
        return s == null ? "" : s;
    }

    private void showAbout() {
        if (containerAbout != null) containerAbout.setVisibility(View.VISIBLE);
        if (containerSessions != null) containerSessions.setVisibility(View.GONE);

        if (divider != null) divider.setVisibility(View.VISIBLE);

        if (lineAbout != null) lineAbout.setVisibility(View.VISIBLE);
        if (lineSessions != null) lineSessions.setVisibility(View.INVISIBLE);

        if (tabAbout != null) tabAbout.setTextColor(ContextCompat.getColor(this, R.color.primary_main));
        if (tabSessions != null) tabSessions.setTextColor(ContextCompat.getColor(this, R.color.text_muted));
    }

    private void showSessions() {
        // Ẩn Movie Detail
        if (containerAbout != null) containerAbout.setVisibility(View.GONE);

        // Hiện Sessions
        if (containerSessions != null) containerSessions.setVisibility(View.VISIBLE);

        if (divider != null) divider.setVisibility(View.VISIBLE); // muốn bỏ divider khi sessions thì đổi thành GONE

        if (lineAbout != null) lineAbout.setVisibility(View.INVISIBLE);
        if (lineSessions != null) lineSessions.setVisibility(View.VISIBLE);

        if (tabAbout != null) tabAbout.setTextColor(ContextCompat.getColor(this, R.color.text_muted));
        if (tabSessions != null) tabSessions.setTextColor(ContextCompat.getColor(this, R.color.primary_main));
        if (!sessionsLoaded && currentFilmId > 0) {
            loadSessions(currentFilmId);
        }

    }

        private void loadSessions(int filmId) {
            ApiService api = ApiClient.getClient(this).create(ApiService.class);


            api.getShowtimesByFilm(filmId).enqueue(new Callback<List<ShowtimeDetailDto>>() {
                @Override
                public void onResponse(Call<List<ShowtimeDetailDto>> call, Response<List<ShowtimeDetailDto>> response) {
                    if (!response.isSuccessful() || response.body() == null) {
                        Toast.makeText(MovieDetailActivity.this,
                                "Load sessions failed: " + response.code(),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    List<ShowtimeDetailDto> dtoList = response.body();

                    sessionList.clear();
                    for (ShowtimeDetailDto dto : dtoList) {
                        sessionList.add(new ShowtimeItem(
                                dto.getShowtimeId(),
                                dto.getStartTime(),
                                dto.getBasePrice(),
                                dto.getCinemaName(),
                                dto.getRoomName()
                        ));
                    }
                    sessionAdapter.notifyDataSetChanged();
                    sessionsLoaded = true;
                }

                @Override
                public void onFailure(Call<List<ShowtimeDetailDto>> call, Throwable t) {
                    Toast.makeText(MovieDetailActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        private String extractTime(String startTime) {
            if (startTime == null) return "";
            int tIndex = startTime.indexOf('T');
            String part = (tIndex >= 0 && tIndex + 1 < startTime.length())
                    ? startTime.substring(tIndex + 1)
                    : startTime;
            return part.length() >= 5 ? part.substring(0, 5) : part;
        }

}
