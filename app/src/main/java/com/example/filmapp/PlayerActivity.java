package com.example.filmapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PlayerActivity extends AppCompatActivity {

    private WebView webView;
    private FrameLayout fullscreenContainer;
    private View customView;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private boolean isFullscreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Layar selalu menyala selama putar trailer
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_player);

        fullscreenContainer = findViewById(R.id.fullscreen_container);

        String title      = getIntent().getStringExtra("movie_title");
        String rating     = getIntent().getStringExtra("movie_rating");
        String year       = getIntent().getStringExtra("movie_year");
        String genre      = getIntent().getStringExtra("movie_genre");
        String trailerUrl = getIntent().getStringExtra("trailer_url");

        TextView tvTitle  = findViewById(R.id.tv_movie_title);
        TextView tvRating = findViewById(R.id.tv_movie_rating);
        TextView tvYear   = findViewById(R.id.tv_movie_year);
        TextView tvGenre  = findViewById(R.id.tv_movie_genre);

        if (title  != null && tvTitle  != null) tvTitle.setText(title);
        if (rating != null && tvRating != null) tvRating.setText("⭐ " + rating);
        if (year   != null && tvYear   != null) tvYear.setText(year);
        if (genre  != null && tvGenre  != null) tvGenre.setText(genre);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        setupWebView(trailerUrl);
    }

    @SuppressLint("SetJavaScriptEnabled")
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
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);

        // FIX error 152: paksa WebView pakai User Agent Chrome browser
        settings.setUserAgentString(
                "Mozilla/5.0 (Linux; Android 10; Mobile) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/120.0.0.0 Mobile Safari/537.36"
        );

        // WebChromeClient untuk handle fullscreen
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                if (customView != null) {
                    callback.onCustomViewHidden();
                    return;
                }
                customView = view;
                customViewCallback = callback;
                isFullscreen = true;

                // Sembunyikan webview, tampilkan fullscreen container
                webView.setVisibility(View.GONE);
                fullscreenContainer.setVisibility(View.VISIBLE);
                fullscreenContainer.addView(view);

                // Paksa landscape saat fullscreen
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                hideSystemUI();
            }

            @Override
            public void onHideCustomView() {
                if (customView == null) return;

                // Kembali ke portrait
                fullscreenContainer.removeView(customView);
                fullscreenContainer.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                customView = null;
                customViewCallback.onCustomViewHidden();
                isFullscreen = false;

                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                showSystemUI();
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Cegah redirect keluar dari WebView
                view.loadUrl(url);
                return true;
            }
        });

        if (trailerUrl == null || trailerUrl.isEmpty()) {
            showNoTrailer();
            return;
        }

        if (isYoutubeUrl(trailerUrl)) {
            String videoId = extractYoutubeId(trailerUrl);
            if (videoId == null || videoId.isEmpty()) {
                showNoTrailer();
                return;
            }
            loadYoutubeEmbed(videoId);

        } else if (trailerUrl.endsWith(".mp4") || trailerUrl.contains(".mp4?")) {
            loadMp4Direct(trailerUrl);

        } else {
            showNoTrailer();
        }
    }

    private boolean isYoutubeUrl(String url) {
        return url.contains("youtube.com") || url.contains("youtu.be");
    }

    private String extractYoutubeId(String url) {
        try {
            if (url.contains("youtu.be/")) {
                String id = url.substring(url.indexOf("youtu.be/") + 9);
                if (id.contains("?")) id = id.substring(0, id.indexOf("?"));
                if (id.contains("&")) id = id.substring(0, id.indexOf("&"));
                return id.trim();
            }
            if (url.contains("/embed/")) {
                String id = url.substring(url.indexOf("/embed/") + 7);
                if (id.contains("?")) id = id.substring(0, id.indexOf("?"));
                if (id.contains("&")) id = id.substring(0, id.indexOf("&"));
                return id.trim();
            }
            if (url.contains("v=")) {
                String id = url.substring(url.indexOf("v=") + 2);
                if (id.contains("&")) id = id.substring(0, id.indexOf("&"));
                if (id.contains("?")) id = id.substring(0, id.indexOf("?"));
                return id.trim();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    private void loadYoutubeEmbed(String videoId) {
        // modestbranding=1 hilangkan logo YouTube
        // rel=0 hilangkan video rekomendasi
        // iv_load_policy=3 hilangkan anotasi
        String embedUrl = "https://www.youtube.com/embed/" + videoId
                + "?autoplay=1"
                + "&controls=1"
                + "&rel=0"
                + "&modestbranding=1"
                + "&playsinline=0"       // 0 = boleh fullscreen native
                + "&iv_load_policy=3"
                + "&showinfo=0"
                + "&fs=1";              // fs=1 aktifkan tombol fullscreen

        String html = "<!DOCTYPE html><html><head>"
                + "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
                + "<style>"
                + "* { margin:0; padding:0; box-sizing:border-box; }"
                + "body { background:#000; width:100vw; height:100vh; overflow:hidden; }"
                + "iframe {"
                + "  position:absolute; top:0; left:0;"
                + "  width:100%; height:100%; border:none;"
                + "}"
                + "</style></head><body>"
                + "<iframe src='" + embedUrl + "'"
                + " allow='autoplay; encrypted-media; fullscreen; accelerometer; gyroscope'"
                + " allowfullscreen='true'"
                + " webkitallowfullscreen='true'"
                + " frameborder='0'>"
                + "</iframe>"
                + "</body></html>";

        webView.loadDataWithBaseURL(
                "https://www.youtube.com", html, "text/html", "utf-8", null);
    }

    private void loadMp4Direct(String mp4Url) {
        String html = "<!DOCTYPE html><html><head>"
                + "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
                + "<style>"
                + "* { margin:0; padding:0; }"
                + "body { background:#000; display:flex; align-items:center;"
                + "justify-content:center; height:100vh; }"
                + "video { width:100%; max-height:100vh; }"
                + "</style></head><body>"
                + "<video controls autoplay playsinline>"
                + "<source src='" + mp4Url + "' type='video/mp4'>"
                + "Browser tidak mendukung video ini."
                + "</video>"
                + "</body></html>";

        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
    }

    private void showNoTrailer() {
        Toast.makeText(this, "Trailer tidak tersedia untuk film ini",
                Toast.LENGTH_LONG).show();
        String html = "<!DOCTYPE html><html><head>"
                + "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
                + "<style>"
                + "body { background:#111; display:flex; flex-direction:column;"
                + "align-items:center; justify-content:center; height:100vh; margin:0; }"
                + "p { color:#aaa; font-size:16px; font-family:sans-serif;"
                + "text-align:center; padding:20px; }"
                + "span { font-size:48px; }"
                + "</style></head><body>"
                + "<span>🎬</span>"
                + "<p>Trailer tidak tersedia untuk film ini</p>"
                + "</body></html>";
        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
    }

    private void showSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
    }

    @Override
    public void onBackPressed() {
        // Kalau sedang fullscreen, keluar fullscreen dulu
        if (isFullscreen && customViewCallback != null) {
            customViewCallback.onCustomViewHidden();
        } else {
            super.onBackPressed();
            finish();
        }
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