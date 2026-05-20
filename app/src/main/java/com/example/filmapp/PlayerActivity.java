package com.example.filmapp;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class PlayerActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        String title      = getIntent().getStringExtra("movie_title");
        String rating     = getIntent().getStringExtra("movie_rating");
        String year       = getIntent().getStringExtra("movie_year");
        String genre      = getIntent().getStringExtra("movie_genre");
        String trailerUrl = getIntent().getStringExtra("trailer_url");

        android.widget.TextView tvTitle  = findViewById(R.id.tv_movie_title);
        android.widget.TextView tvRating = findViewById(R.id.tv_movie_rating);
        android.widget.TextView tvYear   = findViewById(R.id.tv_movie_year);
        android.widget.TextView tvGenre  = findViewById(R.id.tv_movie_genre);

        if (title  != null && tvTitle  != null) tvTitle.setText(title);
        if (rating != null && tvRating != null) tvRating.setText("⭐ " + rating);
        if (year   != null && tvYear   != null) tvYear.setText(year);
        if (genre  != null && tvGenre  != null) tvGenre.setText(genre);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        setupWebView(trailerUrl);
    }

    private void setupWebView(String trailerUrl) {
        webView = findViewById(R.id.web_view_player);
        if (webView == null) return;

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        String embedUrl = convertToEmbedUrl(trailerUrl);

        // Pakai loadDataWithBaseURL agar YouTube tidak diblokir
        String html = "<!DOCTYPE html><html><head>"
                + "<style>body{margin:0;padding:0;background:#000;}"
                + "iframe{width:100%;height:100%;border:none;}</style>"
                + "</head><body>"
                + "<iframe src='" + embedUrl + "'"
                + " allow='autoplay; encrypted-media'"
                + " allowfullscreen></iframe>"
                + "</body></html>";

        webView.loadDataWithBaseURL(
                "https://www.youtube.com",
                html,
                "text/html",
                "utf-8",
                null
        );
    }

    private String convertToEmbedUrl(String youtubeUrl) {
        if (youtubeUrl == null || youtubeUrl.isEmpty()) return "";

        String videoId = "";
        if (youtubeUrl.contains("v=")) {
            videoId = youtubeUrl.substring(youtubeUrl.indexOf("v=") + 2);
            if (videoId.contains("&")) {
                videoId = videoId.substring(0, videoId.indexOf("&"));
            }
        } else if (youtubeUrl.contains("youtu.be/")) {
            videoId = youtubeUrl.substring(youtubeUrl.indexOf("youtu.be/") + 9);
        }

        return "https://www.youtube.com/embed/" + videoId + "?autoplay=1&rel=0";
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
    }
}