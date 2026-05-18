package com.example.filmapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import com.example.filmapp.databinding.ActivitySearchBinding;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;
    private MovieRepository repository;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new MovieRepository();

        binding.btnBack.setOnClickListener(v -> finish());

        showEmptyState();
        setupSearch();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        adapter = new MovieAdapter(repository.getAllMovies(), movie -> {});
        binding.rvSearchResults.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvSearchResults.setAdapter(adapter);
    }

    private void setupSearch() {
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                binding.btnClear.setVisibility(query.isEmpty() ? View.GONE : View.VISIBLE);

                if (query.isEmpty()) {
                    showEmptyState();
                } else {
                    List<Movie> results = repository.searchMovies(query);
                    if (results.isEmpty()) {
                        showEmptyState();
                    } else {
                        showResults(results);
                    }
                }
            }
        });

        binding.btnClear.setOnClickListener(v -> binding.etSearch.setText(""));
    }

    private void showEmptyState() {
        binding.emptyState.setVisibility(View.VISIBLE);
        binding.rvSearchResults.setVisibility(View.GONE);
    }

    private void showResults(List<Movie> results) {
        binding.emptyState.setVisibility(View.GONE);
        binding.rvSearchResults.setVisibility(View.VISIBLE);
        adapter.updateData(results);
    }
}