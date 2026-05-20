package com.example.filmapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.filmapp.databinding.ActivityMovieDetailBinding;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID = "extra_movie_id";

    private ActivityMovieDetailBinding binding;
    private Movie movie;
    private boolean isSynopsisExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String movieId = getIntent().getStringExtra(EXTRA_MOVIE_ID);
        MovieRepository repository = new MovieRepository();
        movie = repository.getMovieById(movieId);

        if (movie == null) {
            Toast.makeText(this, "Film tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        populateData();
        setupClickListeners();
        playEnterAnimation();
    }

    private void populateData() {
        Glide.with(this)
                .load(movie.getPosterUrl())
                .placeholder(R.drawable.bg_glass_card)
                .centerCrop()
                .into(binding.imgPoster);

        binding.tvTitle.setText(movie.getTitle());
        binding.tvRating.setText(movie.getRating());
        binding.tvYear.setText(movie.getYear());
        binding.tvDuration.setText(movie.getDuration());
        binding.tvGenreBadge.setText(movie.getGenre());

        binding.tvRatingBig.setText(movie.getRating());
        binding.tvEpisodesBig.setText(String.valueOf(movie.getEpisodeCount()));
        binding.tvGenreBig.setText(movie.getGenre());

        binding.tvSynopsis.setText(movie.getSynopsis());

        binding.tvYearDetail.setText(movie.getYear());
        binding.tvDurationDetail.setText(movie.getDuration());
        binding.tvGenreDetail.setText(movie.getGenre());
        binding.tvRatingDetail.setText("⭐ " + movie.getRating() + " / 10");

        int eps = movie.getEpisodeCount();
        binding.tvEpisodeDetail.setText(eps + " Episode");

        updateFavoriteIcon();
        updateDownloadIcon();
    }

    private void setupClickListeners() {
        binding.btnBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.slide_up);
        });

        binding.btnFavorite.setOnClickListener(v -> {
            movie.setFavorite(!movie.isFavorite());
            updateFavoriteIcon();
            String msg = movie.isFavorite() ? "Ditambahkan ke favorit ❤" : "Dihapus dari favorit";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            binding.btnFavorite.startAnimation(
                    AnimationUtils.loadAnimation(this, R.anim.scale_in));
        });

        binding.btnDownload.setOnClickListener(v -> {
            movie.setDownloaded(!movie.isDownloaded());
            updateDownloadIcon();
            String msg = movie.isDownloaded() ? "Mengunduh film..." : "Unduhan dibatalkan";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        binding.btnDownloadAction.setOnClickListener(v -> {
            movie.setDownloaded(!movie.isDownloaded());
            updateDownloadIcon();
            String msg = movie.isDownloaded() ? "Mengunduh film..." : "Unduhan dibatalkan";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        binding.tvReadMore.setOnClickListener(v -> {
            isSynopsisExpanded = !isSynopsisExpanded;
            if (isSynopsisExpanded) {
                binding.tvSynopsis.setMaxLines(Integer.MAX_VALUE);
                binding.tvSynopsis.setEllipsize(null);
                binding.tvReadMore.setText("Tutup");
            } else {
                binding.tvSynopsis.setMaxLines(4);
                binding.tvSynopsis.setEllipsize(android.text.TextUtils.TruncateAt.END);
                binding.tvReadMore.setText("Baca selengkapnya");
            }
        });

        // Tombol Tonton Sekarang — buka trailer di PlayerActivity
        binding.btnWatchNow.setOnClickListener(v -> {
            String trailerUrl = movie.getUrlTrailer();
            if (trailerUrl != null && !trailerUrl.isEmpty()) {
                Intent intent = new Intent(this, PlayerActivity.class);
                intent.putExtra("movie_title",  movie.getTitle());
                intent.putExtra("movie_rating", movie.getRating());
                intent.putExtra("movie_year",   movie.getYear());
                intent.putExtra("movie_genre",  movie.getGenre());
                intent.putExtra("trailer_url",  trailerUrl);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.slide_up);
            } else {
                Toast.makeText(this, "Trailer tidak tersedia", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateFavoriteIcon() {
        if (movie.isFavorite()) {
            binding.btnFavorite.setImageResource(android.R.drawable.btn_star_big_on);
            binding.btnFavorite.setColorFilter(
                    getResources().getColor(R.color.teal_primary, null));
        } else {
            binding.btnFavorite.setImageResource(android.R.drawable.btn_star_big_off);
            binding.btnFavorite.setColorFilter(
                    getResources().getColor(R.color.text_secondary, null));
        }
    }

    private void updateDownloadIcon() {
        if (movie.isDownloaded()) {
            binding.btnDownload.setColorFilter(
                    getResources().getColor(R.color.teal_primary, null));
            binding.btnDownloadAction.setText("✓");
        } else {
            binding.btnDownload.setColorFilter(
                    getResources().getColor(R.color.text_secondary, null));
            binding.btnDownloadAction.setText("⬇");
        }
    }

    private void playEnterAnimation() {
        binding.scrollContent.startAnimation(
                AnimationUtils.loadAnimation(this, R.anim.fade_in));
    }
}