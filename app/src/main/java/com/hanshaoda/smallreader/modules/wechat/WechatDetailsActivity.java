package com.hanshaoda.smallreader.modules.wechat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.base.BaseCommonActivity;
import com.hanshaoda.smallreader.config.Constant;
import com.hanshaoda.smallreader.model.WechatItem;
import com.hanshaoda.smallreader.utils.ImageUtils;
import com.hanshaoda.smallreader.utils.SPUtils;
import com.hanshaoda.smallreader.utils.ShareUtils;
import com.hanshaoda.smallreader.widget.Html5WebView;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;

/**
 * author: hanshaoda
 * created on: 2017/9/14 下午5:58
 * description: 新闻详情页面
 */
public class WechatDetailsActivity extends BaseCommonActivity {
    @BindView(R.id.iv_image)
    ImageView mIvImage;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.webView_we_chat)
    Html5WebView mWebViewWeChat;
    @BindView(R.id.nestedscrollview_news_details)
    NestedScrollView mNestedscrollviewNewsDetails;
    @BindView(R.id.fab_share)
    FloatingActionButton mFabShare;
    private WechatItem.ResultBean.ListBean mWechat;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_we_chat_details);
    }

    @Override
    public void initView() {

        initGetIntent();
        ShareSDK.initSDK(this);
        initToolbar();
        initData();
        initFab();
        initWebView();
    }

    /**
     * 初始化
     */
    private void initWebView() {
        Logger.e("得到的URL=" + mWechat.getUrl());
        mWebViewWeChat.loadUrl(mWechat.getUrl());
    }

    /**
     * 悬浮按钮点击事件
     */
    private void initFab() {
        mFabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtils.showShare(WechatDetailsActivity.this, mWechat.getUrl(), mWechat.getTitle());
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {

        boolean isNotLoad = (boolean) SPUtils.get(this, Constant.SLLMS, false);
        if (!isNotLoad) {
            ImageUtils.loadingImgUrl(this, mWechat.getFirstImg(), R.mipmap.errorview, R.drawable.lodingview, 1000, mIvImage);

        }
        getSupportActionBar().setTitle(mWechat.getTitle());
    }

    /**
     * 初始化标题
     */
    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 设置传递的值
     */
    private void initGetIntent() {
        Intent intent = getIntent();
        mWechat = intent.getParcelableExtra("wechat");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void onDestroy() {
        if (mWebViewWeChat != null) {
            mWebViewWeChat.clearHistory();
            ((ViewGroup) mWebViewWeChat.getParent()).removeView(mWebViewWeChat);
            mWebViewWeChat.destroy();
            mWebViewWeChat = null;
        }
        super.onDestroy();
    }
}
