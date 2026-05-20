package com.example.filmapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.filmapp.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private MovieRepository repository;
    private FilmAdapter trendingAdapter, popularAdapter, newAdapter;
    private static final int MAX_RETRY = 3;
    private int retryCount = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repository = new MovieRepository();

        setupAnimations();
        setupRecyclerViews();
        setupClickListeners();
        loadData();
    }

    private void setupAnimations() {
        binding.getRoot().startAnimation(
                AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in));
    }

    private void setupRecyclerViews() {
        trendingAdapter = new FilmAdapter(new ArrayList<>(), movie -> openDetail(movie));
        popularAdapter  = new FilmAdapter(new ArrayList<>(), movie -> openDetail(movie));
        newAdapter      = new FilmAdapter(new ArrayList<>(), movie -> openDetail(movie));

        binding.rvTrending.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvTrending.setAdapter(trendingAdapter);

        binding.rvPopular.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvPopular.setAdapter(popularAdapter);

        binding.rvNewRelease.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvNewRelease.setAdapter(newAdapter);
    }

    private void loadData() {
        List<Movie> cached = repository.getAllMovies();
        if (!cached.isEmpty()) {
            populateAdapters();
            return;
        }

        showLoading(true);
        showRetry(false);
        retryCount = 0;
        loadFromApi();
    }

    private void loadFromApi() {
        repository.loadFromApi(new MovieRepository.OnMoviesLoadedListener() {
            @Override
            public void onLoaded(List<Movie> movies) {
                if (getActivity() == null || binding == null) return;
                showLoading(false);
                retryCount = 0;
                populateAdapters();
            }

            @Override
            public void onError(String message) {
                if (getActivity() == null || binding == null) return;
                if (retryCount < MAX_RETRY) {
                    retryCount++;
                    loadFromApi();
                } else {
                    showLoading(false);
                    showRetry(true);
                }
            }
        });
    }

    private void populateAdapters() {
        trendingAdapter.updateData(repository.getTrendingMovies());
        popularAdapter.updateData(repository.getPopularMovies());
        newAdapter.updateData(repository.getNewReleases());

        Movie featured = repository.getFeaturedMovie();
        if (featured != null) {
            binding.tvFeaturedTitle.setText(featured.getTitle());
            binding.tvFeaturedRating.setText("⭐ " + featured.getRating());
            binding.tvFeaturedGenre.setText(featured.getGenre());
            Glide.with(this)
                    .load(featured.getGambarSampul() != null
                            ? featured.getGambarSampul()
                            : featured.getPosterUrl())
                    .centerCrop()
                    .into(binding.imgFeatured);
        }
    }

    private void showLoading(boolean show) {
        if (binding == null) return;
        binding.loadingProgress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void showRetry(boolean show) {
        if (binding == null) return;
        binding.btnRetry.setVisibility(show ? View.VISIBLE : View.GONE);
        if (show) {
            binding.btnRetry.setOnClickListener(v -> {
                showRetry(false);
                showLoading(true);
                retryCount = 0;
                loadFromApi();
            });
        }
    }

    private void setupClickListeners() {
        // Tombol search
        binding.btnSearch.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), SearchActivity.class)));

        // Tombol play featured banner
        binding.btnPlayFeatured.setOnClickListener(v -> {
            Movie featured = repository.getFeaturedMovie();
            if (featured != null) openDetail(featured);
        });

        // Klik gambar featured banner
        binding.cardFeatured.setOnClickListener(v -> {
            Movie featured = repository.getFeaturedMovie();
            if (featured != null) openDetail(featured);
        });

        // Lihat Semua — Trending
        binding.tvSeeAllTrending.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), AllMoviesActivity.class);
            intent.putExtra(AllMoviesActivity.EXTRA_FILTER, "trending");
            intent.putExtra(AllMoviesActivity.EXTRA_TITLE, "Trending Sekarang");
            startActivity(intent);
        });

        // Lihat Semua — Popular
        binding.tvSeeAllPopular.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), AllMoviesActivity.class);
            intent.putExtra(AllMoviesActivity.EXTRA_FILTER, "popular");
            intent.putExtra(AllMoviesActivity.EXTRA_TITLE, "Film Populer");
            startActivity(intent);
        });

        // Lihat Semua — New Release
        binding.tvSeeAllNew.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), AllMoviesActivity.class);
            intent.putExtra(AllMoviesActivity.EXTRA_FILTER, "new");
            intent.putExtra(AllMoviesActivity.EXTRA_TITLE, "Terbaru");
            startActivity(intent);
        });
    }

    private void openDetail(Movie movie) {
        Intent intent = new Intent(requireContext(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movie.getId());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}