package com.dirzaaulia.gamewish.activity.welcome;

public interface WelcomeView {
    void showProgress();

    void hideProgress();

    void onRequestSuccess(String message);

    void onRequestError(String message);
}
