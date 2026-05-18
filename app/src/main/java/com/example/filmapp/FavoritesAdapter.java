package com.example.filmapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.filmapp.R;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavViewHolder> {

    public interface OnRemoveListener {
        void onRemove(Movie movie, int position);
    }

    private final List<Movie> movies;
    private final OnRemoveListener listener;

    public FavoritesAdapter(List<Movie> movies, OnRemoveListener listener) {
        this.movies = movies;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_card, parent, false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvGenre.setText(movie.getGenre());
        holder.tvRating.setText(movie.getRating());
        holder.btnFavorite.setImageResource(android.R.drawable.btn_star_big_on);

        Glide.with(holder.itemView.getContext())
                .load(movie.getPosterUrl())
                .placeholder(R.drawable.bg_glass_card)
                .centerCrop()
                .into(holder.imgPoster);

        holder.btnFavorite.setOnClickListener(v ->
                listener.onRemove(movie, holder.getAdapterPosition())
        );
    }

    @Override
    public int getItemCount() { return movies.size(); }

    static class FavViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvGenre, tvRating;
        android.widget.ImageButton btnFavorite;

        FavViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvGenre = itemView.findViewById(R.id.tv_genre);
            tvRating = itemView.findViewById(R.id.tv_rating);
            btnFavorite = itemView.findViewById(R.id.btn_favorite);
        }
    }
}