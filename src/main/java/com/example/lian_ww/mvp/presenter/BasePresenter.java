package com.example.lian_ww.mvp.presenter;


import com.example.lian_ww.mvp.module.Module;
import com.example.lian_ww.mvp.view.BaseView;

import okhttp3.FormBody;


public class BasePresenter implements Presenter, Module.MyCallBack {

    private Module module;
    private BaseView baseView;

    public BasePresenter(Module module, BaseView baseView) {
        this.module = module;
        this.baseView = baseView;
    }

    @Override
    public void doGet(int type, String url) {
            module.showGet(type,url,this);
    }

    @Override
    public void doPost(int type, String url, FormBody.Builder builder) {
            module.showPost(type,url,this,builder);
    }

    @Override
    public void success(int type, String data) {
        baseView.success(type,data);
    }

    @Override
    public void fail(String erroe) {
        baseView.fail(erroe);
    }


}
