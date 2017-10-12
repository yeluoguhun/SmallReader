package com.hanshaoda.smallreader.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.hanshaoda.smallreader.model.DayNight;

/**
 * author: hanshaoda
 * created on: 2017/9/7 下午2:30
 * description:
 */

public class DayNightHelper {
    private final static String FILE_NAME = "setting";
    private final static String MODE = "day_night_mode";

    private SharedPreferences mSharedPreferences;

    public DayNightHelper(Context context) {
        this.mSharedPreferences = context.getSharedPreferences(FILE_NAME, context.MODE_PRIVATE);
    }

    /**
     * 保存模式设置
     */
    public boolean setMode(DayNight mode) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(MODE, mode.getName());
        return editor.commit();
    }

    /**
     * 夜间模式
     *
     * @return boolean
     */
    public boolean isNight() {
        String string = mSharedPreferences.getString(MODE, DayNight.DAY.getName());
        if (DayNight.NIGHT.getName().equals(string)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 日间模式
     *
     * @return boolean
     */
    public boolean isDay() {
        String mode = mSharedPreferences.getString(MODE, DayNight.DAY.getName());
        if (DayNight.DAY.getName().equals(mode)) {
            return true;
        } else {
            return false;
        }
    }
}
