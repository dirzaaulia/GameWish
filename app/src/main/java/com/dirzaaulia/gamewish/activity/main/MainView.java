package com.dirzaaulia.gamewish.activity.main;

import com.dirzaaulia.gamewish.model.Wishlist;

import java.util.List;

public interface MainView {
    void showLoad();

    void hideLoad();

    void onGetResult(List<Wishlist> wishlistList);

    void onErrorLoad(String message);
}
