package com.dirzaaulia.gamewish.activity.login;

import com.dirzaaulia.gamewish.api.ApiClient;
import com.dirzaaulia.gamewish.api.ApiInterface;
import com.dirzaaulia.gamewish.model.Wishlist;

import java.util.List;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {
    private LoginView view;

    LoginPresenter(LoginView view) {
        this.view = view;
    }

    void cekKode(String kode) {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Wishlist>> call = apiInterface.cekKode(kode);
        call.enqueue(new Callback<List<Wishlist>>() {
            @Override
            public void onResponse(@NonNull Call<List<Wishlist>> call, @NonNull Response<List<Wishlist>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Wishlist>> call, @NonNull Throwable t) {
                view.onErrorLoad("Sepertinya kode kamu belum terdaftar, Silahkan lakukan pendaftaran kode");
            }
        });
    }
}
