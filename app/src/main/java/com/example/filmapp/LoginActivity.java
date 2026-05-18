package com.example.filmapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.filmapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupClickListeners();
    }

    private void setupClickListeners() {
        binding.btnBack.setOnClickListener(v -> finish());

        binding.tvForgotPassword.setOnClickListener(v ->
                Toast.makeText(this, "Link reset password dikirim ke email", Toast.LENGTH_SHORT).show()
        );

        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                binding.etEmail.setError("Email tidak boleh kosong");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                binding.etPassword.setError("Password tidak boleh kosong");
                return;
            }
            if (password.length() < 6) {
                binding.etPassword.setError("Password minimal 6 karakter");
                return;
            }

            // Simulate login success
            startActivity(new Intent(this, MainActivity.class));
            finishAffinity();
        });

        binding.tvGoRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });
    }
}