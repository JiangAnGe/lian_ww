package com.example.lian_ww.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.lian_ww.R;


public class ShopCarAddView extends RelativeLayout implements View.OnClickListener {

    private Context context;
    private EditText mEdit;

    public ShopCarAddView(Context context) {
        super(context);
        init(context);
    }

    public ShopCarAddView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(final Context context) {
        this.context=context;
        View view=View.inflate(context, R.layout.shop_car_add_view,null);
        mEdit=(EditText)view.findViewById(R.id.ed_shop);
        view.findViewById(R.id.tv_add).setOnClickListener(this);
        view.findViewById(R.id.tv_jian).setOnClickListener(this);

        mEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("CharSequence",s.toString());
                if (TextUtils.isEmpty(s.toString()) && !s.toString().equals("-")){
                    int num = Integer.parseInt(s.toString());
                    if ((s.toString().charAt(0)+"").equals("0")){
                        num=1;
                    }
                    if (num<1){
                        num=1;
                        Toast.makeText(context,"请输入最少一个商品",Toast.LENGTH_LONG).show();
                        mEdit.removeTextChangedListener(this);//先移除监听不然会死循环
                        mEdit.setText(num+"");//对输入文本框进行分段先是
                        mEdit.addTextChangedListener(this);
                    }
                    if (callBack!=null){
                        callBack.num(num);
                    }
                }
            }
        });
        addView(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_jian:
                String trim = mEdit.getText().toString().trim();
                int num = Integer.parseInt(trim);
                if (num<=1){
                    Toast.makeText(context,"至少一个商品",Toast.LENGTH_LONG).show();
                    return;
                }
                num--;
                if (callBack!=null){
                    callBack.num(num);
                }
                mEdit.setText(num+"");
                break;
            case R.id.tv_add:
                String trimString = mEdit.getText().toString().trim();
                int sum = Integer.parseInt(trimString);
                sum++;
                if (callBack!=null){
                    callBack.num(sum);
                }
                mEdit.setText(sum+"");
                break;
        }
    }
    private NumListener callBack;
    public void setOnNumCallBackListener(NumListener callBack){
        this.callBack=callBack;
    }

    public void setNum(int num) {
        mEdit.setText(num+"");
    }


    public interface NumListener{
        void num(int num);
    }
}
