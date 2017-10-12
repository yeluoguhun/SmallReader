package com.hanshaoda.smallreader.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * author: hanshaoda
 * created on: 2017/9/9 上午10:14
 * description:公共参数配置
 */

public class Setting {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public Setting(Context context, String name) {
        mSharedPreferences = context.getSharedPreferences(name, Activity.MODE_PRIVATE);
    }

    /**
     * 加载一个string类型
     */
    public String loadString(String key) {
        return mSharedPreferences.getString(key, "");
    }

    public int loadInt(String key) {
        return mSharedPreferences.getInt(key, -1);
    }

    public long loadLong(String key) {
        return mSharedPreferences.getLong(key, -1);
    }

    public float loadFloat(String key) {
        return mSharedPreferences.getFloat(key, -1);
    }

    public boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    public void saveString(String key, String value) {
        if (mEditor == null) {
            mEditor = mSharedPreferences.edit();
        }
        mEditor.putString(key, value);
        mEditor.commit();

    }

    public void saveInt(String key, int value) {
        if (mEditor == null) {
            mEditor = mSharedPreferences.edit();
        }
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public void saveLong(String key, long value) {
        if (mEditor == null) {
            mEditor = mSharedPreferences.edit();
        }
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    public void saveBoolean(String key, boolean value) {
        if (mEditor == null) {
            mEditor = mSharedPreferences.edit();
        }
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public void saveFloat(String key, float value) {
        if (mEditor == null) {
            mEditor = mSharedPreferences.edit();
        }
        mEditor.putFloat(key, value);
        mEditor.commit();
    }

    /**
     * 清空数据
     */
    public void clear() {
        if (mEditor == null) {
            mSharedPreferences.edit();
        }
        mEditor.clear();
        mEditor.commit();
    }
}
