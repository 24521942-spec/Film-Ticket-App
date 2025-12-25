package com.example.ticketbookingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.models.Cinema;
import com.example.ticketbookingapp.models.Session;
import java.util.List;

public class CinemaAdapter extends RecyclerView.Adapter<CinemaAdapter.CinemaViewHolder> {

    private List<Cinema> cinemaList;
    private OnSessionClickListener listener;

    public interface OnSessionClickListener {
        void onSessionClick(Session session, String cinemaName);
    }

    public CinemaAdapter(List<Cinema> cinemaList, OnSessionClickListener listener) {
        this.cinemaList = cinemaList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CinemaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cinema_group, parent, false);
        return new CinemaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CinemaViewHolder holder, int position) {
        Cinema cinema = cinemaList.get(position);
        holder.txtCinemaName.setText(cinema.getName());
        holder.txtAddress.setText(cinema.getAddress());
        holder.txtDistance.setText(cinema.getDistance());

        holder.layoutSessionsContainer.removeAllViews();

        for (Session session : cinema.getSessions()) {
            View row = LayoutInflater.from(holder.itemView.getContext())
                    .inflate(R.layout.item_sessions_row, holder.layoutSessionsContainer, false);

            ((TextView)row.findViewById(R.id.txtSessionTime)).setText(session.getTime());
            ((TextView)row.findViewById(R.id.txtFormat)).setText(session.getFormat());
            ((TextView)row.findViewById(R.id.txtPriceAdult)).setText(String.format("%.0f ₸", session.getPriceAdult()));
            ((TextView)row.findViewById(R.id.txtPriceChild)).setText(String.format("%.0f ₸", session.getPriceChild()));
            ((TextView)row.findViewById(R.id.txtPriceStudent)).setText(String.format("%.0f ₸", session.getPriceStudent()));
            ((TextView)row.findViewById(R.id.txtPriceVIP)).setText(session.getPriceVIP());
            ((TextView)row.findViewById(R.id.txtLanguage)).setText(session.getLanguage());

            row.setOnClickListener(v -> {
                if (listener != null) listener.onSessionClick(session, cinema.getName());
            });

            holder.layoutSessionsContainer.addView(row);
        }
    }

    @Override
    public int getItemCount() { return cinemaList != null ? cinemaList.size() : 0; }

    public static class CinemaViewHolder extends RecyclerView.ViewHolder {
        TextView txtCinemaName, txtAddress, txtDistance;
        LinearLayout layoutSessionsContainer;
        public CinemaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCinemaName = itemView.findViewById(R.id.txtCinemaName);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtDistance = itemView.findViewById(R.id.txtDistance);
            layoutSessionsContainer = itemView.findViewById(R.id.layoutSessionsContainer);
        }
    }
}