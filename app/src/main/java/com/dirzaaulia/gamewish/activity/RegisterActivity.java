package com.dirzaaulia.gamewish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.dirzaaulia.gamewish.R;
import com.dirzaaulia.gamewish.activity.main.MainActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText editTextRegister;
    Button buttonRegister;

    String kode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bindViews();

        buttonRegister.setOnClickListener(v -> {
            kode = Objects.requireNonNull(editTextRegister.getText()).toString().trim();

            if (kode.isEmpty()){
                editTextRegister.setError("Kode tidak boleh kosong");
            } else {
                Snackbar.make(getWindow().getDecorView(), "Berhasil masuk, Ketuk tombol untuk melanjutkan", Snackbar.LENGTH_LONG).setAction(R.string.lanjutkan, v1 -> {
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.putExtra("kode", kode);
                    startActivity(intent);
                    finish();
                }).setBackgroundTint(getColor(R.color.colorWhite)).setTextColor(getColor(R.color.colorBackground)).setActionTextColor(getColor(R.color.colorWhite)).show();
            }
        });
    }

    private void bindViews(){
        editTextRegister = findViewById(R.id.edit_text_register);
        buttonRegister = findViewById(R.id.button_register);
    }
}