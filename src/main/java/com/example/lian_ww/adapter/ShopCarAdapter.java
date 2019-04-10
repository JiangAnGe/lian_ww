package com.example.lian_ww.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.lian_ww.R;
import com.example.lian_ww.bean.ShopCarBean;

import java.util.ArrayList;
import java.util.List;

public class ShopCarAdapter extends RecyclerView.Adapter<ShopCarAdapter.ShopCarViewHolder> {
    private Context context;
    private List<ShopCarBean.DataBean> list = new ArrayList<>();

    public ShopCarAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ShopCarAdapter.ShopCarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.adapter_item, null);
        ShopCarViewHolder shopCarViewHolder = new ShopCarViewHolder(view);
        return shopCarViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ShopCarAdapter.ShopCarViewHolder shopCarViewHolder, final int i) {
        shopCarViewHolder.mTitle.setText(list.get(i).getSellerName());//设置商家名字

        shopCarViewHolder.mCheckBox.setChecked(list.get(i).isChecked());//改变商家是否选中

        List<ShopCarBean.DataBean.ListBean> listShop = list.get(i).getList();//获取商家下对应得商品的集合
        ShopCarItemAdapter shopCarItemAdapter = new ShopCarItemAdapter(context, listShop);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        shopCarViewHolder.mRecycler.setLayoutManager(linearLayoutManager);//一定要记住设置布局管理器
        shopCarViewHolder.mRecycler.setAdapter(shopCarItemAdapter);

        shopCarItemAdapter.setOnCallBackListener(new ShopCarItemAdapter.OnCallBackListener() {
            @Override
            public void changeData(List<ShopCarBean.DataBean.ListBean> listShop) {
                int checkedNum=0;
                for (int i = 0; i < listShop.size(); i++) {
                    boolean checked=listShop.get(i).isChecked();
                    if(checked){
                        checkedNum++;
                    }
                }

                if(checkedNum==listShop.size()){//商家下的商品 全选
                    shopCarViewHolder.mCheckBox.setChecked(true);
                }else{//不是全选
                    shopCarViewHolder.mCheckBox.setChecked(false);
                }

                if(mOnCallBackListener!=null){
                    mOnCallBackListener.changeData(list);//回传给调用页面
                }

            }
        });

        //商家的checkbox
        shopCarViewHolder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked=shopCarViewHolder.mCheckBox.isChecked();

                list.get(i).setChecked(isChecked);//赋值

                List<ShopCarBean.DataBean.ListBean> listShop = list.get(i).getList();//获取商家下对应得商品的集合
                for (int i = 0; i < listShop.size(); i++) {
                    listShop.get(i).setChecked(isChecked);//设置商品是否选中
                }

                if(mOnCallBackListener!=null){
                    mOnCallBackListener.changeData(list);//回传给调用页面
                }

                //刷新适配器
                notifyItemChanged(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //传递数据
    public void setList(List<ShopCarBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class ShopCarViewHolder extends RecyclerView.ViewHolder {
        CheckBox mCheckBox;
        RecyclerView mRecycler;
        TextView mTitle;

        public ShopCarViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mRecycler = (RecyclerView) itemView.findViewById(R.id.recycler);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }
    }

    private OnCallBackListener mOnCallBackListener;

    public void setOnCallBackListener(OnCallBackListener mOnCallBackListener){
        this.mOnCallBackListener=mOnCallBackListener;
    }

    public interface OnCallBackListener{
        void changeData(List<ShopCarBean.DataBean> list);
    }
}

