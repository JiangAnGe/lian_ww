package com.example.lian_ww;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.example.lian_ww.fragment.Frag01;
import com.example.lian_ww.fragment.Frag02;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radio_group;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化
        radio_group = findViewById(R.id.radio_group);
        //获取适配器
        manager = getSupportFragmentManager();
        final Frag01 frag01 = new Frag01();
        final Frag02 frag02 = new Frag02();
        manager.beginTransaction().add(R.id.fragment_layout,frag01)
                .add(R.id.fragment_layout,frag02)
                .show(frag01).hide(frag02).commit();
        //点击切换
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               FragmentTransaction transaction =  manager.beginTransaction();
               switch (checkedId){
                   case R.id.rd1:
                       transaction.show(frag01).hide(frag02);
                       break;
                   case R.id.rd2:
                       transaction.show(frag02).hide(frag01);
                       break;
               }
               transaction.commit();
            }
        });

    }
}
