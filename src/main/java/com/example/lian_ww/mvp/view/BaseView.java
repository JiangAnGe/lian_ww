package com.example.lian_ww.mvp.view;

public interface BaseView {

    void success(int type, String data);//成功

    void fail(String error);//失败
}
