package com.example.ticketbookingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.network.dto.TicketHistoryDto;

import java.util.List;

public class TicketHistoryAdapter extends RecyclerView.Adapter<TicketHistoryAdapter.VH> {

    public interface OnItemClick {
        void onClick(TicketHistoryDto item);
    }

    private List<TicketHistoryDto> data;
    private final OnItemClick listener;

    public TicketHistoryAdapter(OnItemClick listener) {
        this.listener = listener;
    }

    public void setData(List<TicketHistoryDto> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ticket_history, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        TicketHistoryDto item = data.get(position);

        // ✅ CHỈ HIỂN THỊ BOOKING ID
        h.tvBooking.setText("Booking #" + item.getBookingId());

        h.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvBooking;

        VH(@NonNull View itemView) {
            super(itemView);
            tvBooking = itemView.findViewById(R.id.tvFilmTitle);
        }
    }
}
