package com.dirzaaulia.gamewish.api;

import com.dirzaaulia.gamewish.model.Wishlist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("gamewish/tambahwishlist.php")
    Call<Wishlist> tambahWishlist(
            @Field("kode") String kode,
            @Field("nama") String nama,
            @Field("harga") String harga,
            @Field("toko") String toko,
            @Field("pre_order") String pre_order,
            @Field("tanggal_rilis") String tanggal_rilis
    );

    @GET("gamewish/ambildatawishlist.php")
    Call<List<Wishlist>> getWishlist(
            @Query("key") String keyword,
            @Query("kode") String kode
    );

    @GET("gamewish/cekkode.php")
    Call<List<Wishlist>> cekKode(
            @Query("kode") String keyword
    );

    @FormUrlEncoded
    @POST("gamewish/ubahwishlist.php")
    Call<Wishlist> ubahWishlist(
            @Field("id") String id,
            @Field("kode") String kode,
            @Field("nama") String nama,
            @Field("harga") String harga,
            @Field("toko") String toko,
            @Field("pre_order") String pre_order,
            @Field("tanggal_rilis") String tanggal_rilis
    );

    @FormUrlEncoded
    @POST("gamewish/hapuswishlist.php")
    Call<Wishlist> hapusWishlist(
            @Field("id") String id,
            @Field("kode") String kode
    );
}
