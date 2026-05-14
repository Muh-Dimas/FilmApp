package com.example.filmapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.filmapp.controller.FilmController;
import com.example.filmapp.model.Film;
import com.example.filmapp.network.FilmCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FilmController filmController;
    private static final String TAG = "FilmApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filmController = new FilmController();

        // Test ambil semua film
        filmController.getAllFilm(new FilmCallback() {
            @Override
            public void onSuccess(List<Film> filmList) {
                Log.d(TAG, "Berhasil! Total film: " + filmList.size());
                Toast.makeText(MainActivity.this,
                        "Berhasil load " + filmList.size() + " film!",
                        Toast.LENGTH_LONG).show();

                // Cek film pertama di log
                if (!filmList.isEmpty()) {
                    Film film = filmList.get(0);
                    Log.d(TAG, "Film pertama: " + film.getJudul());
                    Log.d(TAG, "Rating: " + film.getSkorRating());
                    Log.d(TAG, "Kategori: " + film.getKategori());
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e(TAG, "Error: " + errorMessage);
                Toast.makeText(MainActivity.this,
                        "Gagal: " + errorMessage,
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}