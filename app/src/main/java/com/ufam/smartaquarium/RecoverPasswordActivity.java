package com.ufam.smartaquarium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.ufam.smartaquarium.databinding.ActivityRecoverPasswordBinding;

public class RecoverPasswordActivity extends AppCompatActivity {

    private ActivityRecoverPasswordBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecoverPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.forgotBtn.setOnClickListener(v -> validateData());
    }
    private void validateData() {
        String email = binding.emailET.getText().toString().trim();

        if (!email.isEmpty()) {
            recoverAccountFirebase(email);
        }
        else {
            Toast.makeText(this, "Digite um e-mail!", Toast.LENGTH_SHORT).show();
        }
    }

    private void recoverAccountFirebase(String email) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Verique a caixa de entrada do seu email!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Falha na autenticação!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}