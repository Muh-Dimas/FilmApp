package com.example.filmapp.controller;

import android.os.Handler;
import android.os.Looper;

import com.example.filmapp.model.Film;
import com.example.filmapp.network.ApiClient;
import com.example.filmapp.network.FilmCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FilmController {

    private ApiClient apiClient;
    // Handler untuk kembali ke Main Thread (UI thread)
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    public FilmController() {
        apiClient = ApiClient.getInstance();
    }

    // Ambil semua film
    public void getAllFilm(FilmCallback filmCallback) {
        apiClient.getAllFilm(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Kembalikan error ke UI thread
                mainHandler.post(() ->
                        filmCallback.onFailure("Gagal koneksi: " + e.getMessage())
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    mainHandler.post(() ->
                            filmCallback.onFailure("Error server: " + response.code())
                    );
                    return;
                }

                String jsonString = response.body().string();
                List<Film> filmList = parseFilmList(jsonString);

                // Kembalikan data ke UI thread
                mainHandler.post(() ->
                        filmCallback.onSuccess(filmList)
                );
            }
        });
    }

    // Ambil film by ID
    public void getFilmById(String id, FilmCallback filmCallback) {
        apiClient.getFilmById(id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(() ->
                        filmCallback.onFailure("Gagal koneksi: " + e.getMessage())
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    mainHandler.post(() ->
                            filmCallback.onFailure("Error server: " + response.code())
                    );
                    return;
                }

                String jsonString = response.body().string();
                List<Film> filmList = new ArrayList<>();

                try {
                    JSONObject obj = new JSONObject(jsonString);
                    Film film = parseFilm(obj);
                    filmList.add(film);
                } catch (Exception e) {
                    mainHandler.post(() ->
                            filmCallback.onFailure("Gagal parse data: " + e.getMessage())
                    );
                    return;
                }

                mainHandler.post(() ->
                        filmCallback.onSuccess(filmList)
                );
            }
        });
    }

    // Parse JSONArray → List<Film>
    private List<Film> parseFilmList(String jsonString) {
        List<Film> filmList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                filmList.add(parseFilm(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filmList;
    }

    // Parse satu JSONObject → Film
    // Perhatian: skor_rating di API bisa String atau int!
    private Film parseFilm(JSONObject obj) throws Exception {
        String id = obj.getString("id");
        String judul = obj.getString("judul");
        String ringkasan = obj.getString("ringkasan");
        String gambarPoster = obj.getString("gambar_poster");
        String gambarSampul = obj.getString("gambar_sampul");
        long tanggalRilis = obj.getLong("tanggal_rilis");
        String kategori = obj.getString("kategori");
        String urlTrailer = obj.getString("url_trailer");

        // Handle skor_rating yang bisa String atau int
        int skorRating = 0;
        try {
            skorRating = obj.getInt("skor_rating");
        } catch (Exception e) {
            String skorStr = obj.getString("skor_rating");
            try {
                skorRating = Integer.parseInt(skorStr);
            } catch (Exception ex) {
                skorRating = 0;
            }
        }

        return new Film(id, judul, ringkasan, gambarPoster,
                gambarSampul, tanggalRilis, skorRating,
                kategori, urlTrailer);
    }
}