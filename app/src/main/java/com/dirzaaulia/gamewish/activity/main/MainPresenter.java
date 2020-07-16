package com.dirzaaulia.gamewish.activity.main;

import com.dirzaaulia.gamewish.api.ApiClient;
import com.dirzaaulia.gamewish.api.ApiInterface;
import com.dirzaaulia.gamewish.model.Wishlist;

import java.util.List;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {
    private MainView view;

    MainPresenter(MainView view) {
        this.view = view;
    }

    void getWishlist(String key, String kode) {

        view.showLoad();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Wishlist>> call = apiInterface.getWishlist(key, kode);
        call.enqueue(new Callback<List<Wishlist>>() {
            @Override
            public void onResponse(@NonNull Call<List<Wishlist>> call, @NonNull Response<List<Wishlist>> response) {
                view.hideLoad();
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Wishlist>> call, @NonNull Throwable t) {
                view.hideLoad();
                view.onErrorLoad("Terjadi kesalahan saat mengambil data wishlist");
            }
        });
    }
}
