package com.example.filmapp.model;

public class Film {
    private String id;
    private String judul;
    private String ringkasan;
    private String gambarPoster;
    private String gambarSampul;
    private long tanggalRilis;
    private int skorRating;
    private String kategori;
    private String urlTrailer;

    // Constructor
    public Film(String id, String judul, String ringkasan,
                String gambarPoster, String gambarSampul,
                long tanggalRilis, int skorRating,
                String kategori, String urlTrailer) {
        this.id = id;
        this.judul = judul;
        this.ringkasan = ringkasan;
        this.gambarPoster = gambarPoster;
        this.gambarSampul = gambarSampul;
        this.tanggalRilis = tanggalRilis;
        this.skorRating = skorRating;
        this.kategori = kategori;
        this.urlTrailer = urlTrailer;
    }

    // Getters
    public String getId() { return id; }
    public String getJudul() { return judul; }
    public String getRingkasan() { return ringkasan; }
    public String getGambarPoster() { return gambarPoster; }
    public String getGambarSampul() { return gambarSampul; }
    public long getTanggalRilis() { return tanggalRilis; }
    public int getSkorRating() { return skorRating; }
    public String getKategori() { return kategori; }
    public String getUrlTrailer() { return urlTrailer; }
}