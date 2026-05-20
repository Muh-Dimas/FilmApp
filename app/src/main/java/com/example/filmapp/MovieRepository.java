package com.example.filmapp;

import com.example.filmapp.controller.FilmController;
import com.example.filmapp.model.Film;
import com.example.filmapp.network.FilmCallback;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository {

    private static List<Movie> cachedMovies = new ArrayList<>();

    public interface OnMoviesLoadedListener {
        void onLoaded(List<Movie> movies);
        void onError(String message);
    }

    private static Movie convertToMovie(Film film) {
        Movie movie = new Movie(
                film.getId(),
                film.getJudul(),
                film.getKategori(),
                film.getSkorRating(),
                film.getTanggalRilis(),
                "",
                film.getGambarPoster(),   // ← getter sudah cocok dengan Film.java
                film.getRingkasan(),
                0
        );
        movie.setUrlTrailer(film.getUrlTrailer());
        movie.setGambarSampul(film.getGambarSampul());
        return movie;
    }

    private double parseRatingSafe(String rating) {
        if (rating == null || rating.trim().isEmpty()) return 0.0;
        try {
            String cleaned = rating.trim().replaceAll("[^0-9.]", "");
            if (cleaned.isEmpty()) return 0.0;
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public void loadFromApi(final OnMoviesLoadedListener listener) {
        FilmController controller = new FilmController();
        controller.getAllFilm(new FilmCallback() {
            @Override
            public void onSuccess(List<Film> films) {
                cachedMovies.clear();
                for (Film film : films) {
                    cachedMovies.add(convertToMovie(film));
                }
                listener.onLoaded(new ArrayList<>(cachedMovies));
            }

            @Override
            public void onFailure(String errorMessage) {
                listener.onError(errorMessage);
            }
        });
    }

    // Hapus cache agar HomeFragment reload dari API setelah add film
    public static void clearCache() {
        cachedMovies.clear();
    }

    public List<Movie> getAllMovies()  { return new ArrayList<>(cachedMovies); }

    public List<Clip> getAllClips() {
        List<Clip> clips = new ArrayList<>();
        for (Movie movie : cachedMovies) clips.add(Clip.fromMovie(movie));
        return clips;
    }

    public Movie getMovieById(String id) {
        if (id == null) return null;
        for (Movie movie : cachedMovies) {
            if (id.equals(movie.getId())) return movie;
        }
        return null;
    }

    public Movie getFeaturedMovie() {
        return cachedMovies.isEmpty() ? null : cachedMovies.get(0);
    }

    public List<Movie> getTrendingMovies() {
        List<Movie> result = new ArrayList<>();
        int limit = Math.min(10, cachedMovies.size());
        for (int i = 0; i < limit; i++) result.add(cachedMovies.get(i));
        return result;
    }

    public List<Movie> getPopularMovies() {
        List<Movie> sorted = new ArrayList<>(cachedMovies);
        sorted.sort((a, b) -> Double.compare(
                parseRatingSafe(b.getRating()),
                parseRatingSafe(a.getRating())
        ));
        return sorted.size() > 10 ? new ArrayList<>(sorted.subList(0, 10)) : sorted;
    }

    public List<Movie> getNewReleases() {
        List<Movie> result = new ArrayList<>();
        int size  = cachedMovies.size();
        int start = Math.max(0, size - 10);
        for (int i = start; i < size; i++) result.add(cachedMovies.get(i));
        return result;
    }

    public List<Movie> searchMovies(String query) {
        List<Movie> result = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) return result;
        String lower = query.toLowerCase();
        for (Movie movie : cachedMovies) {
            String title = movie.getTitle() != null ? movie.getTitle().toLowerCase() : "";
            String genre = movie.getGenre() != null ? movie.getGenre().toLowerCase() : "";
            if (title.contains(lower) || genre.contains(lower)) result.add(movie);
        }
        return result;
    }

    public List<Movie> getFavoriteMovies() {
        List<Movie> result = new ArrayList<>();
        for (Movie movie : cachedMovies) {
            if (movie.isFavorite()) result.add(movie);
        }
        return result;
    }
}