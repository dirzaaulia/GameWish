package com.dirzaaulia.gamewish.activity.register;

import com.dirzaaulia.gamewish.model.Wishlist;

import java.util.List;

public interface RegisterView {
    void onGetResult(List<Wishlist> wishlistList);

    void onErrorLoad(String message);
}
