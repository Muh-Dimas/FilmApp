package com.example.filmapp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieRepository {

    private final List<Movie> allMovies = new ArrayList<>();
    private final List<Clip> allClips = new ArrayList<>();

    public MovieRepository() {
        seedMovies();
        seedClips();
    }

    private void seedMovies() {
        allMovies.add(new Movie("1", "Interstellar", "Sci-Fi", "8.6", "2014", "2j 49m",
                "https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg",
                "Seorang mantan pilot NASA bergabung dengan tim ilmuwan yang melakukan perjalanan melalui wormhole di dekat Saturnus untuk mencari planet baru yang bisa dihuni manusia demi menyelamatkan peradaban yang hampir punah.",
                1));

        allMovies.add(new Movie("2", "Inception", "Action", "8.8", "2010", "2j 28m",
                "https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg",
                "Dom Cobb adalah pencuri paling terampil dalam seni ekstraksi: mencuri rahasia berharga dari dalam alam bawah sadar saat pikiran sedang paling rentan dalam keadaan bermimpi.",
                1));

        allMovies.add(new Movie("3", "The Dark Knight", "Action", "9.0", "2008", "2j 32m",
                "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
                "Batman meningkatkan perangnya melawan kejahatan. Dengan bantuan Letnan Gordon dan Jaksa Harvey Dent, Batman berusaha membersihkan Gotham dari kriminalitas. Namun seorang kriminal bernama Joker menimbulkan kekacauan baru.",
                1));

        allMovies.add(new Movie("4", "Avengers: Endgame", "Action", "8.4", "2019", "3j 2m",
                "https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
                "Setelah Thanos menghancurkan separuh kehidupan di alam semesta, para Avengers yang tersisa harus melakukan hal mustahil. Mereka berkumpul sekali lagi untuk membalikkan tindakan Thanos dan memulihkan keseimbangan alam semesta.",
                1));

        allMovies.add(new Movie("5", "Parasite", "Drama", "8.5", "2019", "2j 12m",
                "https://image.tmdb.org/t/p/w500/7IiTTgloROVKrn/pP7kOhyLcBU.jpg",
                "Keluarga Kim yang miskin secara bertahap menyusup ke kehidupan keluarga kaya Park yang naif. Sebuah tragedi tak terduga terjadi ketika kepentingan dua keluarga dari kelas sosial yang sangat berbeda saling berbenturan.",
                1));

        allMovies.add(new Movie("6", "Dune: Part One", "Sci-Fi", "8.0", "2021", "2j 35m",
                "https://image.tmdb.org/t/p/w500/d5NXSklXo0qyIYkgV48Z9d3Tznk.jpg",
                "Paul Atreides, seorang pemuda cerdas dan berbakat, harus bepergian ke planet paling berbahaya di alam semesta untuk memastikan masa depan keluarga dan rakyatnya. Di sana, ia menemukan takdirnya yang sesungguhnya.",
                2));

        allMovies.add(new Movie("7", "The Shawshank Redemption", "Drama", "9.3", "1994", "2j 22m",
                "https://image.tmdb.org/t/p/w500/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg",
                "Andy Dufresne, seorang bankir, dihukum dua kali penjara seumur hidup atas pembunuhan istrinya dan kekasihnya. Ia harus bertahan di penjara Shawshank sambil mempertahankan harapan melalui kebaikan dan kecerdikannya.",
                1));

        allMovies.add(new Movie("8", "Oppenheimer", "Drama", "8.3", "2023", "3j 0m",
                "https://image.tmdb.org/t/p/w500/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg",
                "Kisah J. Robert Oppenheimer, fisikawan teoretis Amerika yang memimpin Proyek Manhattan yang mengembangkan bom atom pertama selama Perang Dunia II dan dampak moralnya yang menghantui seumur hidup.",
                1));

        allMovies.add(new Movie("9", "Spider-Man: No Way Home", "Action", "8.3", "2021", "2j 28m",
                "https://image.tmdb.org/t/p/w500/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
                "Peter Parker meminta Doctor Strange untuk membantu memulihkan identitas rahasianya. Namun mantra yang dilakukan salah membuka portal multiverse, mendatangkan penjahat-penjahat dari dimensi lain ke dunia Peter.",
                1));

        allMovies.add(new Movie("10", "Everything Everywhere", "Komedi", "7.8", "2022", "2j 19m",
                "https://image.tmdb.org/t/p/w500/w3LxiVYdWWRvEVdn5RYq6jIqkb1.jpg",
                "Seorang wanita imigran Tionghoa-Amerika yang kewalahan harus terhubung dengan kehidupan paralel di alam semesta lain untuk mencegah kekuatan jahat menghancurkan multiverse.",
                1));

        allMovies.get(0).setFavorite(true);
        allMovies.get(2).setFavorite(true);
        allMovies.get(6).setFavorite(true);
    }

    private void seedClips() {
        allClips.add(new Clip("c1", "Interstellar - Official Trailer", "Sci-Fi",
                "2:45", "12.5M", "https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg"));
        allClips.add(new Clip("c2", "Inception - Dream Scene", "Action",
                "3:20", "8.2M", "https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg"));
        allClips.add(new Clip("c3", "The Dark Knight - Joker Scene", "Action",
                "4:10", "20.1M", "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg"));
        allClips.add(new Clip("c4", "Parasite - Staircase Scene", "Drama",
                "2:55", "5.6M", "https://image.tmdb.org/t/p/w500/7IiTTgloROVKrn/pP7kOhyLcBU.jpg"));
        allClips.add(new Clip("c5", "Avengers - Final Battle", "Action",
                "5:30", "35.0M", "https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg"));
        allClips.add(new Clip("c6", "Dune - Spice Scene", "Sci-Fi",
                "3:45", "9.8M", "https://image.tmdb.org/t/p/w500/d5NXSklXo0qyIYkgV48Z9d3Tznk.jpg"));
        allClips.add(new Clip("c7", "Oppenheimer - Trinity Scene", "Drama",
                "4:00", "15.3M", "https://image.tmdb.org/t/p/w500/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg"));
        allClips.add(new Clip("c8", "Everything Everywhere - Best Moments", "Komedi",
                "3:10", "7.4M", "https://image.tmdb.org/t/p/w500/w3LxiVYdWWRvEVdn5RYq6jIqkb1.jpg"));
    }

    public Movie getFeaturedMovie() { return allMovies.get(0); }
    public List<Movie> getTrendingMovies() { return allMovies.subList(0, 5); }
    public List<Movie> getPopularMovies() { return allMovies.subList(2, 8); }
    public List<Movie> getNewReleases() { return allMovies.subList(5, allMovies.size()); }
    public List<Movie> getAllMovies() { return allMovies; }

    public Movie getMovieById(String id) {
        return allMovies.stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Movie> getFavoriteMovies() {
        return allMovies.stream().filter(Movie::isFavorite).collect(Collectors.toList());
    }

    public List<Movie> searchMovies(String query) {
        String q = query.toLowerCase();
        return allMovies.stream()
                .filter(m -> m.getTitle().toLowerCase().contains(q)
                        || m.getGenre().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public List<Clip> getAllClips() { return allClips; }
}