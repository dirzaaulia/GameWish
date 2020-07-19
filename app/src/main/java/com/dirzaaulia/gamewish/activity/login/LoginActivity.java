package com.dirzaaulia.gamewish.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dirzaaulia.gamewish.R;
import com.dirzaaulia.gamewish.activity.main.MainActivity;
import com.dirzaaulia.gamewish.activity.register.RegisterActivity;
import com.dirzaaulia.gamewish.model.Wishlist;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements LoginView{

    LoginPresenter loginPresenter;

    TextInputEditText editTextKode;
    TextView teksRegister;
    Button buttonLogin;

    String kode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindViews();

        loginPresenter = new LoginPresenter(this);

        buttonLogin.setOnClickListener(v -> {
            kode = Objects.requireNonNull(editTextKode.getText()).toString().trim();

            if (kode.isEmpty()){
                editTextKode.setError("Kode tidak boleh kosong!");
            } else {
                loginPresenter.cekKode(kode);
            }
        });

        teksRegister.setOnClickListener(v -> startActivity(new Intent(getBaseContext(), RegisterActivity.class)));
    }

    @Override
    public void onGetResult(List<Wishlist> wishlistList) {
        if (wishlistList.isEmpty()){
            Snackbar.make(getWindow().getDecorView(), "Kode kamu tidak ditemukan. Silahkan daftarkan kode kamu", Snackbar.LENGTH_LONG)
                    .setAction(R.string.register, v -> startActivity(new Intent(getBaseContext(), RegisterActivity.class)))
                    .setBackgroundTint(getColor(R.color.colorWhite))
                    .setTextColor(getColor(R.color.colorBackground))
                    .setActionTextColor(getColor(R.color.colorWhite))
                    .show();
        } else {
            Snackbar.make(getWindow().getDecorView(), "Berhasil masuk, Ketuk tombol untuk melanjutkan", Snackbar.LENGTH_LONG)
                    .setAction(R.string.lanjutkan, v -> {
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra("kode", kode);
                        startActivity(intent);
                    })
                    .setBackgroundTint(getColor(R.color.colorWhite))
                    .setTextColor(getColor(R.color.colorBackground))
                    .setActionTextColor(getColor(R.color.colorWhite))
                    .show();
        }
    }

    @Override
    public void onErrorLoad(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void bindViews(){
        editTextKode = findViewById(R.id.edit_text_login);
        teksRegister = findViewById(R.id.teks_register);
        buttonLogin = findViewById(R.id.button_login);
    }
}