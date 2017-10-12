package com.hanshaoda.smallreader.widget.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.hanshaoda.smallreader.R;

/**
 * author: hanshaoda
 * created on: 2017/10/10 下午2:20
 * description:
 */

public class MapTextView extends TextView {

    private String mValueText;
    private String mKeyText;
    private boolean mIsUniformColor;
    private int mCircleColor;
    private int mKeyBackgroundColor;
    private int mValueTextColor;
    private int mKeyTextColor;
    private float mValueTextSize;
    private float mKeyTextSize;
    private int mViewSize;
    private int mWidth;
    private int mhight;

    public MapTextView(Context context) {
        super(context);
    }

    public MapTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MapTextView);
        mValueText = ta.getString(R.styleable.MapTextView_valueText);
        mKeyText = ta.getString(R.styleable.MapTextView_keyText);
        mIsUniformColor = ta.getBoolean(R.styleable.MapTextView_isUniformColor, true);
        int color = context.getResources().getColor(R.color.colorAccent);
        mCircleColor = ta.getColor(R.styleable.MapTextView_circleColor, color);
        if (mIsUniformColor) {
            mKeyBackgroundColor = mCircleColor;
            mKeyTextColor = Color.WHITE;
            mValueTextColor = mCircleColor;
        } else {
            mKeyBackgroundColor = ta.getColor(R.styleable.MapTextView_keyBackgroundColor, color);
            mValueTextColor = ta.getColor(R.styleable.MapTextView_valueTextColor, Color.WHITE);
            mKeyTextColor = ta.getColor(R.styleable.MapTextView_keyTextColor, color);
        }
        mKeyTextSize = ta.getDimension(R.styleable.MapTextView_keyTextSize, 0);
        mValueTextSize = ta.getDimension(R.styleable.MapTextView_valueTextSize, 0);
        ta.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mViewSize = Math.min(w, h);
        mWidth = w;
        mhight = h;
        if (mKeyTextSize == 0.0) {
            mKeyTextSize = mViewSize / 10;
        }
        if (mValueTextSize == 0.0) {
            mValueTextSize = mViewSize / 4;
        }
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mhight / 2);
        Paint paint = new Paint();
        paint.setColor(mCircleColor);
        paint.setStyle(Paint.Style.STROKE);
        int radius = mViewSize / 2;
        canvas.drawCircle(0, 0, radius, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(mKeyBackgroundColor);

        RectF rectF = new RectF(-radius, -radius, radius, radius);
        canvas.drawArc(rectF, 20, 140, false, paint);

        float pathStartX = (float) Math.sqrt(radius * radius - mValueTextSize * mValueTextSize);

        canvas.translate(-pathStartX, 0);
        float pathEndX = pathStartX * 2;

        Path path = new Path();
        path.lineTo(pathEndX, 0);

        paint.setTextSize(mValueTextSize);
        paint.setColor(mValueTextColor);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawTextOnPath(TextUtils.isEmpty(mValueText) ? "love" : mValueText, path, 0, 0, paint);

        double sin20 = Math.sin(Math.PI * 20 / 180);
        double radiusDistance = sin20 * radius;
        double v = (radius - radiusDistance) / 2;
        double distanceX = Math.sqrt(radius * radius - (v + radiusDistance) * (v + radiusDistance));

        canvas.translate(pathStartX, 0);
        canvas.translate(0, (float) (v + radiusDistance));
        canvas.translate((float) -distanceX, 0);

        Path path_bottomText = new Path();
        path_bottomText.lineTo((float) (distanceX * 2), 0);

        paint.setColor(mKeyTextColor);
        paint.setTextSize((float) Math.min(mKeyTextSize, v));
        canvas.drawTextOnPath(TextUtils.isEmpty(mKeyText) ? "小秋魔盒" : mKeyText, path_bottomText, 0, 0, paint);

    }

    public void setValueText(String mValueText) {
        this.mValueText = mValueText;
    }

    public void setKeyText(String mKeyText) {
        this.mKeyText = mKeyText;
    }
}
