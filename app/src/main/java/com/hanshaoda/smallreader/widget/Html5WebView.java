package com.hanshaoda.smallreader.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.utils.webviewutils.NetStatusUtil;

/**
 * author: hanshaoda
 * created on: 2017/9/18 下午3:39
 * description:
 */

public class Html5WebView extends WebView {

    private ProgressBar mProgressBar;

    private Context mContext;

    private WebsiteChangeListener mWebsiteChangeListener;

    public Html5WebView(Context context) {
        super(context);
        mContext = context;
        init();
    }


    public Html5WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public Html5WebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
//初始化顶部进度条
        mProgressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleHorizontal);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 8);
        mProgressBar.setLayoutParams(layoutParams);

        Drawable drawable = mContext.getResources().getDrawable(R.drawable.web_progress_bar_states);
        mProgressBar.setProgressDrawable(drawable);

        addView(mProgressBar);

        WebSettings settings = getSettings();
        settings.setSupportZoom(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setLoadsImagesAutomatically(true);

        settings.setJavaScriptEnabled(true);
        settings.setSupportMultipleWindows(true);

        saveData(settings);
        newWin(settings);
        setWebChromeClient(webChromeClient);
        setWebViewClient(webViewClient);
    }

    private void newWin(WebSettings settings) {

        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
    }

    private void saveData(WebSettings settings) {

        if (NetStatusUtil.isConnected(mContext)) {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCacheEnabled(true);
        String absolutePath = mContext.getCacheDir().getAbsolutePath();
        settings.setAppCachePath(absolutePath);
    }

    WebViewClient webViewClient = new WebViewClient() {
        /**
         * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            Log.d("Url:", url);
            if (mWebsiteChangeListener != null) {
                mWebsiteChangeListener.onUrlChange(url);
            }
            return true;
        }
    };
    WebChromeClient webChromeClient = new WebChromeClient() {

        //=========HTML5定位==========================================================
        //需要先加入权限
        //<uses-permission android:name="android.permission.INTERNET"/>
        //<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
        //<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
        }

        @Override
        public void onGeolocationPermissionsHidePrompt() {
            super.onGeolocationPermissionsHidePrompt();
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);//注意个函数，第二个参数就是是否同意定位权限，第三个是是否希望内核记住
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
        //=========HTML5定位==========================================================


        //        多窗口问题
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            HitTestResult hitTestResult = view.getHitTestResult();
            String extra = hitTestResult.getExtra();
            view.loadUrl(extra);
            return true;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressBar.setVisibility(GONE);
            } else {
                if (mProgressBar.getVisibility() == GONE)
                    mProgressBar.setVisibility(VISIBLE);
                mProgressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (mWebsiteChangeListener != null) {
                mWebsiteChangeListener.onWebsiteChange(title);
            }
        }
    };


    public interface WebsiteChangeListener {
        void onWebsiteChange(String title);

        void onUrlChange(String url);
//        void onWebsiteChangeBackTop();
    }

    public void setWebsiteChangeListener(WebsiteChangeListener websiteChangeListener) {
        this.mWebsiteChangeListener = websiteChangeListener;

    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams layoutParams = (LayoutParams) mProgressBar.getLayoutParams();
        layoutParams.x = l;
        layoutParams.y = t;
        mProgressBar.setLayoutParams(layoutParams);
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
