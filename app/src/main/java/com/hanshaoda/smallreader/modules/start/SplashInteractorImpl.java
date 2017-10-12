package com.hanshaoda.smallreader.modules.start;

import android.os.Handler;

/**
 * author: hanshaoda
 * created on: 2017/9/14 上午10:14
 * description:
 */

public class SplashInteractorImpl implements SplashInteractor {
    @Override
    public void enterInto(boolean isFirstOpen, final OnEnterIntoFinishListener listener) {
        if (!isFirstOpen) {
            listener.isFirstOpen();
        } else {
            listener.showContentView();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    listener.isNotFirstOpen();
                }
            }, 2000);
        }
    }
}
