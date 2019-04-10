package com.example.lian_ww.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lian_ww.R;
import com.example.lian_ww.bean.ShopCarBean;
import com.example.lian_ww.view.ShopCarAddView;

import java.util.ArrayList;
import java.util.List;

public class ShopCarItemAdapter  extends RecyclerView.Adapter<ShopCarItemAdapter.ShopCarItemViewHolder> {
    private Context context;
    private List<ShopCarBean.DataBean.ListBean> listShop = new ArrayList<>();

    public ShopCarItemAdapter(Context context, List<ShopCarBean.DataBean.ListBean> listShop) {
        this.context = context;
        this.listShop = listShop;
    }
    @NonNull
    @Override
    public ShopCarItemAdapter.ShopCarItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.adapter_item_child, null);
        ShopCarItemViewHolder shopCarItemViewHolder = new ShopCarItemViewHolder(view);
        return shopCarItemViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final ShopCarItemViewHolder shopCarItemViewHolder, final int i) {
        Glide.with(context).load(listShop.get(i).getImages()).into(shopCarItemViewHolder.mImage);
        shopCarItemViewHolder.mTitle.setText(listShop.get(i).getTitle());
        shopCarItemViewHolder.mPrice.setText(listShop.get(i).getPrice() + "");

        shopCarItemViewHolder.mCheckBox.setChecked(listShop.get(i).isChecked());//设置商品是否选中

        shopCarItemViewHolder.mShopCarAddView.setNum(listShop.get(i).getNum());//传递数量

        //接收数量发生变化
        shopCarItemViewHolder.mShopCarAddView.setOnNumCallBackListener(new ShopCarAddView.NumListener() {
            @Override
            public void num(int num) {
                listShop.get(i).setNum(num);
                if (listener!=null){
                    listener.changeData(listShop);
                }
            }
        });
        //商品的CheckBox
        shopCarItemViewHolder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked= shopCarItemViewHolder.mCheckBox.isChecked();
                listShop.get(i).setChecked(isChecked);

                if(listener!=null){
                    listener.changeData(listShop);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return listShop.size();
    }

    public class ShopCarItemViewHolder extends RecyclerView.ViewHolder {
        ShopCarAddView mShopCarAddView;
        CheckBox mCheckBox;
        TextView mTitle, mPrice;
        ImageView mImage;

        public ShopCarItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.iv_shop);
            mTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mPrice = (TextView) itemView.findViewById(R.id.tv_price);
            mCheckBox=(CheckBox)itemView.findViewById(R.id.checkbox);
            mShopCarAddView=(ShopCarAddView)itemView.findViewById(R.id.shop_car_view);
        }
    }
    private OnCallBackListener listener;
    public void setOnCallBackListener(OnCallBackListener listener){
        this.listener=listener;
    }

    public interface OnCallBackListener{
        void changeData(List<ShopCarBean.DataBean.ListBean> listShop);
    }
}
