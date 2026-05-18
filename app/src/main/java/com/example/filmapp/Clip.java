package com.example.filmapp;

public class Clip {
    private String id;
    private String title;
    private String genre;
    private String duration;
    private String views;
    private String thumbnailUrl;

    public Clip(String id, String title, String genre, String duration,
                String views, String thumbnailUrl) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.views = views;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public String getDuration() { return duration; }
    public String getViews() { return views; }
    public String getThumbnailUrl() { return thumbnailUrl; }
}