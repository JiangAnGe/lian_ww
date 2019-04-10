package com.example.lian_ww.mvp.presenter;

import okhttp3.FormBody;

public interface Presenter {

    void doGet(int type, String url);

    void doPost(int type, String url, FormBody.Builder builder);

}
