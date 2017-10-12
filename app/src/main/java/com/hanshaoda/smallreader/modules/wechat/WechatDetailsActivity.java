package com.hanshaoda.smallreader.modules.wechat;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.base.BaseCommonActivity;
import com.hanshaoda.smallreader.widget.Html5WebView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: hanshaoda
 * created on: 2017/9/14 下午5:58
 * description:
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

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_we_chat_details);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initPresenter() {

    }

}
