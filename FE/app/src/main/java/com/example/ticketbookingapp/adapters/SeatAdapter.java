package com.example.ticketbookingapp.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.models.Seat;

import java.util.List;
import java.util.Set;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {

    public interface OnSeatClick {
        void onClick(Seat seat);
    }

    private final List<Seat> seatList;
    private final Set<Integer> selectedSeatIds;
    private final OnSeatClick listener;

    public SeatAdapter(List<Seat> seatList,
                       Set<Integer> selectedSeatIds,
                       OnSeatClick listener) {
        this.seatList = seatList;
        this.selectedSeatIds = selectedSeatIds;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_seat_single, parent, false);
        return new SeatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        Seat seat = seatList.get(position);
        holder.txtSeat.setText(seat.getSeatCode());

        boolean isSelected = selectedSeatIds.contains(seat.getSeatId());
        boolean isAvailable = "AVAILABLE".equalsIgnoreCase(seat.getStatus());

        // 🎨 màu ghế
        if (isSelected) {
            holder.itemView.setBackgroundResource(R.drawable.seat_chosen);
        } else if (!isAvailable) {
            holder.itemView.setBackgroundResource(R.drawable.seat_occupied);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.seat_available);
        }
        if (!isAvailable) {
            holder.txtSeat.setText(""); // ẩn chữ
            holder.itemView.setBackgroundResource(R.drawable.seat_occupied);
            holder.itemView.setClickable(false);
        }



        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(seat);
            }
        });
    }

    @Override
    public int getItemCount() {
        return seatList.size();
    }

    static class SeatViewHolder extends RecyclerView.ViewHolder {
        TextView txtSeat;

        SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSeat = itemView.findViewById(R.id.txtSeat);
        }
    }
}
