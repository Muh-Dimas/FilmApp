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
    private String tanggalRilis;

    @SerializedName("skor_rating")
    private String skorRating;

    @SerializedName("kategori")
    private String kategori;

    @SerializedName("url_trailer")
    private String urlTrailer;

    public String getId() { return id; }
    public String getJudul() { return judul; }
    public String getRingkasan() { return ringkasan; }
    public String getGambarPoster() { return gambarPoster; }
    public String getGambarSampul() { return gambarSampul; }
    public String getTanggalRilis() { return tanggalRilis; }
    public String getSkorRating() { return skorRating; }
    public String getKategori() { return kategori; }
    public String getUrlTrailer() { return urlTrailer; }
}