package com.example.ticketbookingapp.screens;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.adapters.MovieAdapter;
import com.example.ticketbookingapp.models.Movie;
import com.example.ticketbookingapp.utils.GridSpacingItemDecoration;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView rvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rvMovies = findViewById(R.id.rvMovies);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        rvMovies.setLayoutManager(manager);

        int spacing = (int) (16 * getResources().getDisplayMetrics().density);
// includeEdge = false  -> cạnh ngoài lấy từ padding RV, giữa 2 card = 16dp
        rvMovies.addItemDecoration(new GridSpacingItemDecoration(2, spacing, false));



        List<Movie> demo = new ArrayList<>();
        demo.add(new Movie("The Batman", "Action", "", 8.1f));
        demo.add(new Movie("Uncharted", "Adventure", "", 7.9f));
        demo.add(new Movie("The Batman", "Action", "", 8.1f));
        demo.add(new Movie("Uncharted", "Adventure", "", 7.9f));
        demo.add(new Movie("The Batman", "Action", "", 8.1f));
        demo.add(new Movie("Uncharted", "Adventure", "", 7.9f));
        demo.add(new Movie("The Batman", "Action", "", 8.1f));
        demo.add(new Movie("Uncharted", "Adventure", "", 7.9f));
        demo.add(new Movie("The Batman", "Action", "", 8.1f));
        demo.add(new Movie("Uncharted", "Adventure", "", 7.9f));
        demo.add(new Movie("The Batman", "Action", "", 8.1f));
        demo.add(new Movie("Uncharted", "Adventure", "", 7.9f));
        demo.add(new Movie("The Batman", "Action", "", 8.1f));
        demo.add(new Movie("Uncharted", "Adventure", "", 7.9f));

        MovieAdapter adapter = new MovieAdapter(demo);
        rvMovies.setAdapter(adapter);
    }
}
