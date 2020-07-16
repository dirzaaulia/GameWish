package com.dirzaaulia.gamewish.activity.editor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.Toast;

import com.dirzaaulia.gamewish.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditorActivity extends AppCompatActivity implements EditorView{

    TextInputEditText editTextNama, editTextHarga, editTextTanggalRilis;
    AutoCompleteTextView autoCompleteTextViewToko;
    CheckBox checkBoxPreOrder;

    Menu actionMenu;
    ProgressDialog progressDialog;
    Toolbar toolbar;

    String id, kode, nama, harga, toko, pre_order, tanggal_rilis;
    String[] listToko = {"Steam", "Uplay", "Blizzard", "Epic Games", "Origin", "Fanatical", "Green Man Gaming", "PlayStation Store", "Xbox / Microsoft Store"};

    EditorPresenter editorPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent intent1 = getIntent();
        kode = intent1.getStringExtra("kode");

        bindViews();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Tambah Wishlist");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        setDataFromIntentExtra();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon tunggu, wishlist sedang ditambahkan...");

        editorPresenter = new EditorPresenter(this);

        ambilDaftarToko();

        checkBoxPreOrder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                editTextTanggalRilis.setVisibility(View.VISIBLE);
                editTextTanggalRilis.setText(tanggal_rilis);
            } else {
                editTextTanggalRilis.setText(null);
                editTextTanggalRilis.setVisibility(View.GONE);
            }
        });

        editTextTanggalRilis.setOnClickListener(v -> tampilkanKalender());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);
        actionMenu = menu;

        if (id != null) {

            actionMenu.findItem(R.id.edit).setVisible(true);
            actionMenu.findItem(R.id.delete).setVisible(true);
            actionMenu.findItem(R.id.save).setVisible(false);
            actionMenu.findItem(R.id.update).setVisible(false);

        } else {

            actionMenu.findItem(R.id.edit).setVisible(false);
            actionMenu.findItem(R.id.delete).setVisible(false);
            actionMenu.findItem(R.id.save).setVisible(true);
            actionMenu.findItem(R.id.update).setVisible(false);

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        nama = Objects.requireNonNull(editTextNama.getText()).toString().trim();
        harga = Objects.requireNonNull(editTextHarga.getText()).toString().trim();
        toko = autoCompleteTextViewToko.getText().toString();
        pre_order = "Tidak";
        tanggal_rilis = null;

        if (checkBoxPreOrder.isChecked()){
            pre_order = "Ya";
            tanggal_rilis = Objects.requireNonNull(editTextTanggalRilis.getText()).toString().trim();
        }

        switch (item.getItemId()) {
            case R.id.edit:

                editMode();
                actionMenu.findItem(R.id.edit).setVisible(false);
                actionMenu.findItem(R.id.delete).setVisible(false);
                actionMenu.findItem(R.id.save).setVisible(false);
                actionMenu.findItem(R.id.update).setVisible(true);

                return true;

            case R.id.save:

                if (nama.isEmpty()) {
                    editTextNama.setError("Nama game tidak boleh kosong");
                } else if (nama.contains("\"") || nama.contains("'")){
                    editTextNama.setError("Hapus tanda kutip dari nama game");
                }
                else if (harga.isEmpty()) {
                    editTextHarga.setError("Harga game tidak boleh kosong");
                } else if (checkBoxPreOrder.isChecked() && tanggal_rilis.isEmpty()){
                    editTextTanggalRilis.setError("Tanggal rilis tidak boleh kosong jika game ini Pre-Order");
                } else {
                    //Toast.makeText(this, kode + " " + nama + " " + harga + " " + toko + " " + pre_order + " " + tanggal_rilis, Toast.LENGTH_SHORT).show();
                    editorPresenter.tambahWishlist(kode, nama, harga, toko, pre_order, tanggal_rilis);
                }

                return true;

            case R.id.update:

                if (nama.isEmpty()) {
                    editTextNama.setError("Nama game tidak boleh kosong");
                } else if (nama.contains("\"") || nama.contains("'")){
                    editTextNama.setError("Hapus tanda kutip dari nama game");
                } else if (harga.isEmpty()) {
                    editTextHarga.setError("Harga game tidak boleh kosong");
                } else if (checkBoxPreOrder.isChecked()) {
                    if (tanggal_rilis.isEmpty()) {
                        editTextTanggalRilis.setError("Tanggal rilis tidak boleh kosong jika game ini Pre-Order");
                    }
                } else {
                    editorPresenter.ubahWishlist(id, kode, nama, harga, toko, pre_order, tanggal_rilis);
                }

                return true;

            case R.id.delete:

                new MaterialAlertDialogBuilder(this)
                        .setTitle("Hapus Wishlist")
                        .setMessage("Apakah anda yakin untuk menghapus wishlist ini?")
                        .setPositiveButton("Ya", (dialog, which) -> {
                            dialog.dismiss();
                            editorPresenter.hapusWishlist(id, kode);
                        })
                        .setNegativeButton("Tidak", null)
                        .show();

            default:
                return super.onOptionsItemSelected(item);
        }
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onRequestError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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

    private void setDataFromIntentExtra() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        nama = intent.getStringExtra("nama");
        harga = intent.getStringExtra("harga");
        toko = intent.getStringExtra("toko");
        pre_order = intent.getStringExtra("pre_order");
        tanggal_rilis = intent.getStringExtra("tanggal_rilis");

        if (id != null) {
            editTextNama.setText(nama);
            editTextHarga.setText(harga);
            autoCompleteTextViewToko.setText(toko);

            if (pre_order.equals("Ya")){
                checkBoxPreOrder.setChecked(true);
                editTextTanggalRilis.setVisibility(View.VISIBLE);
                editTextTanggalRilis.setText(tanggal_rilis);
            }

            Objects.requireNonNull(getSupportActionBar()).setTitle("Wishlist");
            readMode();
        }
    }

    private void editMode() {
        Objects.requireNonNull(getSupportActionBar()).setTitle("Ubah Wishlist");
        editTextNama.setEnabled(true);
        editTextHarga.setEnabled(true);
        autoCompleteTextViewToko.setEnabled(true);
        checkBoxPreOrder.setEnabled(true);
        editTextTanggalRilis.setEnabled(true);
    }

    private void readMode() {
        editTextNama.setEnabled(false);
        editTextHarga.setEnabled(false);
        autoCompleteTextViewToko.setEnabled(false);
        checkBoxPreOrder.setEnabled(false);
        editTextTanggalRilis.setEnabled(false);
    }

    private void bindViews(){
        toolbar = findViewById(R.id.toolbar_editor);
        editTextNama = findViewById(R.id.nama_game);
        editTextHarga = findViewById(R.id.harga_game);
        editTextTanggalRilis = findViewById(R.id.tanggal_rilis);
        autoCompleteTextViewToko = findViewById(R.id.toko_game);
        checkBoxPreOrder = findViewById(R.id.checkbox_pre_order);
    }
}