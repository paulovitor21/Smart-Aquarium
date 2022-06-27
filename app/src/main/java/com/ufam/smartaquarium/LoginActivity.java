package com.ufam.smartaquarium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.ufam.smartaquarium.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    private boolean passwordShowing = false;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        // redireciona para RegisterActivity
        binding.signUpBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        // redireciona para RecoverPasswordActivity
        binding.forgotPasswordBtn.setOnClickListener(v ->
                startActivity(new Intent(this, RecoverPasswordActivity.class)));

        final EditText passwordET = findViewById(R.id.passwordET);
        final ImageView passwordIcon = findViewById(R.id.passwordIcon);

        // verificando se a senha está aparecendo ou não
        passwordIcon.setOnClickListener(v -> {

            if (passwordShowing) {
                passwordShowing = false;

                passwordET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordIcon.setImageResource(R.drawable.ic_password_hide);
            }
            else {
                passwordShowing = true;
                passwordET.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                passwordIcon.setImageResource(R.drawable.ic_password_show);
            }
            // move o cursor para o final do texto
            passwordET.setSelection(passwordET.length());
        });

        binding.signInBtn.setOnClickListener(v -> validateData());
    }

    private void validateData() {
        String email = binding.emailET.getText().toString().trim();
        String password = binding.passwordET.getText().toString().trim();

        if (!email.isEmpty()) {
            if (!password.isEmpty()) {
                loginFirebase(email, password);
            }
            else {
                Toast.makeText(this, "Digite uma senha!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Digite um e-mail!", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginFirebase(String email, String password) {
        mAuth.signInWithEmailAndPassword(
                email, password
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                finish();
                startActivity(new Intent(this, MainActivity.class));
            }
            else {
                Toast.makeText(this, "Falha na autenticação!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}