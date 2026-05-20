package com.example.filmapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.filmapp.controller.FilmController;
import com.example.filmapp.model.Film;

public class AddFilmActivity extends AppCompatActivity {

    private EditText etJudul, etRingkasan, etKategori, etPoster,
            etSampul, etRating, etTanggal, etTrailer;
    private Button      btnSimpan;
    private ImageButton btnBatal;      // ← FIX: ImageButton, bukan Button
    private ProgressBar progressBar;

    private FilmController filmController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_film);

        filmController = new FilmController();
        bindViews();
        setupListeners();
    }

    private void bindViews() {
        etJudul     = findViewById(R.id.et_judul);
        etRingkasan = findViewById(R.id.et_ringkasan);
        etKategori  = findViewById(R.id.et_kategori);
        etPoster    = findViewById(R.id.et_poster);
        etSampul    = findViewById(R.id.et_sampul);
        etRating    = findViewById(R.id.et_rating);
        etTanggal   = findViewById(R.id.et_tanggal);
        etTrailer   = findViewById(R.id.et_trailer);
        btnSimpan   = findViewById(R.id.btn_simpan);
        btnBatal    = findViewById(R.id.btn_batal);   // ← ImageButton
        progressBar = findViewById(R.id.progress_bar);
    }

    private void setupListeners() {
        btnBatal.setOnClickListener(v -> finish());
        btnSimpan.setOnClickListener(v -> {
            if (validateInput()) submitFilm();
        });
    }

    private boolean validateInput() {
        String judul = etJudul.getText().toString().trim();
        String ringkasan = etRingkasan.getText().toString().trim();

        if (judul.isEmpty()) {
            etJudul.setError("Judul wajib diisi");
            etJudul.requestFocus();
            return false;
        }
        if (ringkasan.isEmpty()) {
            etRingkasan.setError("Ringkasan wajib diisi");
            etRingkasan.requestFocus();
            return false;
        }

        // Validasi rating jika diisi
        String rating = etRating.getText().toString().trim();
        if (!rating.isEmpty()) {
            try {
                double r = Double.parseDouble(rating);
                if (r < 0 || r > 10) {
                    etRating.setError("Rating harus antara 0 - 10");
                    etRating.requestFocus();
                    return false;
                }
            } catch (NumberFormatException e) {
                etRating.setError("Format rating tidak valid");
                etRating.requestFocus();
                return false;
            }
        }

        return true;
    }

    private void submitFilm() {
        setLoading(true);

        Film film = new Film(
                etJudul.getText().toString().trim(),
                etRingkasan.getText().toString().trim(),
                etKategori.getText().toString().trim(),
                etPoster.getText().toString().trim(),
                etSampul.getText().toString().trim(),
                etRating.getText().toString().trim(),
                etTanggal.getText().toString().trim(),
                etTrailer.getText().toString().trim()
        );

        filmController.addFilm(film, new FilmController.AddFilmCallback() {
            @Override
            public void onSuccess(Film addedFilm) {
                setLoading(false);
                Toast.makeText(
                        AddFilmActivity.this,
                        "\"" + addedFilm.getJudul() + "\" berhasil ditambahkan!",
                        Toast.LENGTH_LONG
                ).show();
                MovieRepository.clearCache(); // hapus cache → HomeFragment reload
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(String message) {
                setLoading(false);
                Toast.makeText(
                        AddFilmActivity.this,
                        "Gagal menambahkan film: " + message,
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void setLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        btnSimpan.setEnabled(!isLoading);
        btnBatal.setEnabled(!isLoading);

        // Disable semua input saat loading
        etJudul.setEnabled(!isLoading);
        etRingkasan.setEnabled(!isLoading);
        etKategori.setEnabled(!isLoading);
        etPoster.setEnabled(!isLoading);
        etSampul.setEnabled(!isLoading);
        etRating.setEnabled(!isLoading);
        etTanggal.setEnabled(!isLoading);
        etTrailer.setEnabled(!isLoading);
    }
}