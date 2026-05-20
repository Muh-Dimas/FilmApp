package com.example.filmapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AllMoviesActivity extends AppCompatActivity {

    public static final String EXTRA_FILTER = "filter";
    public static final String EXTRA_TITLE  = "title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_movies);

        String filter = getIntent().getStringExtra(EXTRA_FILTER);
        String title  = getIntent().getStringExtra(EXTRA_TITLE);

        // Toolbar
        TextView tvTitle   = findViewById(R.id.tv_toolbar_title);
        ImageButton btnBack = findViewById(R.id.btn_back);
        tvTitle.setText(title != null ? title : "Semua Film");
        btnBack.setOnClickListener(v -> finish());

        // Ambil data dari repository (cache sudah ada dari HomeFragment)
        MovieRepository repository = new MovieRepository();
        List<Movie> movies = new ArrayList<>();

        if (filter != null) {
            switch (filter) {
                case "trending": movies = repository.getTrendingMovies(); break;
                case "popular":  movies = repository.getPopularMovies();  break;
                case "new":      movies = repository.getNewReleases();    break;
                default:         movies = repository.getAllMovies();       break;
            }
        } else {
            movies = repository.getAllMovies();
        }

        // Tampilkan empty state kalau kosong
        TextView tvEmpty = findViewById(R.id.tv_empty);
        tvEmpty.setVisibility(movies.isEmpty() ? View.VISIBLE : View.GONE);

        // RecyclerView grid 2 kolom
        RecyclerView recyclerView = findViewById(R.id.rv_all_movies);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Movie> finalMovies = movies;
        FilmAdapter adapter = new FilmAdapter(finalMovies, movie -> {
            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movie.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }
}