package com.dirzaaulia.gamewish.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.dirzaaulia.gamewish.R;
import com.dirzaaulia.gamewish.activity.welcome.WelcomeActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    RegisterPresenter registerPresenter;

    TextInputEditText editTextRegister;
    Button buttonRegister;

    String kode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bindViews();

        registerPresenter = new RegisterPresenter(this);

        buttonRegister.setOnClickListener(v -> {
            kode = Objects.requireNonNull(editTextRegister.getText()).toString().trim();

            if (kode.isEmpty()){
                editTextRegister.setError("Kode tidak boleh kosong");
            } else {
               registerPresenter.registerKode(kode);
            }
        });
    }

    private void bindViews(){
        editTextRegister = findViewById(R.id.edit_text_register);
        buttonRegister = findViewById(R.id.button_register);
    }

    @Override
    public void onRequestSuccess(String message) {
        Snackbar.make(getWindow().getDecorView(), message, Snackbar.LENGTH_LONG)
                .setAction(R.string.lanjutkan, v -> {
                    Intent intent = new Intent(getBaseContext(), WelcomeActivity.class);
                    intent.putExtra("kode", kode);
                    startActivity(intent);
                    finish();
                })
                .setBackgroundTint(getColor(R.color.colorWhite))
                .setTextColor(getColor(R.color.colorBackground))
                .setActionTextColor(getColor(R.color.colorWhite))
                .show();
    }

    @Override
    public void onRequestError(String message) {
        Snackbar.make(getWindow().getDecorView(), message, Snackbar.LENGTH_LONG)
                .setBackgroundTint(getColor(R.color.colorWhite))
                .setTextColor(getColor(R.color.colorBackground))
                .setActionTextColor(getColor(R.color.colorWhite))
                .show();
    }
}