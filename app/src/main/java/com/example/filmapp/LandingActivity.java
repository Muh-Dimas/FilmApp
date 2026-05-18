package com.example.filmapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatActivity;
import com.example.filmapp.R;
import com.example.filmapp.databinding.ActivityLandingBinding;

public class LandingActivity extends AppCompatActivity {

    private ActivityLandingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupAnimations();
        setupClickListeners();
    }

    private void setupAnimations() {
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);

        binding.tvLogo.startAnimation(fadeIn);

        slideUp.setStartOffset(300);
        binding.tvTitle.startAnimation(slideUp);

        slideUp.setStartOffset(450);
        binding.tvSubtitle.startAnimation(slideUp);

        slideUp.setStartOffset(600);
        binding.btnGetStarted.startAnimation(slideUp);
        binding.btnLogin.startAnimation(slideUp);
    }

    private void setupClickListeners() {
        binding.btnGetStarted.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
            overridePendingTransition(R.anim.slide_up, R.anim.fade_in);
        });

        binding.btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            overridePendingTransition(R.anim.slide_up, R.anim.fade_in);
        });
    }
}