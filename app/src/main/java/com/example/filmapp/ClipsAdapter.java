package com.example.filmapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.filmapp.R;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClipsAdapter extends RecyclerView.Adapter<ClipsAdapter.ClipViewHolder> {

    public interface OnClipClickListener {
        void onClipClick(Clip clip);
    }

    private List<Clip> clips;
    private final List<Clip> allClips;
    private final OnClipClickListener listener;

    public ClipsAdapter(List<Clip> clips, OnClipClickListener listener) {
        this.allClips = new ArrayList<>(clips);
        this.clips = new ArrayList<>(clips);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_clip_card, parent, false);
        return new ClipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClipViewHolder holder, int position) {
        holder.bind(clips.get(position), listener);
    }

    @Override
    public int getItemCount() { return clips.size(); }

    public void filterByGenre(String genre) {
        if (genre.equals("all")) {
            clips = new ArrayList<>(allClips);
        } else {
            clips = allClips.stream()
                    .filter(c -> c.getGenre().equals(genre))
                    .collect(Collectors.toList());
        }
        notifyDataSetChanged();
    }

    static class ClipViewHolder extends RecyclerView.ViewHolder {
        ImageView imgThumbnail;
        TextView tvTitle, tvGenre, tvDuration, tvViews;
        ImageButton btnPlay;

        ClipViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.img_thumbnail);
            tvTitle = itemView.findViewById(R.id.tv_clip_title);
            tvGenre = itemView.findViewById(R.id.tv_clip_genre);
            tvDuration = itemView.findViewById(R.id.tv_duration);
            tvViews = itemView.findViewById(R.id.tv_clip_views);
            btnPlay = itemView.findViewById(R.id.btn_play);
        }

        void bind(Clip clip, OnClipClickListener listener) {
            tvTitle.setText(clip.getTitle());
            tvGenre.setText(clip.getGenre());
            tvDuration.setText(clip.getDuration());
            tvViews.setText(clip.getViews() + " views");

            Glide.with(itemView.getContext())
                    .load(clip.getThumbnailUrl())
                    .placeholder(R.drawable.bg_glass_card)
                    .centerCrop()
                    .into(imgThumbnail);

            btnPlay.setOnClickListener(v -> listener.onClipClick(clip));
            itemView.setOnClickListener(v -> listener.onClipClick(clip));
        }
    }
}