package com.example.ticketbookingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.models.Food;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private List<Food> list;
    private OnFoodAddListener listener;

    public interface OnFoodAddListener {
        void onAdd(Food foodItem);
    }

    public FoodAdapter(List<Food> list, OnFoodAddListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food f = list.get(position);

        holder.txtName.setText(f.name);
        holder.txtDesc.setText(f.description);
        holder.txtPrice.setText(f.price + " ₸");

        holder.imgFood.setImageResource(getFoodImageRes(f.name));

        holder.btnAdd.setOnClickListener(v -> {
            if (listener != null) listener.onAdd(f);
        });
    }

    @Override
    public int getItemCount() { return list == null ? 0 : list.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtDesc, txtPrice;
        Button btnAdd;
        ImageView imgFood;

        public ViewHolder(View v) {
            super(v);
            imgFood = v.findViewById(R.id.imgFood);
            txtName = v.findViewById(R.id.txtFoodName);
            txtDesc = v.findViewById(R.id.txtFoodDesc);
            txtPrice = v.findViewById(R.id.txtFoodPrice);
            btnAdd = v.findViewById(R.id.btnAddFood);
        }
    }

    private int getFoodImageRes(String name) {
        if (name == null) return R.drawable.popcorn;

        String n = name.trim().toLowerCase();

        if (n.contains("combo")) return R.drawable.combopopcorn;
        if (n.contains("coca") || n.contains("coke")) return R.drawable.cocacola;
        if (n.contains("popcorn")) return R.drawable.popcorn;

        return R.drawable.popcorn;
    }
}
