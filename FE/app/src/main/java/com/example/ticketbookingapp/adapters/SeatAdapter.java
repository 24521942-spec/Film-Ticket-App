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

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {
    private List<Seat> seats;
    private OnSeatClickListener listener;

    public interface OnSeatClickListener {
        void onSeatClick(Seat seat);
    }

    public SeatAdapter(List<Seat> seats, OnSeatClickListener listener) {
        this.seats = seats;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seat, parent, false);
        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        Seat seat = seats.get(position);
        holder.txtSeatNumber.setText(seat.getId().replaceAll("[^0-9]", ""));

        if (seat.getStatus() == 1) { // Occupied
            holder.viewSeat.setBackgroundResource(R.drawable.bg_seat_occupied); // Bạn cần tạo drawable này
            holder.viewSeat.setBackgroundColor(Color.parseColor("#546E7A"));
        } else if (seat.getStatus() == 2) { // Selected
            holder.viewSeat.setBackgroundColor(Color.parseColor("#FC6D19"));
        } else { // Available
            holder.viewSeat.setBackgroundColor(Color.parseColor("#253554"));
        }

        holder.itemView.setOnClickListener(v -> {
            if (seat.getStatus() != 1) { // Nếu ghế chưa bị ai mua
                listener.onSeatClick(seat);
            }
        });
    }

    @Override
    public int getItemCount() { return seats.size(); }

    static class SeatViewHolder extends RecyclerView.ViewHolder {
        View viewSeat;
        TextView txtSeatNumber;
        public SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            viewSeat = itemView.findViewById(R.id.viewSeat);
            txtSeatNumber = itemView.findViewById(R.id.txtSeatNumber);
        }
    }
}