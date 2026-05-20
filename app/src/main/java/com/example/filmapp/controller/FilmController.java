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
                try {
                    Type listType = new TypeToken<List<Film>>() {}.getType();
                    List<Film> filmList = gson.fromJson(jsonString, listType);
                    if (filmList == null) filmList = new ArrayList<>();

                    List<Film> filteredList = new ArrayList<>();
                    for (Film film : filmList) {
                        if (film != null && film.getJudul() != null) {
                            filteredList.add(film);
                        }
                    }
                    List<Film> finalList = filteredList;
                    mainHandler.post(() -> filmCallback.onSuccess(finalList));
                } catch (Exception e) {
                    mainHandler.post(() ->
                            filmCallback.onFailure("Gagal parse data: " + e.getMessage())
                    );
                }
            }
        });
    }

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
                try {
                    Film film = gson.fromJson(jsonString, Film.class);
                    List<Film> filmList = new ArrayList<>();
                    if (film != null) filmList.add(film);
                    mainHandler.post(() -> filmCallback.onSuccess(filmList));
                } catch (Exception e) {
                    mainHandler.post(() ->
                            filmCallback.onFailure("Gagal parse data: " + e.getMessage())
                    );
                }
            }
        });
    }

    // ── Tambah film baru ─────────────────────────────────────────────────────
    public interface AddFilmCallback {
        void onSuccess(Film film);
        void onFailure(String message);
    }

    public void addFilm(Film film, AddFilmCallback callback) {
        String jsonBody = gson.toJson(film);
        apiClient.addFilm(jsonBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(() ->
                        callback.onFailure("Gagal koneksi: " + e.getMessage())
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    mainHandler.post(() ->
                            callback.onFailure("Error server: " + response.code())
                    );
                    return;
                }
                String jsonString = response.body().string();
                try {
                    Film addedFilm = gson.fromJson(jsonString, Film.class);
                    mainHandler.post(() -> callback.onSuccess(addedFilm));
                } catch (Exception e) {
                    mainHandler.post(() ->
                            callback.onFailure("Gagal parse response: " + e.getMessage())
                    );
                }
            }
        });
    }

    // ── Hapus film by ID ─────────────────────────────────────────────────────
    public interface DeleteFilmCallback {
        void onSuccess();
        void onFailure(String message);
    }

    public void deleteFilm(String id, DeleteFilmCallback callback) {
        apiClient.deleteFilm(id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(() ->
                        callback.onFailure("Gagal koneksi: " + e.getMessage())
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    mainHandler.post(() ->
                            callback.onFailure("Error server: " + response.code())
                    );
                    return;
                }
                mainHandler.post(callback::onSuccess);
            }
        });
    }
}