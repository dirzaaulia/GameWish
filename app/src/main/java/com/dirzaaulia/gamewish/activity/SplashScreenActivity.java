package com.dirzaaulia.gamewish.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.dirzaaulia.gamewish.R;
import com.dirzaaulia.gamewish.activity.login.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            finish();
        }, 2000);
    }
}