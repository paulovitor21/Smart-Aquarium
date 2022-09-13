package com.ufam.smartaquarium;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class TermofUse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termof_use);

        WebView webView = findViewById(R.id.wv_termosUso);
        webView.loadUrl("https://sites.google.com/view/aquarismo-acqua/in%C3%ADcio");

    }
}