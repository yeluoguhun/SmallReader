package com.hanshaoda.smallreader.base;

import android.text.TextUtils;

/**
 * author: hanshaoda
 * created on: 2017/9/8 下午2:20
 * description:公共的presenter
 */

public abstract class BasePresenter {

    /**
     * 检验指定的字符串是否为空，为空则弹出指定内的Toast
     *
     * @param data
     * @param view
     * @param showMessage
     * @return
     */
    public boolean isEmpty(String data, IBaseView view, String showMessage) {
        if (TextUtils.isEmpty(data)) {
            view.showToast(data);
            return true;
        }
        return false;
    }
}
