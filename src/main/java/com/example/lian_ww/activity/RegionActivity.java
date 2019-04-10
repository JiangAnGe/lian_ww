package com.example.lian_ww.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lian_ww.R;
import com.example.lian_ww.mvp.module.BaseModule;
import com.example.lian_ww.mvp.presenter.BasePresenter;
import com.example.lian_ww.mvp.view.BaseView;

import okhttp3.FormBody;


public class RegionActivity extends AppCompatActivity implements BaseView {

    private EditText Mname,Mpwsd;
    private Button region;
    private String url="http://172.17.8.100/small/user/v1/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region);
        Mname=findViewById(R.id.regin_name);
        Mpwsd=findViewById(R.id.regin_pwd);
        region=findViewById(R.id.region);
        final BasePresenter presenter=new BasePresenter(new BaseModule(),this);
        //点击注册
        region.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //非空验证
                String name = Mname.getText().toString().trim();
                String pwd = Mpwsd.getText().toString().trim();
                if (TextUtils.isEmpty(name)||TextUtils.isEmpty(pwd)){
                    Toast.makeText(RegionActivity.this,"输入内容不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                FormBody.Builder builder=new FormBody.Builder();
                builder.add("phone",name);
                builder.add("pwd",pwd);
                presenter.doPost(1,url,builder);
            }
        });
    }

    @Override
    public void success(int type, String data) {
        if (type==1){
            Toast.makeText(RegionActivity.this,data,Toast.LENGTH_SHORT);
            Log.i("a a",data);
            finish();
        }

    }

    @Override
    public void fail(String error) {

    }
}
