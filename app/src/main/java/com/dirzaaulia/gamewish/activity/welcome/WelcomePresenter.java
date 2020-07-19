package com.dirzaaulia.gamewish.activity.welcome;

import com.dirzaaulia.gamewish.api.ApiClient;
import com.dirzaaulia.gamewish.api.ApiInterface;
import com.dirzaaulia.gamewish.model.Wishlist;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomePresenter {
    private WelcomeView view;

    WelcomePresenter(WelcomeView view) {
        this.view = view;
    }

    void tambahWishlist(final String kode, final String nama, final String harga, final String toko, final String pre_order, final String tanggal_rilis) {

        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Wishlist> call = apiInterface.tambahWishlist(kode, nama, harga, toko, pre_order, tanggal_rilis);
        call.enqueue(new Callback<Wishlist>() {
            @Override
            public void onResponse(@NonNull Call<Wishlist> call, @NonNull Response<Wishlist> response) {

                view.hideProgress();

                if (response.isSuccessful() && response.body() != null) {

                    Boolean success = response.body().getSuccess();
                    if (success) {
                        view.onRequestSuccess(response.body().getMessage());
                    } else {
                        view.onRequestError(response.body().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<Wishlist> call, @NonNull Throwable t) {

                view.hideProgress();
                view.onRequestError("Terjadi kesalahan saat menambahkan wishlist");
            }
        });
    }
}
