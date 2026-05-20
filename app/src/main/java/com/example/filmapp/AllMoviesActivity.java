package com.example.filmapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AllMoviesActivity extends AppCompatActivity {

    public static final String EXTRA_FILTER = "filter";
    public static final String EXTRA_TITLE  = "title";
    private static final int REQUEST_ADD_FILM = 100;

    private FilmAdapter adapter;
    private MovieRepository repository;
    private String currentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_movies);

        currentFilter = getIntent().getStringExtra(EXTRA_FILTER);
        String title  = getIntent().getStringExtra(EXTRA_TITLE);

        // Toolbar
        TextView    tvTitle = findViewById(R.id.tv_toolbar_title);
        ImageButton btnBack = findViewById(R.id.btn_back);
        tvTitle.setText(title != null ? title : "Semua Film");
        btnBack.setOnClickListener(v -> finish());

        // FAB tambah film
        FloatingActionButton fabAdd = findViewById(R.id.fab_add_film);
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddFilmActivity.class);
            startActivityForResult(intent, REQUEST_ADD_FILM);
        });

        // Setup RecyclerView
        repository = new MovieRepository();
        RecyclerView recyclerView = findViewById(R.id.rv_all_movies);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new FilmAdapter(new ArrayList<>(), movie -> {
            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movie.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        loadMovies();
    }

    private void loadMovies() {
        List<Movie> movies = new ArrayList<>();

        if (currentFilter != null) {
            switch (currentFilter) {
                case "trending": movies = repository.getTrendingMovies(); break;
                case "popular":  movies = repository.getPopularMovies();  break;
                case "new":      movies = repository.getNewReleases();    break;
                default:         movies = repository.getAllMovies();       break;
            }
        } else {
            movies = repository.getAllMovies();
        }

        // Empty state
        TextView tvEmpty = findViewById(R.id.tv_empty);
        tvEmpty.setVisibility(movies.isEmpty() ? View.VISIBLE : View.GONE);

        adapter.updateData(movies);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_FILM && resultCode == RESULT_OK) {
            // Cache sudah dihapus di AddFilmActivity, reload dari API
            repository.loadFromApi(new MovieRepository.OnMoviesLoadedListener() {
                @Override
                public void onLoaded(List<Movie> movies) {
                    loadMovies();
                    Toast.makeText(AllMoviesActivity.this,
                            "Daftar film diperbarui!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(AllMoviesActivity.this,
                            "Gagal refresh: " + message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}