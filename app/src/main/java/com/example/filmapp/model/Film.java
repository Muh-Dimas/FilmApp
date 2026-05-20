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

    // Constructor untuk AddFilmActivity (tanpa id, MockAPI generate otomatis)
    public Film(String judul, String ringkasan, String kategori,
                String gambarPoster, String gambarSampul,
                String skorRating, String tanggalRilis, String urlTrailer) {
        this.judul        = judul;
        this.ringkasan    = ringkasan;
        this.kategori     = kategori;
        this.gambarPoster = gambarPoster;
        this.gambarSampul = gambarSampul;
        this.skorRating   = skorRating;
        this.tanggalRilis = tanggalRilis;
        this.urlTrailer   = urlTrailer;
    }

    public String getId()            { return id; }
    public String getJudul()         { return judul; }
    public String getRingkasan()     { return ringkasan; }
    public String getGambarPoster()  { return gambarPoster; }
    public String getGambarSampul()  { return gambarSampul; }
    public String getTanggalRilis()  { return tanggalRilis; }
    public String getSkorRating()    { return skorRating; }
    public String getKategori()      { return kategori; }
    public String getUrlTrailer()    { return urlTrailer; }
}