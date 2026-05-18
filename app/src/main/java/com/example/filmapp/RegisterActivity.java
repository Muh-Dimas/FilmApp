package com.example.filmapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.filmapp.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupClickListeners();
    }

    private void setupClickListeners() {
        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnRegister.setOnClickListener(v -> {
            String name = binding.etName.getText().toString().trim();
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();
            String confirmPassword = binding.etConfirmPassword.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                binding.etName.setError("Nama tidak boleh kosong");
                return;
            }
            if (TextUtils.isEmpty(email)) {
                binding.etEmail.setError("Email tidak boleh kosong");
                return;
            }
            if (password.length() < 8) {
                binding.etPassword.setError("Password minimal 8 karakter");
                return;
            }
            if (!password.equals(confirmPassword)) {
                binding.etConfirmPassword.setError("Password tidak cocok");
                return;
            }

            Toast.makeText(this, "Akun berhasil dibuat!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finishAffinity();
        });

        binding.tvGoLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}