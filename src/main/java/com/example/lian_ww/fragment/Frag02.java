package com.example.lian_ww.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lian_ww.R;
import com.example.lian_ww.activity.LoginActivity;
import com.example.lian_ww.mvp.view.BaseView;
import com.example.lian_ww.view.WaterView;

public class Frag02 extends Fragment {

    private ImageView imageView;
    private TextView text_view;
    private WaterView watreView;
    private SharedPreferences config;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag02, container, false);
        imageView = view.findViewById(R.id.image_view);
        text_view = view.findViewById(R.id.text_view);
        watreView = view.findViewById(R.id.water);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        config = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        //让上面的照片与水波纹一起浮动
        watreView.setAnimationListener(new WaterView.AnimatorListener() {
            @Override
            public void Animator(float y) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                params.setMargins(0, (int) y, 0, 0);
                imageView.setLayoutParams(params);
            }
        });

        text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = config.getInt("id", 0);
                if (id != 0) {

                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });
    }
}
