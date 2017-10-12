package com.hanshaoda.smallreader.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;

/**
 * author: hanshaoda
 * created on: 2017/9/7 下午2:18
 * description:
 */

public class DrawableUtils {

    /***
     * 创建一个图片
     *
     * @param contentColor 内部填充颜色
     * @param strokeColor  描边颜色
     * @param radius       圆角
     * @return 图片
     */
    @SuppressWarnings("WrongConstant")
    public static GradientDrawable createDrawable(int contentColor, int strokeColor, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setGradientType(GradientDrawable.RECTANGLE);//生成矩形
        drawable.setColor(contentColor);
        drawable.setStroke(1, strokeColor);
        drawable.setCornerRadius(radius);
        return drawable;
    }

    /**
     * 创建一个图片选择器
     *
     * @param normalState  普通状态的图片
     * @param pressedState 按压状态的图片
     */
    public static StateListDrawable createSelector(Drawable normalState, Drawable pressedState) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressedState);
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, normalState);
        stateListDrawable.addState(new int[]{}, normalState);
        return stateListDrawable;
    }

    /**
     * 获取图的大小
     *
     * @param drawable 图片
     * @return 图片的大小
     */
    public static int getDrawableSize(Drawable drawable) {
        if (drawable == null) {
            return 0;
        }
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        } else {
            return bitmap.getRowBytes() * bitmap.getHeight();
        }
    }
}
