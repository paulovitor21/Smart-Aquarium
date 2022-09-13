package com.ufam.smartaquarium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

public class PrivacyPolicy extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        
        //final ImageView btnBackPolicy = findViewById(R.id.btnBackPolicy);

        WebView webView = findViewById(R.id.wv_privacidade);
        webView.loadUrl("https://sites.google.com/view/documentos-smart-aquarium/in%C3%ADcio");



    }
}