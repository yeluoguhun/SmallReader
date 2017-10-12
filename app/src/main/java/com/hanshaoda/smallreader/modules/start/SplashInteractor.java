package com.hanshaoda.smallreader.modules.start;

/**
 * author: hanshaoda
 * created on: 2017/9/14 上午10:08
 * description:
 */

public interface SplashInteractor {

    interface OnEnterIntoFinishListener {
        void isFirstOpen();

        void isNotFirstOpen();

        void showContentView();
    }

    void enterInto(boolean isFirstOpen, OnEnterIntoFinishListener listener);
}
