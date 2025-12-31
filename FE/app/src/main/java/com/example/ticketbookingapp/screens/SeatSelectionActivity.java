package com.example.ticketbookingapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.adapters.SeatAdapter;
import com.example.ticketbookingapp.models.BookingDraft;
import com.example.ticketbookingapp.models.Seat;
import com.example.ticketbookingapp.network.ApiClient;
import com.example.ticketbookingapp.network.ApiService;
import com.example.ticketbookingapp.network.dto.HoldRequest;
import com.example.ticketbookingapp.network.dto.SeatDto;
import com.example.ticketbookingapp.utils.SessionManager;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeatSelectionActivity extends AppCompatActivity {

    public static final String EXTRA_SHOWTIME_ID  = "showtimeId";
    public static final String EXTRA_BASE_PRICE   = "basePrice";

    public static final String EXTRA_FILM_TITLE   = "filmTitle";
    public static final String EXTRA_CINEMA_NAME  = "cinemaName";
    public static final String EXTRA_ROOM_NAME    = "roomName";
    public static final String EXTRA_DATE_TEXT    = "dateText";
    public static final String EXTRA_TIME_TEXT    = "timeText";

    private RecyclerView recyclerSeats;
    private SeatAdapter adapter;
    private final List<Seat> seatList = new ArrayList<>();
    private final Set<Integer> selectedSeatIds = new HashSet<>();

    private int totalPrice = 0;

    private MaterialButton btnBuy;
    private ImageButton btnBack;

    private TextView txtMovieName, txtCinemaName, txtDate, txtTime;

    private int showtimeId;
    private int basePrice;

    // ✅ giữ header “chuẩn” từ intent để buildDraft không bị mất
    private String filmTitle, cinemaName, roomName, dateText, timeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        btnBuy = findViewById(R.id.btnBuy);
        btnBack = findViewById(R.id.btnBack);
        recyclerSeats = findViewById(R.id.recyclerSeats);

        txtMovieName  = findViewById(R.id.txtMovieName);
        txtCinemaName = findViewById(R.id.txtCinemaName);
        txtDate       = findViewById(R.id.txtDate);
        txtTime       = findViewById(R.id.txtTime);

        btnBack.setOnClickListener(v -> finish());

        showtimeId = getIntent().getIntExtra(EXTRA_SHOWTIME_ID, -1);
        basePrice  = getIntent().getIntExtra(EXTRA_BASE_PRICE, 0);

        if (showtimeId <= 0) {
            Toast.makeText(this, "Missing showtimeId", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        // basePrice = 0 => seat total = 0 (Preview sẽ sai)
        if (basePrice <= 0) {
            Toast.makeText(this, "Warning: basePrice = 0 (seat total will be 0)", Toast.LENGTH_SHORT).show();
        }

        // ✅ đọc header từ intent và lưu vào biến member
        filmTitle  = getIntent().getStringExtra(EXTRA_FILM_TITLE);
        cinemaName = getIntent().getStringExtra(EXTRA_CINEMA_NAME);
        roomName   = getIntent().getStringExtra(EXTRA_ROOM_NAME);
        dateText   = getIntent().getStringExtra(EXTRA_DATE_TEXT);
        timeText   = getIntent().getStringExtra(EXTRA_TIME_TEXT);

        // show lên UI (không bắt buộc nhưng giúp đẹp)
        if (filmTitle != null && !filmTitle.trim().isEmpty()) txtMovieName.setText(filmTitle);

        String line2 = "";
        if (cinemaName != null && !cinemaName.trim().isEmpty()) line2 = cinemaName.trim();
        if (roomName != null && !roomName.trim().isEmpty()) {
            if (line2.isEmpty()) line2 = roomName.trim();
            else line2 = line2 + " • " + roomName.trim();
        }
        if (!line2.isEmpty()) txtCinemaName.setText(line2);

        if (dateText != null && !dateText.trim().isEmpty()) txtDate.setText(dateText);
        if (timeText != null && !timeText.trim().isEmpty()) txtTime.setText(timeText);

        recyclerSeats.setLayoutManager(new GridLayoutManager(this, 8));

        adapter = new SeatAdapter(
                seatList,
                selectedSeatIds,
                seat -> toggleSeat(seat)
        );
        recyclerSeats.setAdapter(adapter);

        recyclerSeats.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                if (e.getAction() != MotionEvent.ACTION_UP) return false;

                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child == null) return false;

                int position = rv.getChildAdapterPosition(child);
                if (position == RecyclerView.NO_POSITION) return false;

                Seat seat = seatList.get(position);
                toggleSeat(seat);
                return true;
            }
        });

        totalPrice = 0;
        updateTotalPrice(totalPrice);
        btnBuy.setEnabled(false);

        btnBuy.setOnClickListener(v -> {
            if (selectedSeatIds.isEmpty()) {
                Toast.makeText(this, "Please choose at least 1 seat", Toast.LENGTH_SHORT).show();
                return;
            }

            // ✅ Option A: đi thẳng sang Food
            // goToFoodCombo(buildDraft());

            // ✅ Option B: hold seat trên BE rồi mới sang Food (đang dùng)
            holdSeatsThenGoFood();
        });

        loadSeats();
    }

    private void loadSeats() {
        ApiService api = ApiClient.getClient(this).create(ApiService.class);

        api.getSeatsByShowtime(showtimeId).enqueue(new Callback<List<SeatDto>>() {
            @Override
            public void onResponse(Call<List<SeatDto>> call, Response<List<SeatDto>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(SeatSelectionActivity.this,
                            "Load seats failed: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                seatList.clear();
                selectedSeatIds.clear();
                totalPrice = 0;
                updateTotalPrice(totalPrice);
                btnBuy.setEnabled(false);

                for (SeatDto dto : response.body()) {
                    seatList.add(new Seat(
                            dto.seatId != null ? dto.seatId : 0,
                            dto.seatCode != null ? dto.seatCode : "",
                            dto.status != null ? dto.status : "AVAILABLE"
                    ));
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<SeatDto>> call, Throwable t) {
                Toast.makeText(SeatSelectionActivity.this,
                        "Network error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toggleSeat(Seat seat) {
        if (seat.getStatus() == null || !"AVAILABLE".equalsIgnoreCase(seat.getStatus())) return;

        int seatId = seat.getSeatId();
        if (selectedSeatIds.contains(seatId)) {
            selectedSeatIds.remove(seatId);
        } else {
            selectedSeatIds.add(seatId);
        }

        // ✅ tính lại cho chắc, không bị lệch
        totalPrice = basePrice * selectedSeatIds.size();

        updateTotalPrice(totalPrice);
        btnBuy.setEnabled(!selectedSeatIds.isEmpty());
        adapter.notifyDataSetChanged();
    }

    private void updateTotalPrice(int price) {
        btnBuy.setText("Continue • " + price + " ₸");
    }

    private BookingDraft buildDraft() {
        BookingDraft draft = new BookingDraft();

        draft.showtimeId = showtimeId;
        draft.basePrice  = basePrice;

        // ✅ dùng biến member (lấy từ intent), không phụ thuộc UI
        draft.filmTitle  = filmTitle;
        draft.cinemaName = cinemaName;
        draft.roomName   = roomName;
        draft.dateText   = dateText;
        draft.timeText   = timeText;

        draft.seatIds = new ArrayList<>(selectedSeatIds);

        // ✅ đảm bảo luôn đúng
        draft.seatTotalPrice = basePrice * draft.seatIds.size();

        // init food
        if (draft.foods == null) draft.foods = new ArrayList<>();
        draft.foodTotalPrice = 0;

        return draft;
    }

    private void goToFoodCombo(BookingDraft draft) {
        Intent i = new Intent(this, FoodComboActivity.class);
        i.putExtra("bookingDraft", draft);
        startActivity(i);
    }

    private void holdSeatsThenGoFood() {
        ApiService api = ApiClient.getClient(this).create(ApiService.class);

        SessionManager session = new SessionManager(this);
        int userId = session.getUserId();

        HoldRequest body = new HoldRequest(
                userId,
                new ArrayList<>(selectedSeatIds),
                5
        );

        api.holdSeats(showtimeId, body).enqueue(new Callback<List<SeatDto>>() {
            @Override
            public void onResponse(Call<List<SeatDto>> call, Response<List<SeatDto>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(SeatSelectionActivity.this,
                            "Hold seats failed: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // optional update status
                if (response.body() != null) {
                    for (SeatDto dto : response.body()) {
                        int id = dto.seatId != null ? dto.seatId : -1;
                        for (Seat s : seatList) {
                            if (s.getSeatId() == id) {
                                s.setStatus(dto.status);
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                goToFoodCombo(buildDraft());
            }

            @Override
            public void onFailure(Call<List<SeatDto>> call, Throwable t) {
                Toast.makeText(SeatSelectionActivity.this,
                        "Network error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
