package com.example.ticketbookingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.models.FoodDraftItem;

import java.util.ArrayList;

public class FoodPreviewAdapter extends RecyclerView.Adapter<FoodPreviewAdapter.VH> {

    private final ArrayList<FoodDraftItem> items;

    public FoodPreviewAdapter(ArrayList<FoodDraftItem> items) {
        this.items = (items == null) ? new ArrayList<>() : items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food_preview, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        FoodDraftItem it = items.get(position);
        if (it == null) return;

        h.tvName.setText(it.name);
        h.tvQty.setText("x" + it.quantity);

        int lineTotal = it.quantity * it.unitPrice;
        h.tvPrice.setText(lineTotal + " ₸");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvQty, tvPrice;

        VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvFoodName);
            tvQty  = itemView.findViewById(R.id.tvFoodQty);
            tvPrice= itemView.findViewById(R.id.tvFoodLinePrice);
        }
    }
}
