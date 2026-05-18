package com.example.filmapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.example.filmapp.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUserInfo();
        setupClickListeners();
    }

    private void setupUserInfo() {
        binding.tvUserName.setText("Alex Johnson");
        binding.tvUserEmail.setText("alex@example.com");
    }

    private void setupClickListeners() {
        binding.rowProfile.getRoot().setOnClickListener(v ->
                Toast.makeText(requireContext(), "Edit Profil", Toast.LENGTH_SHORT).show()
        );
        binding.rowSubscription.getRoot().setOnClickListener(v ->
                Toast.makeText(requireContext(), "Kelola Langganan", Toast.LENGTH_SHORT).show()
        );
        binding.rowLanguage.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Pilih Bahasa", Toast.LENGTH_SHORT).show()
        );

        binding.switchDarkMode.setOnCheckedChangeListener((btn, isChecked) ->
                Toast.makeText(requireContext(),
                        isChecked ? "Mode gelap aktif" : "Mode terang aktif",
                        Toast.LENGTH_SHORT).show()
        );

        binding.btnLogout.setOnClickListener(v -> showLogoutDialog());
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Keluar")
                .setMessage("Apakah kamu yakin ingin keluar dari akun?")
                .setPositiveButton("Keluar", (dialog, which) -> {
                    startActivity(new Intent(requireContext(), LandingActivity.class));
                    requireActivity().finishAffinity();
                })
                .setNegativeButton("Batal", null)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}