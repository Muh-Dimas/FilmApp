package com.example.filmapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.filmapp.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter untuk menampilkan daftar film menggunakan RecyclerView.
 * Mengimplementasikan Glide untuk memuat gambar poster.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }

    private List<Movie> movies;
    private final OnMovieClickListener listener;

    public MovieAdapter(List<Movie> movies, OnMovieClickListener listener) {
        this.movies = new ArrayList<>(movies);
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_card, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie, listener);
        holder.itemView.startAnimation(
                AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.scale_in)
        );
    }

    @Override
    public int getItemCount() { return movies.size(); }

    public void updateData(List<Movie> newMovies) {
        this.movies = new ArrayList<>(newMovies);
        notifyDataSetChanged();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvGenre, tvRating;
        ImageButton btnFavorite;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvGenre = itemView.findViewById(R.id.tv_genre);
            tvRating = itemView.findViewById(R.id.tv_rating);
            btnFavorite = itemView.findViewById(R.id.btn_favorite);
        }

        void bind(Movie movie, OnMovieClickListener listener) {
            tvTitle.setText(movie.getTitle());
            tvGenre.setText(movie.getGenre());
            tvRating.setText(movie.getRating());

            Glide.with(itemView.getContext())
                    .load(movie.getPosterUrl())
                    .placeholder(R.drawable.bg_glass_card)
                    .centerCrop()
                    .into(imgPoster);

            btnFavorite.setImageResource(movie.isFavorite()
                    ? android.R.drawable.btn_star_big_on
                    : android.R.drawable.btn_star_big_off);

            btnFavorite.setOnClickListener(v -> {
                movie.setFavorite(!movie.isFavorite());
                btnFavorite.setImageResource(movie.isFavorite()
                        ? android.R.drawable.btn_star_big_on
                        : android.R.drawable.btn_star_big_off);
            });

            // Klik item → ke halaman Detail
            itemView.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(
                        itemView.getContext(),
                        MovieDetailActivity.class
                );
                intent.putExtra(
                        MovieDetailActivity.EXTRA_MOVIE_ID,
                        movie.getId()
                );
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
