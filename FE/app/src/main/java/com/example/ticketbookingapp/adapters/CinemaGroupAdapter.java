package com.example.ticketbookingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.models.CinemaGroup;
import com.example.ticketbookingapp.models.SessionItem;

import java.util.ArrayList;
import java.util.List;

public class CinemaGroupAdapter extends RecyclerView.Adapter<CinemaGroupAdapter.VH> {

    public interface OnSessionClick {
        void onClick(SessionItem item);
    }

    private final Context context;
    private final OnSessionClick onSessionClick;
    private final List<CinemaGroup> data = new ArrayList<>();

    public CinemaGroupAdapter(Context context, OnSessionClick onSessionClick) {
        this.context = context;
        this.onSessionClick = onSessionClick;
    }

    public void setData(List<CinemaGroup> list) {
        data.clear();
        if (list != null) data.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_cinema_group, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        CinemaGroup g = data.get(position);
        h.txtCinemaName.setText(g.cinemaName != null ? g.cinemaName : "");
        h.txtAddress.setText(g.address != null ? g.address : "");

        // IMPORTANT: item_cinema_group của bạn đang dùng include cố định 3 dòng -> không phù hợp data động
        // => Nên đổi layout: thay <include ...> bằng RecyclerView hoặc LinearLayout rỗng để addView.
        // Ở đây mình dùng LinearLayout để add session rows động:
        h.layoutSessionsContainer.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(context);
        for (SessionItem s : g.sessions) {
            View row = inflater.inflate(R.layout.item_session_by_time_row, h.layoutSessionsContainer, false);

            TextView txtTime = row.findViewById(R.id.txtSessionTime);
            TextView txtCinema = row.findViewById(R.id.txtCinemaName);
            TextView txtAdult = row.findViewById(R.id.txtPriceAdult);
            TextView txtChild = row.findViewById(R.id.txtPriceChild);
            TextView txtStudent = row.findViewById(R.id.txtPriceStudent);
            TextView txtVip = row.findViewById(R.id.txtPriceVIP);

            txtTime.setText(s.startTime != null ? s.startTime : "");
            txtCinema.setText(g.cinemaName != null ? g.cinemaName : "");
            txtAdult.setText(s.adult != null ? String.valueOf(s.adult) : "-");
            txtChild.setText(s.child != null ? String.valueOf(s.child) : "-");
            txtStudent.setText(s.student != null ? String.valueOf(s.student) : "-");
            txtVip.setText(s.vip != null ? String.valueOf(s.vip) : "-");

            row.setOnClickListener(v -> onSessionClick.onClick(s));
            h.layoutSessionsContainer.addView(row);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView txtCinemaName, txtAddress;
        LinearLayout layoutSessionsContainer;

        VH(@NonNull View itemView) {
            super(itemView);
            txtCinemaName = itemView.findViewById(R.id.txtCinemaName);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            layoutSessionsContainer = itemView.findViewById(R.id.layoutSessionsContainer);
        }
    }
}
