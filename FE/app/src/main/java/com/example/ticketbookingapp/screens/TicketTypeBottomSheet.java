package com.example.ticketbookingapp.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.ticketbookingapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class TicketTypeBottomSheet extends BottomSheetDialogFragment {

    private String seatId;
    private OnTicketTypeSelectedListener listener;

    public interface OnTicketTypeSelectedListener {
        void onTypeSelected(String type, int price);
    }

    public TicketTypeBottomSheet(String seatId, OnTicketTypeSelectedListener listener) {
        this.seatId = seatId;
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_ticket_type, container, false);

        TextView txtInfo = view.findViewById(R.id.txtSelectedSeatInfo);
        txtInfo.setText("Seat " + seatId);

        // Xử lý sự kiện click cho từng loại vé
        view.findViewById(R.id.layoutTypeAdult).setOnClickListener(v -> select(2200));
        view.findViewById(R.id.layoutTypeChild).setOnClickListener(v -> select(1000));
        view.findViewById(R.id.layoutTypeStudent).setOnClickListener(v -> select(1500));

        return view;
    }

    private void select(int price) {
        listener.onTypeSelected("Selected", price);
        dismiss(); // Đóng BottomSheet
    }
}