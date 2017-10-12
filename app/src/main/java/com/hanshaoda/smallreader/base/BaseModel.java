package com.hanshaoda.smallreader.base;

import android.content.Context;

/**
 * author: hanshaoda
 * created on: 2017/9/8 下午2:18
 * description:
 */

public abstract class BaseModel {
    Context mContext;

    public BaseModel(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

}
