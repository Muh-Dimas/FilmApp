package com.example.filmapp.model;

import java.io.Serializable;

/**
 * BAGIAN ANDIKA: Model data sesuai instruksi tugas spesifik.
 * Variabel dibuat mirip persis dengan kunci di JSON.
 */
public class Film implements Serializable {
    // Sesuai tips: id dan tanggal_rilis menggunakan long
    private long id;
    private long tanggal_rilis;
    
    // Sesuai tips: lainnya menggunakan String agar mudah di-parse
    private String judul;
    private String ringkasan;
    private String gambar_poster;
    private String gambar_sampul;
    private String skor_rating;
    private String kategori;
    private String url_trailer;

    // Constructor Kosong (Wajib untuk GSON)
    public Film() {
    }

    // Constructor Lengkap (Opsional tapi berguna)
    public Film(long id, String judul, String ringkasan, String gambar_poster, 
                String gambar_sampul, String skor_rating, String kategori, 
                long tanggal_rilis, String url_trailer) {
        this.id = id;
        this.judul = judul;
        this.ringkasan = ringkasan;
        this.gambar_poster = gambar_poster;
        this.gambar_sampul = gambar_sampul;
        this.skor_rating = skor_rating;
        this.kategori = kategori;
        this.tanggal_rilis = tanggal_rilis;
        this.url_trailer = url_trailer;
    }

    // Getter dan Setter (Nama disesuaikan dengan variabel baru)
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; }

    public String getRingkasan() { return ringkasan; }
    public void setRingkasan(String ringkasan) { this.ringkasan = ringkasan; }

    public String getGambar_poster() { return gambar_poster; }
    public void setGambar_poster(String gambar_poster) { this.gambar_poster = gambar_poster; }

    public String getGambar_sampul() { return gambar_sampul; }
    public void setGambar_sampul(String gambar_sampul) { this.gambar_sampul = gambar_sampul; }

    public String getSkor_rating() { return skor_rating; }
    public void setSkor_rating(String skor_rating) { this.skor_rating = skor_rating; }

    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }

    public long getTanggal_rilis() { return tanggal_rilis; }
    public void setTanggal_rilis(long tanggal_rilis) { this.tanggal_rilis = tanggal_rilis; }

    public String getUrl_trailer() { return url_trailer; }
    public void setUrl_trailer(String url_trailer) { this.url_trailer = url_trailer; }
}
