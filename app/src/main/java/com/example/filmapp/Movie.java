package com.example.filmapp;

public class Movie {
    private String id;
    private String title;
    private String genre;
    private String rating;
    private String year;
    private String duration;
    private String posterUrl;
    private String synopsis;
    private int episodeCount;
    private boolean isFavorite;
    private boolean isDownloaded;
    private String urlTrailer;
    private String gambarSampul;

    public Movie(String id, String title, String genre, String rating,
                 String year, String duration, String posterUrl,
                 String synopsis, int episodeCount) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.year = year;
        this.duration = duration;
        this.posterUrl = posterUrl;
        this.synopsis = synopsis;
        this.episodeCount = episodeCount;
        this.isFavorite = false;
        this.isDownloaded = false;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public String getRating() { return rating; }
    public String getYear() { return year; }
    public String getDuration() { return duration; }
    public String getPosterUrl() { return posterUrl; }
    public String getSynopsis() { return synopsis; }
    public int getEpisodeCount() { return episodeCount; }
    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
    public boolean isDownloaded() { return isDownloaded; }
    public void setDownloaded(boolean downloaded) { isDownloaded = downloaded; }
    public String getUrlTrailer() { return urlTrailer; }
    public void setUrlTrailer(String urlTrailer) { this.urlTrailer = urlTrailer; }
    public String getGambarSampul() { return gambarSampul; }
    public void setGambarSampul(String gambarSampul) { this.gambarSampul = gambarSampul; }
}