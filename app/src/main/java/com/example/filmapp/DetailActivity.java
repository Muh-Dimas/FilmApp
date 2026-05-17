package com.example.filmapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.filmapp.model.Film;

/**
 * Bagian Tugas: DetailActivity untuk menampilkan informasi lengkap film
 */
public class DetailActivity extends AppCompatActivity {

    private ImageView imgSampul;
    private TextView tvJudul, tvKategori, tvRating, tvRingkasan;
    private Button btnTrailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 1. Inisialisasi View dari activity_detail.xml
        imgSampul = findViewById(R.id.imgSampul);
        tvJudul = findViewById(R.id.tvJudulDetail);
        tvKategori = findViewById(R.id.tvKategoriDetail);
        tvRating = findViewById(R.id.tvRatingDetail);
        tvRingkasan = findViewById(R.id.tvRingkasanDetail);
        btnTrailer = findViewById(R.id.btnTrailer);

        // 2. Mengambil data Film yang dikirim dari Intent (MainActivity/Adapter)
        Film film = (Film) getIntent().getSerializableExtra("EXTRA_FILM");

        if (film != null) {
            // 3. Menampilkan data ke UI
            tvJudul.setText(film.getJudul());
            tvKategori.setText(film.getKategori());
            tvRating.setText("⭐ " + film.getSkor_rating());
            tvRingkasan.setText(film.getRingkasan());

            // Implementasi Glide Berantai (Chaining) untuk Gambar Sampul
            Glide.with(this)
                    .load(film.getGambar_sampul())
                    .placeholder(android.R.drawable.ic_menu_gallery) // Placeholder bawaan Android
                    .error(android.R.drawable.stat_notify_error)    // Error bawaan Android
                    .transform(new CenterCrop(), new RoundedCorners(24)) // Estetika: Sudut Melengkung
                    .into(imgSampul);

            // 4. Logika klik tombol Trailer
            btnTrailer.setOnClickListener(v -> {
                String trailerUrl = film.getUrl_trailer();
                if (trailerUrl != null && !trailerUrl.isEmpty()) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
                    startActivity(browserIntent);
                }
            });
        }
    }
}
