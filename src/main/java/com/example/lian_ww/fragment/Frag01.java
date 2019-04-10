package com.example.lian_ww.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.lian_ww.R;
import com.example.lian_ww.adapter.ShopCarAdapter;
import com.example.lian_ww.bean.ShopCarBean;
import com.example.lian_ww.mvp.module.BaseModule;
import com.example.lian_ww.mvp.presenter.BasePresenter;
import com.example.lian_ww.mvp.view.BaseView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Frag01 extends Fragment implements BaseView {
    private RecyclerView mRecycler;
    private TextView mTvPrice;
    private TextView mTvMoney;
    private ShopCarAdapter adapter;
    private BasePresenter presenter;
    private LinearLayoutManager manager;
    private CheckBox mCheckeBox;
    private String url = "http://172.17.8.100/ks/product/getCarts?uid=51";
    private List<ShopCarBean.DataBean> datas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag01, container, false);
        mRecycler = view.findViewById(R.id.recycler);
        mTvPrice = view.findViewById(R.id.tv_allprice);
        mTvMoney = view.findViewById(R.id.tv_money);
        mCheckeBox = view.findViewById(R.id.checkbox_all);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new BasePresenter(new BaseModule(), this);

        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecycler.setLayoutManager(manager);
        adapter = new ShopCarAdapter(getActivity());

        mRecycler.setAdapter(adapter);
        mCheckeBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = mCheckeBox.isChecked();
                float allPrice = 0;
                int allNum = 0;
                for (int i = 0; i < datas.size(); i++) {
                    datas.get(i).setChecked(isChecked);
                    List<ShopCarBean.DataBean.ListBean> list = datas.get(i).getList();
                    for (int j = 0; j < list.size(); j++) {
                        list.get(j).setChecked(isChecked);
                        if (isChecked) {//全选
                            float price = list.get(j).getPrice();
                            int num = list.get(j).getNum();
                            allPrice = allPrice + price * num;
                            allNum = allNum + num;
                        } else {//不全选
                            allNum = 0;
                            allPrice = 0;
                        }
                    }
                }
                mTvPrice.setText(allPrice + "");//设置总价
                mTvMoney.setText("去结算" + allNum + "");//设置数量
                adapter.setList(datas);
            }
        });

        adapter.setOnCallBackListener(new ShopCarAdapter.OnCallBackListener() {
            @Override
            public void changeData(List<ShopCarBean.DataBean> list) {

                float allPrice = 0;
                int allNum = 0;
                for (int i = 0; i < list.size(); i++) {
                    boolean ischecked = list.get(i).isChecked();
                    if (ischecked) {//商家选中
                        List<ShopCarBean.DataBean.ListBean> listC = list.get(i).getList();
                        for (int j = 0; j < listC.size(); j++) {
                            float price = listC.get(j).getPrice();
                            int num = listC.get(j).getNum();
                            allPrice = allPrice + price * num;
                            allNum = allNum + num;
                        }
                    } else {//商家未选中，有可能商家里面的商品选中了
                        List<ShopCarBean.DataBean.ListBean> listC = list.get(i).getList();
                        for (int j = 0; j < listC.size(); j++) {
                            if (listC.get(j).isChecked()) {
                                float price = listC.get(j).getPrice();
                                int num = listC.get(j).getNum();
                                allPrice = allPrice + price * num;
                                allNum = allNum + num;
                            }
                        }
                    }
                }
                mTvPrice.setText(allPrice + "");//设置总价
                mTvMoney.setText("去结算（" + allNum + ")");//设置数量
            }
        });
        presenter.doGet(0, url);
    }


    @Override
    public void success(int type, String data) {
        ShopCarBean bean = new Gson().fromJson(data, ShopCarBean.class);
        datas = bean.getData();//所有的商家以及商家下的商品集合
        datas.remove(0);
        adapter.setList(datas);

    }

    @Override
    public void fail(String error) {

    }

}


