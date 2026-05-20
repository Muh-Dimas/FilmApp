package com.example.filmapp.network;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ApiClient {

    private static final String BASE_URL =
            "https://68ff8dfbe02b16d1753e765d.mockapi.io";

    private static final MediaType JSON =
            MediaType.parse("application/json; charset=utf-8");

    private static ApiClient instance;
    private OkHttpClient client;

    private ApiClient() {
        client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    public void getAllFilm(Callback callback) {
        Request request = new Request.Builder()
                .url(BASE_URL + "/film")
                .get()
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void getFilmById(String id, Callback callback) {
        Request request = new Request.Builder()
                .url(BASE_URL + "/film/" + id)
                .get()
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void addFilm(String jsonBody, Callback callback) {
        RequestBody body = RequestBody.create(jsonBody, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + "/film")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    // ── Hapus film by ID ─────────────────────────────────────────────────────
    public void deleteFilm(String id, Callback callback) {
        Request request = new Request.Builder()
                .url(BASE_URL + "/film/" + id)
                .delete()
                .build();
        client.newCall(request).enqueue(callback);
    }
}