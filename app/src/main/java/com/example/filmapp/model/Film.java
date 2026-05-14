package com.example.filmapp.model;

import com.google.gson.annotations.SerializedName;

public class Film {

    @SerializedName("id")
    private String id;

    @SerializedName("judul")
    private String judul;

    @SerializedName("ringkasan")
    private String ringkasan;

    @SerializedName("gambar_poster")
    private String gambarPoster;

    @SerializedName("gambar_sampul")
    private String gambarSampul;

    @SerializedName("tanggal_rilis")
    private long tanggalRilis;

    @SerializedName("skor_rating")
    private int skorRating;

    @SerializedName("kategori")
    private String kategori;

    @SerializedName("url_trailer")
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