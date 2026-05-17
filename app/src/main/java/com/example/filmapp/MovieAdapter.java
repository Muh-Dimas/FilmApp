package com.example.filmapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.filmapp.model.Film;

import java.util.List;

/**
 * Adapter untuk menampilkan daftar film menggunakan RecyclerView.
 * Mengimplementasikan Glide untuk memuat gambar poster.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Film> movieList;
    private Context context;

    public MovieAdapter(List<Film> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Film movie = movieList.get(position);

        // 1. Tampilkan Judul
        holder.tvTitle.setText(movie.getJudul());

        // 2. Implementasi Glide Berantai (Chaining) untuk Gambar Poster
        // Menggunakan Rounded Corners untuk estetika Netflix
        Glide.with(context)
                .load(movie.getGambar_poster()) // URL Poster Tegak dari Model Andika
                .placeholder(android.R.drawable.ic_menu_gallery) // Placeholder bawaan
                .error(android.R.drawable.stat_notify_error)    // Image jika error
                .transform(new CenterCrop(), new RoundedCorners(24)) // Transformasi: Potong Tengah & Sudut Melengkung
                .into(holder.ivMoviePoster);

        // 3. Navigasi ke DetailActivity saat item diklik
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("EXTRA_FILM", movie);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMoviePoster;
        TextView tvTitle;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMoviePoster = itemView.findViewById(R.id.iv_movie_poster);
            tvTitle = itemView.findViewById(R.id.tv_movie_title);
        }
    }
}
