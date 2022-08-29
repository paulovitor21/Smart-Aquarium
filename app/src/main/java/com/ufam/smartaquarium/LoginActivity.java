package com.ufam.smartaquarium;

import static com.google.android.gms.auth.api.signin.GoogleSignIn.*;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
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
        final RelativeLayout signInWithGoogle = findViewById(R.id.signInWithGoogle);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                        .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);

        // verificando se o usuário já fez login
        if (googleSignInAccount != null) {
            // abre activity ProfileFragment
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                // obter uma conta conectada depois que o usuário selecionou uma conta Google na caixa de diálogo
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                tarefaLogin(task);
            }
        });

        signInWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entrarIntent = googleSignInClient.getSignInIntent();
                activityResultLauncher.launch(entrarIntent);
            }
        });

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

    private void tarefaLogin(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount conta = task.getResult(ApiException.class);
            // obtendo dados da conta
            final String getNome = conta.getDisplayName();
            final String getEmail = conta.getEmail();
            final Uri getFotoURL = conta.getPhotoUrl();

            // abre HomeFragment
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } catch (ApiException e) {
            e.printStackTrace();
            Toast.makeText(this, "Autenticação falhou!", Toast.LENGTH_SHORT).show();
        }
    }
}