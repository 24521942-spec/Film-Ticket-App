package com.example.ticketbookingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.models.Food;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private List<Food> list;
    private OnFoodAddListener listener;

    // QUAN TRỌNG: Đổi int thành Food để sửa lỗi "Cannot access fields"
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

        // Truyền nguyên đối tượng f vào listener
        holder.btnAdd.setOnClickListener(v -> listener.onAdd(f));
    }

    @Override
    public int getItemCount() { return list.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtDesc, txtPrice;
        Button btnAdd;
        public ViewHolder(View v) {
            super(v);
            txtName = v.findViewById(R.id.txtFoodName);
            txtDesc = v.findViewById(R.id.txtFoodDesc);
            txtPrice = v.findViewById(R.id.txtFoodPrice);
            btnAdd = v.findViewById(R.id.btnAddFood);
        }
    }
}