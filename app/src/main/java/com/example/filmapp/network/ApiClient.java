package com.example.filmapp.network;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ApiClient {

    private static final String BASE_URL =
            "https://68ff8dfbe02b16d1753e765d.mockapi.io";

    private static ApiClient instance;
    private OkHttpClient client;

    // Singleton — satu instance saja
    private ApiClient() {
        client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    // GET semua film
    public void getAllFilm(Callback callback) {
        Request request = new Request.Builder()
                .url(BASE_URL + "/film")
                .get()
                .build();

        client.newCall(request).enqueue(callback);
    }

    // GET film by ID
    public void getFilmById(String id, Callback callback) {
        Request request = new Request.Builder()
                .url(BASE_URL + "/film/" + id)
                .get()
                .build();

        client.newCall(request).enqueue(callback);
    }
}