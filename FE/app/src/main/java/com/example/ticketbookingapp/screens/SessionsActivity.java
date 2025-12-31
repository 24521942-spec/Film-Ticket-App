//package com.example.ticketbookingapp.screens;
//
//import android.os.Bundle;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.ticketbookingapp.R;
//import com.example.ticketbookingapp.adapters.SessionAdapter;
//import com.example.ticketbookingapp.models.Session;
//import com.example.ticketbookingapp.network.ApiClient;
//import com.example.ticketbookingapp.network.ApiService;
//import com.example.ticketbookingapp.network.dto.ShowtimeDetailDto;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class SessionsActivity extends AppCompatActivity {
//
//    public static final String EXTRA_FILM_ID = "filmId";
//    public static final String EXTRA_TITLE  = "extra_title";
//
//    private ImageView btnBack;
//    private TextView txtTitle;
//    private RecyclerView rvSessionsByTime;
//
//    private final List<Session> sessionList = new ArrayList<>();
//    private SessionAdapter adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sessions);
//
//        btnBack = findViewById(R.id.btnBack);
//        txtTitle = findViewById(R.id.txtTitle);
//        rvSessionsByTime = findViewById(R.id.rvSessionsByTime);
//
//        btnBack.setOnClickListener(v -> finish());
//
//        int filmId = getIntent().getIntExtra(EXTRA_FILM_ID, -1);
//        String title = getIntent().getStringExtra(EXTRA_TITLE);
//
//        txtTitle.setText((title != null && !title.trim().isEmpty()) ? (title + " - Sessions") : "Sessions");
//
//        if (filmId == -1) {
//            Toast.makeText(this, "Missing filmId", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        rvSessionsByTime.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new SessionAdapter(sessionList, session -> {
//            // tuỳ bạn xử lý click: mở SeatActivity / ShowtimeDetailActivity...
//            Toast.makeText(
//                    SessionsActivity.this,
//                    "ShowtimeId: " + session.getTime(),
//                    Toast.LENGTH_SHORT
//            ).show();
//        });
//        rvSessionsByTime.setAdapter(adapter);
//
//        loadSessions(filmId);
//    }
//
//    private void loadSessions(int filmId) {
//        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
//
//
//        // ĐÚNG theo ApiService của bạn: getShowtimesByFilm(Integer filmId):contentReference[oaicite:4]{index=4}
//        Call<List<ShowtimeDetailDto>> call = apiService.getShowtimesByFilm(filmId);
//
//        call.enqueue(new Callback<List<ShowtimeDetailDto>>() {
//            @Override
//            public void onResponse(Call<List<ShowtimeDetailDto>> call, Response<List<ShowtimeDetailDto>> response) {
//                if (!response.isSuccessful() || response.body() == null) {
//                    Toast.makeText(SessionsActivity.this, "Load sessions failed", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                List<ShowtimeDetailDto> dtoList = response.body();
//
//                sessionList.clear();
//                for (ShowtimeDetailDto dto : dtoList) {
//                    // startTime BE có thể là "2025-03-01T18:00" hoặc full giây -> mình cắt HH:mm
//                    String time = extractTime(dto.getStartTime());
//
//                    // SessionAdapter đang show txtFormat + txtLanguage.
//                    // DTO không có format/language => dùng roomName làm "format", language để "-" (không bịa)
//                    String format = safe(dto.getRoomName());
//                    String language = "-";
//
//                    double priceAdult   = dto.getPriceAdult()   != null ? dto.getPriceAdult()   : 0;
//                    double priceChild   = dto.getPriceChild()   != null ? dto.getPriceChild()   : 0;
//                    double priceStudent = dto.getPriceStudent() != null ? dto.getPriceStudent() : 0;
//
//                    // priceVIP trong Session là String:contentReference[oaicite:5]{index=5}
//                    String priceVipText = (dto.getPriceVip() != null) ? String.valueOf(dto.getPriceVip()) : "•";
//
//                    String cinemaName = safe(dto.getCinemaName());
//
//                    sessionList.add(new Session(
//                            time,
//                            format,
//                            language,
//                            priceAdult,
//                            priceChild,
//                            priceStudent,
//                            priceVipText,
//                            cinemaName
//                    ));
//                }
//
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<List<ShowtimeDetailDto>> call, Throwable t) {
//                Toast.makeText(SessionsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private String safe(String s) {
//        return (s == null) ? "" : s;
//    }
//
//    // Nhận "2025-03-01T18:00" / "2025-12-25T20:30:00" / "18:00" => trả "18:00"
//    private String extractTime(String startTime) {
//        if (startTime == null) return "";
//        int tIndex = startTime.indexOf('T');
//        String part = (tIndex >= 0 && tIndex + 1 < startTime.length())
//                ? startTime.substring(tIndex + 1)
//                : startTime;
//
//        // part có thể là "20:30:00" => lấy "20:30"
//        if (part.length() >= 5) return part.substring(0, 5);
//        return part;
//    }
//}
