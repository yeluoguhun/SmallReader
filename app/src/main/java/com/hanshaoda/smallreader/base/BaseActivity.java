package com.hanshaoda.smallreader.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.hanshaoda.smallreader.widget.custom.CustomConfirmDialog;

import butterknife.ButterKnife;

/**
 * author: hanshaoda
 * created on: 2017/9/7 下午5:37
 * description:
 */
public abstract class BaseActivity extends FragmentActivity implements IBaseView {

    private ProgressDialog mProgressDialog;
    FragmentManager fragmentManager;

    /**
     * 初始化布局
     */
    public abstract void initContentView();

    /**
     * 初始化布局
     */
    public abstract void initView();

    /**
     * 初始化控制中心
     */
    public abstract void initPresenter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        隐藏状态栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initContentView();
        ButterKnife.bind(this);
        initPresenter();
        initView();
    }

    @Override
    public void finish() {
        super.finish();
    }

    /**
     * 显示单选对话框
     *
     * @param title           标题
     * @param message         提示信息
     * @param strings         默认选中
     * @param checkdItem      选项数组
     * @param onClickListener 点击事件的监听
     */
    public void showRadioButtonDialog(String title, String message, String[] strings, int checkdItem,
                                      DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message);
        }
        builder.setSingleChoiceItems(strings, checkdItem, onClickListener);
        builder.create();
        builder.show();
    }

    /**
     * 显示单选对话框
     *
     * @param title           标题
     * @param strings         选项数组
     * @param onClickListener 点击事件的监听
     */
    public void showRadioButtonDialog(String title, String[] strings, DialogInterface.OnClickListener onClickListener) {
        showRadioButtonDialog(title, null, strings, 0, onClickListener);
    }

    /**
     * 弹出自定义对话框
     *
     * @param title            标题
     * @param positiveListener 点击事件
     */
    public void showConfirmDialog(String title, View.OnClickListener positiveListener) {
        CustomConfirmDialog customConfirmDialog = new CustomConfirmDialog(this, title, positiveListener);
        customConfirmDialog.show();
    }


    @Override
    public void showProgress(boolean flag, String message) {

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(flag);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setMessage(message);
        }
        mProgressDialog.show();
    }

    @Override
    public void showProgress(String message) {
        showProgress(true, message);
    }

    @Override
    public void showProgress() {
        showProgress(true);
    }

    @Override
    public void showProgress(boolean flag) {
        showProgress(flag, "");
    }

    @Override
    public void hideProgress() {
        if (mProgressDialog == null) {
            return;
        }
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(int resId) {
        showToast(getString(resId));
    }

    @Override
    public void showToast(String msg) {
        if (!isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void close() {
        finish();
    }


    //-----Fragment相关--------------

    /**
     * 获取Fragment管理器
     *
     * @return 管理器
     */
    public FragmentManager getBaseFragmentManager() {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        return fragmentManager;
    }

    /**
     * 获取事务管理器
     */
    public android.support.v4.app.FragmentTransaction getFragmentTransaction() {
        return getBaseFragmentManager().beginTransaction();
    }

    /**
     * 替换fragment
     *
     * @param res      布局资源
     * @param fragment fragment
     */
    public void replaceFragment(int res, BaseFragment fragment) {
        replaceFragment(res, fragment, false);
    }

    /**
     * 替换Fragment并设置是否加入回退栈
     *
     * @param res              布局文件
     * @param fragment         fragment
     * @param isAddToBackStack 回退栈
     */
    public void replaceFragment(int res, BaseFragment fragment, boolean isAddToBackStack) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentTransaction();
        fragmentTransaction.replace(res, fragment);
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    /**
     * 添加一个Fragment
     */
    public void addFragment(int res, BaseFragment fragment) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentTransaction();
        fragmentTransaction.add(res, fragment);
        fragmentTransaction.commit();
    }

    public void addFragment(int res, BaseFragment fragment, String flag) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentTransaction();
        fragmentTransaction.add(res, fragment, flag);
        fragmentTransaction.commit();
    }

    /**
     * 移除一个Fragment
     */
    public void removeFragment(BaseFragment fragment) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }

    /**
     * 显示一个Fragment
     */
    public void showFragment(BaseFragment fragment) {
        if (fragment.isHidden()) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentTransaction();
            fragmentTransaction.show(fragment);
            fragmentTransaction.commit();
        }
    }

    /**
     * 隐藏一个Fragment
     */
    public void hideFramgent(BaseFragment fragment) {
        if (!fragment.isHidden()) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentTransaction();
            fragmentTransaction.hide(fragment);
            fragmentTransaction.commit();
        }
    }


}
