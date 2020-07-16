package com.dirzaaulia.gamewish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wishlist {
    @Expose
    @SerializedName("id") private String id;
    @Expose
    @SerializedName("kode") private String kode;
    @Expose
    @SerializedName("nama") private String nama;
    @Expose
    @SerializedName("harga") private String harga;
    @Expose
    @SerializedName("toko") private String toko;
    @Expose
    @SerializedName("pre_order") private String pre_order;
    @Expose
    @SerializedName("tanggal_rilis") private String tanggal_rilis;
    @Expose
    @SerializedName("success") private Boolean success;
    @Expose
    @SerializedName("message") private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getToko() {
        return toko;
    }

    public void setToko(String toko) {
        this.toko = toko;
    }

    public String getPre_order() {
        return pre_order;
    }

    public void setPre_order(String pre_order) {
        this.pre_order = pre_order;
    }

    public String getTanggal_rilis() {
        return tanggal_rilis;
    }

    public void setTanggal_rilis(String tanggal_rilis) {
        this.tanggal_rilis = tanggal_rilis;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
