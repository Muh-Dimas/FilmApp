package com.example.filmapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.filmapp.databinding.FragmentClipsBinding;

public class ClipsFragment extends Fragment {

    private FragmentClipsBinding binding;
    private MovieRepository repository;
    private ClipsAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentClipsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repository = new MovieRepository();

        setupRecyclerView();
        setupChips();
    }

    private void setupRecyclerView() {
        adapter = new ClipsAdapter(repository.getAllClips(), clip -> {
            Intent intent = new Intent(requireContext(), PlayerActivity.class);
            intent.putExtra("movie_title", clip.getTitle());
            intent.putExtra("movie_genre", clip.getGenre());
            startActivity(intent);
        });

        binding.rvClips.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvClips.setAdapter(adapter);
    }

    private void setupChips() {
        binding.chipAll.setOnClickListener(v -> {
            setActiveChip(0);
            adapter.filterByGenre("all");
        });
        binding.chipAction.setOnClickListener(v -> {
            setActiveChip(1);
            adapter.filterByGenre("Action");
        });
        binding.chipDrama.setOnClickListener(v -> {
            setActiveChip(2);
            adapter.filterByGenre("Drama");
        });
        binding.chipComedy.setOnClickListener(v -> {
            setActiveChip(3);
            adapter.filterByGenre("Komedi");
        });
        binding.chipScifi.setOnClickListener(v -> {
            setActiveChip(4);
            adapter.filterByGenre("Sci-Fi");
        });
    }

    private void setActiveChip(int index) {
        int defaultBg = com.example.filmapp.R.drawable.bg_glass_card;
        int activeBg = com.example.filmapp.R.drawable.bg_button_teal;
        int defaultColor = getResources().getColor(com.example.filmapp.R.color.text_secondary, null);
        int activeColor = getResources().getColor(com.example.filmapp.R.color.bg_dark, null);

        binding.chipAll.setBackgroundResource(defaultBg);
        binding.chipAll.setTextColor(defaultColor);
        binding.chipAction.setBackgroundResource(defaultBg);
        binding.chipAction.setTextColor(defaultColor);
        binding.chipDrama.setBackgroundResource(defaultBg);
        binding.chipDrama.setTextColor(defaultColor);
        binding.chipComedy.setBackgroundResource(defaultBg);
        binding.chipComedy.setTextColor(defaultColor);
        binding.chipScifi.setBackgroundResource(defaultBg);
        binding.chipScifi.setTextColor(defaultColor);

        switch (index) {
            case 0: binding.chipAll.setBackgroundResource(activeBg); binding.chipAll.setTextColor(activeColor); break;
            case 1: binding.chipAction.setBackgroundResource(activeBg); binding.chipAction.setTextColor(activeColor); break;
            case 2: binding.chipDrama.setBackgroundResource(activeBg); binding.chipDrama.setTextColor(activeColor); break;
            case 3: binding.chipComedy.setBackgroundResource(activeBg); binding.chipComedy.setTextColor(activeColor); break;
            case 4: binding.chipScifi.setBackgroundResource(activeBg); binding.chipScifi.setTextColor(activeColor); break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}