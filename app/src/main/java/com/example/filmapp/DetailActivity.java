package com.example.filmapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    private ImageView imgSampul;
    private TextView tvJudul, tvKategori, tvRating, tvRingkasan;
    private Button btnTrailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Detail Film");
        }

        // Sesuaikan ID dengan activity_detail.xml
        imgSampul = findViewById(R.id.imgSampul);
        tvJudul = findViewById(R.id.tvJudulDetail);
        tvKategori = findViewById(R.id.tvKategoriDetail);
        tvRating = findViewById(R.id.tvRatingDetail);
        tvRingkasan = findViewById(R.id.tvRingkasanDetail);
        btnTrailer = findViewById(R.id.btnTrailer);

        String judul = getIntent().getStringExtra("judul");
        String ringkasan = getIntent().getStringExtra("ringkasan");
        String gambarSampul = getIntent().getStringExtra("gambar_sampul");
        String kategori = getIntent().getStringExtra("kategori");
        String skorRating = getIntent().getStringExtra("skor_rating");
        String urlTrailer = getIntent().getStringExtra("url_trailer");

        tvJudul.setText(judul);
        tvKategori.setText(kategori);
        tvRating.setText("⭐ " + skorRating + "/100");
        tvRingkasan.setText(ringkasan);

        Glide.with(this)
                .load(gambarSampul)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_gallery)
                .into(imgSampul);

        btnTrailer.setOnClickListener(v -> {
            if (urlTrailer != null && !urlTrailer.isEmpty()
                    && urlTrailer.startsWith("http")) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(urlTrailer));
                startActivity(intent);
            } else {
                Toast.makeText(this,
                        "Trailer tidak tersedia",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}