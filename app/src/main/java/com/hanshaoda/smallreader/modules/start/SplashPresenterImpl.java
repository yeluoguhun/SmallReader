package com.hanshaoda.smallreader.modules.start;

/**
 * author: hanshaoda
 * created on: 2017/9/14 上午10:11
 * description:
 */

public class SplashPresenterImpl implements SplashPresenter, SplashInteractor.OnEnterIntoFinishListener {


    private SplashView mView;

    private SplashInteractor mSplashInteractor;

    public SplashPresenterImpl(SplashView view) {
        mView = view;
        mSplashInteractor = new SplashInteractorImpl();
    }

    @Override
    public void isFirstOpen() {

    }

    @Override
    public void isNotFirstOpen() {
        mView.startHomeActivity();
    }

    @Override
    public void showContentView() {
        mView.initContentView();
    }

    @Override
    public void isFirstOpen(boolean isFirstOpen) {
        mSplashInteractor.enterInto(isFirstOpen, this);
    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
