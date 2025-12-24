package com.example.ticketbookingapp.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.models.Movie;
import com.example.ticketbookingapp.network.dto.ApiMovie;
import com.example.ticketbookingapp.screens.MovieDetailActivity;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final List<Movie> movies;
    private final List<ApiMovie> apiMovies;

    public MovieAdapter(List<Movie> movies, List<ApiMovie> apiMovies) {
        this.movies = movies;
        this.apiMovies = apiMovies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        ApiMovie apiMovie = (apiMovies != null && apiMovies.size() > position) ? apiMovies.get(position) : null;

        holder.txtTitle.setText(movie.getTitle());
        holder.txtGenre.setText(movie.getGenre());
        holder.txtRating.setText(String.valueOf(movie.getRating()));

        String url = movie.getPosterUrl();

        // Không muốn sửa placeholder thì cứ bỏ placeholder đi để khỏi crash
        if (url == null || url.trim().isEmpty()) {
            holder.imgPoster.setImageResource(R.drawable.ic_back_arrow); // hoặc bất kỳ drawable có sẵn
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imgPoster);
        }

        holder.itemView.setOnClickListener(v -> {
            int filmId = apiMovies.get(position).getFilmId(); // QUAN TRỌNG
            Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);

            intent.putExtra("film_id", filmId);
            intent.putExtra("movie_title", movie.getTitle()); // optional
            intent.putExtra("movie_genre", movie.getGenre());
            intent.putExtra("movie_rating", movie.getRating());
            intent.putExtra("movie_poster", movie.getPosterUrl());

            v.getContext().startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
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
