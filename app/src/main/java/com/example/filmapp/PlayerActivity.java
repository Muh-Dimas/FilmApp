package com.example.filmapp;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import com.example.filmapp.databinding.ActivityPlayerBinding;

public class PlayerActivity extends AppCompatActivity {

    private ActivityPlayerBinding binding;
    private ExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String title = getIntent().getStringExtra("movie_title");
        String rating = getIntent().getStringExtra("movie_rating");
        String year = getIntent().getStringExtra("movie_year");
        String genre = getIntent().getStringExtra("movie_genre");

        if (title != null) binding.tvMovieTitle.setText(title);
        if (rating != null) binding.tvMovieRating.setText("⭐ " + rating);
        if (year != null) binding.tvMovieYear.setText(year);
        if (genre != null) binding.tvMovieGenre.setText(genre);

        setupPlayer();
        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void setupPlayer() {
        player = new ExoPlayer.Builder(this).build();
        binding.playerView.setPlayer(player);

        // Sample stream URL - ganti dengan URL film nyata
        MediaItem mediaItem = MediaItem.fromUri(
                Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
        );
        player.setMediaItem(mediaItem);
        player.prepare();
        player.setPlayWhenReady(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) player.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}