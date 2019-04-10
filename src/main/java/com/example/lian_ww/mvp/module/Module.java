package com.example.lian_ww.mvp.module;

import okhttp3.FormBody;


public interface Module {

    public interface MyCallBack{
        void success(int type, String data);

        void fail(String erroe);
    }

    void showGet(int type, String url, MyCallBack callBack);

    void showPost(int type, String url, MyCallBack callBack, FormBody.Builder builder);

}
