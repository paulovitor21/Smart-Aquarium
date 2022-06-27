package com.ufam.smartaquarium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ufam.smartaquarium.databinding.ActivityRegisterBinding;

import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;

    private boolean passwordShowing = false;
    private boolean confirmPasswordShowing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.signUpBtn.setOnClickListener(v -> validateData());

        final EditText password = findViewById(R.id.passwordET);
        final EditText conPassword = findViewById(R.id.confirmPasswordET);
        final ImageView passwordIcon = findViewById(R.id.passwordIcon);
        final ImageView conPasswordIcon = findViewById(R.id.confirmPasswordIcon);



        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // verificando se a senha está aparecendo ou não
                if (passwordShowing) {
                    passwordShowing = false;

                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.ic_password_show);
                }
                else {
                    passwordShowing = true;
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.ic_password_hide);
                }
                // move o cursor para o final do texto
                password.setSelection(password.length());
            }
        });

        conPasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // verificando se a senha está aparecendo ou não
                if (confirmPasswordShowing) {
                    confirmPasswordShowing = false;

                    conPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    conPasswordIcon.setImageResource(R.drawable.ic_password_hide);
                }
                else {
                    confirmPasswordShowing = true;
                    conPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.ic_password_show);
                }
                // move o cursor para o final do texto
                conPassword.setSelection(conPassword.length());
            }
        });
    }

    private void validateData() {
        String email = binding.emailET.getText().toString().trim();
        String password = binding.passwordET.getText().toString().trim();

        if (!email.isEmpty()) {
            if (!password.isEmpty()) {
                createAccountFirebase(email, password);
            }
            else {
                Toast.makeText(this, "Digite uma senha!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Digite um e-mail!", Toast.LENGTH_SHORT).show();
        }
    }

    private void createAccountFirebase(String email, String password) {
        mAuth.createUserWithEmailAndPassword(
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