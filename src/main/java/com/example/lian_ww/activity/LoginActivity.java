package com.example.lian_ww.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lian_ww.MainActivity;
import com.example.lian_ww.R;
import com.example.lian_ww.bean.LoginBean;
import com.example.lian_ww.mvp.module.BaseModule;
import com.example.lian_ww.mvp.presenter.BasePresenter;
import com.example.lian_ww.mvp.view.BaseView;
import com.google.gson.Gson;

import okhttp3.FormBody;


public class LoginActivity extends AppCompatActivity implements BaseView {

    String LoginUrl = "http://172.17.8.100/small/user/v1/login";
    private TextView mName, mPwd;
    private Button mRegion, mLogin;
    private BasePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mName = findViewById(R.id.login_name);
        mPwd = findViewById(R.id.login_pwd);
        mRegion = findViewById(R.id.login_region);
        mLogin = findViewById(R.id.login);
        //调用presenter层
        presenter = new BasePresenter(new BaseModule(), this);
        //点击登录
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取传值
                String name = mName.getText().toString().trim();
                String pwd = mPwd.getText().toString().trim();
                //非空校验
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
                    Toast.makeText(LoginActivity.this, "输入的内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                FormBody.Builder builder = new FormBody.Builder();
                builder.add("phone", name);
                builder.add("pwd", pwd);
                presenter.doPost(1, LoginUrl, builder);

            }
        });
        mRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegionActivity.class));
            }
        });
    }

    @Override
    public void success(int type, String data) {
        if (type == 1) {
            Gson gson = new Gson();
            LoginBean loginBean = gson.fromJson(data, LoginBean.class);
            Log.i("aaa", loginBean.toString());
            if (loginBean.getStatus().equals("0000")) {
                SharedPreferences config = getSharedPreferences("config", MODE_PRIVATE);
                String nickName = loginBean.getResult().getNickName();
                String headPic = loginBean.getResult().getHeadPic();
                config.edit().putString("name", nickName).commit();
                config.edit().putString("pwd", nickName).commit();
                config.edit().putInt("id", loginBean.getResult().getUserId()).commit();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else {
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void fail(String error) {

    }
}
