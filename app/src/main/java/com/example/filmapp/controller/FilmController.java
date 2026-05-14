package com.example.filmapp.controller;

import android.os.Handler;
import android.os.Looper;

import com.example.filmapp.model.Film;
import com.example.filmapp.network.ApiClient;
import com.example.filmapp.network.FilmCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FilmController {

    private ApiClient apiClient;
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private Gson gson = new Gson();

    public FilmController() {
        apiClient = ApiClient.getInstance();
    }

    // Ambil semua film
    public void getAllFilm(FilmCallback filmCallback) {
        apiClient.getAllFilm(new Callback() {
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

                // GSON parse otomatis JSON → List<Film>
                Type listType = new TypeToken<List<Film>>(){}.getType();
                List<Film> filmList = gson.fromJson(jsonString, listType);

                // Kalau hasil null, kirim list kosong
                if (filmList == null) filmList = new ArrayList<>();

                List<Film> finalFilmList = filmList;
                mainHandler.post(() ->
                        filmCallback.onSuccess(finalFilmList)
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

                // GSON parse satu objek Film
                Film film = gson.fromJson(jsonString, Film.class);

                List<Film> filmList = new ArrayList<>();
                if (film != null) filmList.add(film);

                List<Film> finalFilmList = filmList;
                mainHandler.post(() ->
                        filmCallback.onSuccess(finalFilmList)
                );
            }
        });
    }
}