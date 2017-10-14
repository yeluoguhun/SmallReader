package com.hanshaoda.smallreader.modules.me.calender;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import com.hanshaoda.smallreader.app.BaseApplication;

public class CircleBackGroundSpan implements LineBackgroundSpan {
    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#FF4081"));
        c.drawCircle((right - left) / 2, (bottom - top) / 2 + dip2px(4), dip2px(18), paint);
    }
    public static int dip2px(float dpValue) {
        float scale = BaseApplication.screenDensity;
        return (int)(dpValue * scale + 0.5F);
    }
}
