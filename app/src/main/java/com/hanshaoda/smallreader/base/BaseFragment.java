package com.hanshaoda.smallreader.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * author: hanshaoda
 * created on: 2017/9/7 下午5:37
 * description:
 */
public abstract class BaseFragment extends Fragment implements IBaseView {

    private BaseActivity mActivity;
    private View mLayoutView;

    /**
     * 初始化布局
     *
     * @return 布局文件的id
     */
    public abstract int getLayoutRes();

    /**
     * 初始化视图
     */
    public abstract void initView();

    /**
     * 如果Fragment创建需要数据，在这里接收传进来的数据
     * 如果没有这个抽象方法就空实现
     */
    public abstract void managerArguments();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            managerArguments();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mLayoutView != null) {
            ViewGroup parent = (ViewGroup) mLayoutView.getParent();
            if (parent != null) {
                parent.removeView(mLayoutView);
            }
        } else {
            mLayoutView = getCreateView(inflater, container);
            ButterKnife.bind(this, mLayoutView);
            initView();
        }
        return mLayoutView;
    }

    /**
     * 获取布局文件中的view
     */
    private View getCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    /**
     * 获取当前Fragment的状态
     *
     * @return true 正常 false 未加载或正在删除
     */
    private boolean getStatus() {
        return ((isAdded() && !isRemoving()));
    }

    /**
     * 获取Activity
     */
    public BaseActivity getBaseActivity() {
        if (mActivity == null) {
            mActivity = (BaseActivity) getActivity();
        }
        return mActivity;
    }

    @Override
    public void showProgress(boolean flag, String message) {
        if (getStatus()) {
            BaseActivity baseActivity = getBaseActivity();
            if (baseActivity != null) {
                baseActivity.showProgress(flag, message);
            }

        }
    }

    @Override
    public void showProgress(String message) {
        showProgress(message);
    }

    @Override
    public void showProgress(boolean flag) {
        showProgress(flag, "");
    }

    @Override
    public void showProgress() {
        showProgress(true);
    }

    @Override
    public void hideProgress() {
        if (getStatus()) {
            BaseActivity baseActivity = getBaseActivity();
            if (baseActivity != null) {
                baseActivity.hideProgress();
            }
        }
    }

    @Override
    public void showToast(int resId) {
        if (getStatus()) {
            BaseActivity activity = getBaseActivity();
            if (activity != null) {
                activity.showToast(resId);
            }
        }
    }

    @Override
    public void showToast(String msg) {
        if (getStatus()) {
            BaseActivity activity = getBaseActivity();
            if (activity != null) {
                activity.showToast(msg);
            }
        }
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void close() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
