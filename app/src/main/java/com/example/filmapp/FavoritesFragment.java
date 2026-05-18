package com.example.filmapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import com.example.filmapp.databinding.FragmentFavoritesBinding;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;
    private MovieRepository repository;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repository = new MovieRepository();
        loadFavorites();
    }

    private void loadFavorites() {
        List<Movie> favorites = repository.getFavoriteMovies();

        if (favorites.isEmpty()) {
            binding.emptyState.setVisibility(View.VISIBLE);
            binding.rvFavorites.setVisibility(View.GONE);
            binding.tvCount.setText("0 film tersimpan");
        } else {
            binding.emptyState.setVisibility(View.GONE);
            binding.rvFavorites.setVisibility(View.VISIBLE);
            binding.tvCount.setText(favorites.size() + " film tersimpan");

            MovieAdapter adapter = new MovieAdapter(favorites, movie -> {});
            binding.rvFavorites.setLayoutManager(new GridLayoutManager(requireContext(), 2));
            binding.rvFavorites.setAdapter(adapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}