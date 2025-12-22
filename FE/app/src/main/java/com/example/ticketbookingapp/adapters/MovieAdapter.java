package com.example.ticketbookingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movies;

    public MovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.txtTitle.setText(movie.getTitle());
        holder.txtGenre.setText(movie.getGenre());
        holder.txtRating.setText(String.valueOf(movie.getRating()));
        holder.itemView.setOnClickListener(v -> {
            // v.getContext() lấy ra Context từ chính cái View đang hiển thị
            android.content.Intent intent = new android.content.Intent(v.getContext(),
                    com.example.ticketbookingapp.screens.MovieDetailActivity.class);

            // Gửi dữ liệu phim sang màn hình Detail (để bên kia biết là đang xem phim nào)
            intent.putExtra("movie_title", movie.getTitle());
            intent.putExtra("movie_genre", movie.getGenre());
            intent.putExtra("movie_rating", movie.getRating());

            v.getContext().startActivity(intent);
        });

    }

    @Override
    public  int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView txtTitle, txtGenre, txtRating;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtGenre = itemView.findViewById(R.id.txtGenre);
            txtRating = itemView.findViewById(R.id.txtRating);
        }
    }
}
