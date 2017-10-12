package com.hanshaoda.smallreader.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.WindowManager;

import com.hanshaoda.smallreader.app.BaseApplication;

/**
 * author: hanshaoda
 * created on: 2017/9/7 上午11:35
 * description:橡塑转换工具
 */

public class PixelUtil {

    public static Context mContext = BaseApplication.getInstance();

    /**
     * 获取屏幕宽度
     *
     * @return the int
     */
    public static int getWindowWidth() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    /**
     * 获取屏幕高度
     *
     * @return the int
     */
    public static int getWindowHeight() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    /**
     * dp 转 px
     *
     * @param value the value
     * @return the int
     */
    public static int dp2px(float value) {
        final float scale = mContext.getResources().getDisplayMetrics().densityDpi;
        return (int) (value * (scale / 160) + 0.5f);
    }

    /**
     * dp 转px
     *
     * @param value   the value
     * @param context the context
     * @return the int
     */
    public static int dp2px(float value, Context context) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) (value * (scale / 160) + 0.5f);
    }

    /**
     * px转dp
     *
     * @param value the value
     * @return the int
     */
    public static int px2dp(float value) {
        final float scale = mContext.getResources().getDisplayMetrics().densityDpi;
        return (int) ((value * 160) / scale + 0.5f);
    }

    /**
     * px 转 dp
     *
     * @param value   the value
     * @param context the context
     * @return the int
     */
    public static int px2dp(float value, Context context) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) ((value * 160) / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param value the value
     * @return the context
     */
    public static int sp2px(float value) {
        Resources r;
        if (mContext == null) {
            r = Resources.getSystem();
        } else {
            r = mContext.getResources();
        }
        float v = value * r.getDisplayMetrics().scaledDensity;
        return (int) (v + 0.5f);

    }

    /**
     * sp 转 px
     *
     * @param value   the value
     * @param context the context
     * @return the int
     */
    public static int sp2px(float value, Context context) {
        Resources r;
        if (context == null) {
            r = Resources.getSystem();
        } else {
            r = context.getResources();
        }
        float v = value * r.getDisplayMetrics().scaledDensity;
        return (int) (v + 0.5f);

    }

    /**
     * px转sp.
     *
     * @param value the value
     * @return the int
     */
    public static int px2sp(float value) {
        final float scale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (value / scale + 0.5f);
    }

    /**
     * px转sp.
     *
     * @param value   the value
     * @param context the context
     * @return the int
     */
    public static int px2sp(float value, Context context) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (value / scale + 0.5f);
    }
}
