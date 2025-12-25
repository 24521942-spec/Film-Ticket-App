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
        // Đảm bảo tên layout R.layout.item_session_by_time_row khớp với file XML của bạn
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_session_by_time_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Session session = sessionList.get(position);

        // 1. Hiển thị giờ và định dạng
        holder.txtTime.setText(session.getTime());
        holder.txtFormat.setText(session.getFormat());

        // 2. BỔ SUNG: Hiển thị tên rạp (txtCinemaName)
        if (holder.txtCinemaName != null) {
            holder.txtCinemaName.setText(session.getCinemaName());
        }

        // 3. Hiển thị các loại giá tiền
        holder.txtPriceAdult.setText(String.format("%.0f", session.getPriceAdult()));
        holder.txtPriceChild.setText(String.format("%.0f", session.getPriceChild()));
        holder.txtPriceStudent.setText(String.format("%.0f", session.getPriceStudent()));

        // 4. BỔ SUNG: Hiển thị giá VIP (hoặc dấu •)
        if (holder.txtPriceVIP != null) {
            holder.txtPriceVIP.setText(session.getPriceVIP());
        }

        // 5. Ngôn ngữ
        holder.txtLanguage.setText(session.getLanguage());

        holder.itemView.setOnClickListener(v -> listener.onSessionClick(session));
    }

    @Override
    public int getItemCount() {
        return sessionList != null ? sessionList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Bổ sung txtCinemaName và txtPriceVIP
        TextView txtTime, txtFormat, txtLanguage, txtPriceAdult, txtPriceChild, txtPriceStudent, txtCinemaName, txtPriceVIP;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTime = itemView.findViewById(R.id.txtSessionTime);
            txtFormat = itemView.findViewById(R.id.txtFormat);
            txtLanguage = itemView.findViewById(R.id.txtLanguage);
            txtPriceAdult = itemView.findViewById(R.id.txtPriceAdult);
            txtPriceChild = itemView.findViewById(R.id.txtPriceChild);
            txtPriceStudent = itemView.findViewById(R.id.txtPriceStudent);

            // Ánh xạ ID bổ sung từ XML
            txtCinemaName = itemView.findViewById(R.id.txtCinemaName);
            txtPriceVIP = itemView.findViewById(R.id.txtPriceVIP);
        }
    }
}