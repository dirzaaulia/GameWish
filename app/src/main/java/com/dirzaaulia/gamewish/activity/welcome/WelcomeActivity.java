package com.dirzaaulia.gamewish.activity.welcome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.dirzaaulia.gamewish.R;
import com.dirzaaulia.gamewish.activity.main.MainActivity;
import com.google.android.material.chip.Chip;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity implements WelcomeView{

    TextInputEditText editTextNama, editTextHarga, editTextTanggalRilis;
    AutoCompleteTextView autoCompleteTextViewToko;
    Chip chipPreOrder;
    Button buttonBuatWishlist;

    ProgressDialog progressDialog;

    String id, kode, nama, harga, toko, pre_order, tanggal_rilis;
    String[] listToko = {"Steam", "Uplay", "Blizzard", "Epic Games", "Origin", "Fanatical", "Green Man Gaming", "PlayStation Store", "Xbox / Microsoft Store"};

    WelcomePresenter welcomePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Intent intent = getIntent();
        kode = intent.getStringExtra("kode");

        bindViews();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon tunggu, wishlist sedang ditambahkan...");

        welcomePresenter = new WelcomePresenter(this);

        ambilDaftarToko();

        chipPreOrder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                editTextTanggalRilis.setVisibility(View.VISIBLE);
                editTextTanggalRilis.setText(tanggal_rilis);
            } else {
                editTextTanggalRilis.setText(null);
                editTextTanggalRilis.setVisibility(View.GONE);
            }
        });

        editTextTanggalRilis.setOnClickListener(v -> tampilkanKalender());

        buttonBuatWishlist.setOnClickListener(v -> {
            nama = Objects.requireNonNull(editTextNama.getText()).toString().trim();
            harga = Objects.requireNonNull(editTextHarga.getText()).toString().trim();
            toko = autoCompleteTextViewToko.getText().toString();
            pre_order = "Tidak";
            tanggal_rilis = null;

            if (chipPreOrder.isChecked()){
                pre_order = "Ya";
                tanggal_rilis = Objects.requireNonNull(editTextTanggalRilis.getText()).toString().trim();
            }

            if (nama.isEmpty()) {
                editTextNama.setError("Nama game tidak boleh kosong");
            } else if (nama.contains("\"") || nama.contains("'")){
                editTextNama.setError("Hapus tanda kutip dari nama game");
            }
            else if (harga.isEmpty()) {
                editTextHarga.setError("Harga game tidak boleh kosong");
            } else if (chipPreOrder.isChecked() && tanggal_rilis.isEmpty()){
                editTextTanggalRilis.setError("Tanggal rilis tidak boleh kosong jika game ini Pre-Order");
            } else {
                welcomePresenter.tambahWishlist(kode, nama, harga, toko, pre_order, tanggal_rilis);
            }
        });
    }

    private void ambilDaftarToko(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu, listToko);
        autoCompleteTextViewToko.setAdapter(adapter);
    }

    private void tampilkanKalender() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Pilih Tanggal Rilis Game");
        builder.setSelection(Calendar.getInstance().getTimeInMillis());

        MaterialDatePicker<Long> picker = builder.build();
        picker.show(getSupportFragmentManager(), picker.toString());
        picker.addOnPositiveButtonClickListener(selection -> editTextTanggalRilis.setText(picker.getHeaderText()));
    }

    private void bindViews(){
        editTextNama = findViewById(R.id.edit_text_welcome_nama_game);
        editTextHarga = findViewById(R.id.edit_text_welcome_harga);
        editTextTanggalRilis = findViewById(R.id.edit_text_welcome_tanggal_rilis);
        autoCompleteTextViewToko = findViewById(R.id.edit_text_welcome_toko);
        chipPreOrder = findViewById(R.id.chip_welcome_pre_order);
        buttonBuatWishlist = findViewById(R.id.button_buat_wishlist);
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void onRequestSuccess(String message) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("kode", kode);
        startActivity(intent);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onRequestError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}