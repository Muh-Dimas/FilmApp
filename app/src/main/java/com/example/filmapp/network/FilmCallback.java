package com.example.filmapp.network;

import com.example.filmapp.model.Film;
import java.util.List;

public interface FilmCallback {
    void onSuccess(List<Film> filmList);
    void onFailure(String errorMessage);
}