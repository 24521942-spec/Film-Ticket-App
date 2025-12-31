package com.example.ticketbookingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieVH> {

    public interface OnMovieClickListener {
        void onClick(Movie movie);
    }

    private final Context context;
    private final OnMovieClickListener listener;
    private final List<Movie> data = new ArrayList<>();

    public MovieAdapter(Context context, OnMovieClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setData(List<Movie> movies) {
        data.clear();
        if (movies != null) data.addAll(movies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieVH h, int position) {
        Movie m = data.get(position);

        h.txtTitle.setText(m.getTitle());
        h.txtGenre.setText(m.getGenre());
        h.txtRating.setText(String.valueOf(m.getRating()));

        // Nếu bạn chưa có placeholder_movie thì đổi thành android built-in:
        // .placeholder(android.R.drawable.ic_menu_report_image)
        Glide.with(context)
                .load(m.getPosterUrl())
                .placeholder(android.R.drawable.ic_menu_report_image)
                .error(android.R.drawable.ic_menu_report_image)
                .centerCrop()
                .into(h.imgPoster);

        h.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onClick(m);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class MovieVH extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView txtTitle, txtGenre, txtRating;

        MovieVH(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtGenre = itemView.findViewById(R.id.txtGenre);
            txtRating = itemView.findViewById(R.id.txtRating);
        }
    }
}
