package com.example.ticketbookingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.models.Session;
import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {
    private List<Session> sessionList;
    private OnSessionClickListener listener;

    public interface OnSessionClickListener {
        void onSessionClick(Session session);
    }

    public SessionAdapter(List<Session> sessionList, OnSessionClickListener listener) {
        this.sessionList = sessionList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_session_by_time_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Session session = sessionList.get(position); // Dùng .get(position) thay vì []
        holder.txtTime.setText(session.getTime());
        holder.txtFormat.setText(session.getFormat());
        holder.txtLanguage.setText(session.getLanguage());
        holder.txtPriceAdult.setText(String.format("%.0f ₸", session.getPriceAdult()));
        holder.txtPriceChild.setText(String.format("%.0f ₸", session.getPriceChild()));
        holder.txtPriceStudent.setText(String.format("%.0f ₸", session.getPriceStudent()));

        holder.itemView.setOnClickListener(v -> listener.onSessionClick(session));
    }

    @Override
    public int getItemCount() { return sessionList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTime, txtFormat, txtLanguage, txtPriceAdult, txtPriceChild, txtPriceStudent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTime = itemView.findViewById(R.id.txtSessionTime);
            txtFormat = itemView.findViewById(R.id.txtFormat);
            txtLanguage = itemView.findViewById(R.id.txtLanguage);
            txtPriceAdult = itemView.findViewById(R.id.txtPriceAdult);
            txtPriceChild = itemView.findViewById(R.id.txtPriceChild);
            txtPriceStudent = itemView.findViewById(R.id.txtPriceStudent);
        }
    }
}