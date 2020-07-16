package com.dirzaaulia.gamewish.activity.login;

import com.dirzaaulia.gamewish.model.Wishlist;

import java.util.List;

public interface LoginView {
    void onGetResult(List<Wishlist> wishlistList);

    void onErrorLoad(String message);
}
