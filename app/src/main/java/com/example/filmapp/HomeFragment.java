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
import com.example.filmapp.R;
import com.example.filmapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private MovieRepository repository;

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
    }

    private void setupAnimations() {
        binding.getRoot().startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in));
    }

    private void setupRecyclerViews() {
        LinearLayoutManager horizontalLayout = new LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayout2 = new LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayout3 = new LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false);

        MovieAdapter trendingAdapter = new MovieAdapter(repository.getTrendingMovies(), movie -> openDetail(movie));
        MovieAdapter popularAdapter = new MovieAdapter(repository.getPopularMovies(), movie -> openDetail(movie));
        MovieAdapter newAdapter = new MovieAdapter(repository.getNewReleases(), movie -> openDetail(movie));

        binding.rvTrending.setLayoutManager(horizontalLayout);
        binding.rvTrending.setAdapter(trendingAdapter);

        binding.rvPopular.setLayoutManager(horizontalLayout2);
        binding.rvPopular.setAdapter(popularAdapter);

        binding.rvNewRelease.setLayoutManager(horizontalLayout3);
        binding.rvNewRelease.setAdapter(newAdapter);
    }

    private void setupClickListeners() {
        binding.btnSearch.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), SearchActivity.class))
        );

        binding.btnPlayFeatured.setOnClickListener(v -> {
            Movie featured = repository.getFeaturedMovie();
            openDetail(featured);
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