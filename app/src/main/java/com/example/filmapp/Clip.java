package com.example.filmapp;

public class Clip {
    private String id;
    private String title;
    private String genre;
    private String duration;
    private String thumbnailUrl;
    private String views;

    public Clip(String id, String title, String genre, String duration,
                String thumbnailUrl, String views) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.thumbnailUrl = thumbnailUrl;
        this.views = views;
    }

    public static Clip fromMovie(Movie movie) {
        return new Clip(
                movie.getId(),
                movie.getTitle(),
                movie.getGenre() != null ? movie.getGenre() : "",
                movie.getDuration() != null && !movie.getDuration().isEmpty()
                        ? movie.getDuration() : "2j 0m",
                movie.getPosterUrl(),
                "0"
        );
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public String getDuration() { return duration; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public String getViews() { return views; }
}