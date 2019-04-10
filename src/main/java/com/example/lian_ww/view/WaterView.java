package com.example.lian_ww.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class WaterView extends View {

    private Context context;
    private Paint paint;
    private Path path;
    private float mX;

    public WaterView(Context context) {
        super(context);
        init(context);
    }

    public WaterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WaterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        this.context = context;
        paint = new Paint();
        path = new Path();
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(20);
        paint.setAntiAlias(true);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //重置路径
        path.reset();
        //记录初始位置
        path.moveTo(getLeft(), getBottom());
        //记录波动位置
        float ml = (float) (Math.PI * 2 / getRight());

        mX -= 0.1f;
        for (int i = 0; i < getRight(); i++) {
            float y = (float) (10 * Math.sin(ml * i + mX) + 10);
            path.lineTo(i,y);
            if (mAnimatorListener != null) {
                mAnimatorListener.Animator(y);
            }
        }
        //记录结束位置
        path.lineTo(getRight(), getBottom());

        canvas.drawPath(path, paint);

        postInvalidateDelayed(20);//定时刷新
    }

    private AnimatorListener mAnimatorListener;

    public void setAnimationListener(AnimatorListener listener) {
        this.mAnimatorListener = listener;
    }

    public interface AnimatorListener {
        void Animator(float y);
    }
}
