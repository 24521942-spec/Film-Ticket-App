package com.example.ticketbookingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.models.ShowtimeItem;

import java.text.DecimalFormat;
import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.VH> {

    public interface OnClick {
        void onClick(ShowtimeItem item);
    }

    private final List<ShowtimeItem> list;
    private final OnClick listener;
    private final DecimalFormat money = new DecimalFormat("#,###");

    public SessionAdapter(List<ShowtimeItem> list, OnClick listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_session_by_time_row, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        ShowtimeItem item = list.get(position);

        h.txtTime.setText(extractTime(item.getStartTime()));
        h.txtRoom.setText(item.getRoomName());
        h.txtCinema.setText(item.getCinemaName());
        h.txtPrice.setText(money.format(item.getBasePrice()));

        h.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView txtTime, txtRoom, txtCinema, txtPrice;
        VH(@NonNull View itemView) {
            super(itemView);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtRoom = itemView.findViewById(R.id.txtRoom);
            txtCinema = itemView.findViewById(R.id.txtCinema);
            txtPrice = itemView.findViewById(R.id.txtPrice);
        }
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
