package com.example.easyorder.activity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends MainActivity {
    private static boolean splashLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}