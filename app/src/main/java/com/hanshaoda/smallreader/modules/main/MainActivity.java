package com.hanshaoda.smallreader.modules.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.app.BaseApplication;
import com.hanshaoda.smallreader.base.BaseCustomActivity;
import com.hanshaoda.smallreader.config.Constant;
import com.hanshaoda.smallreader.config.StatusBarCompat;
import com.hanshaoda.smallreader.database.CategoryDao;
import com.hanshaoda.smallreader.database.FunctionDao;
import com.hanshaoda.smallreader.model.CategoryManager;
import com.hanshaoda.smallreader.model.Function;
import com.hanshaoda.smallreader.model.FunctionBean;
import com.hanshaoda.smallreader.model.RefreshNewsFragmentEvent;
import com.hanshaoda.smallreader.modules.find.FindFragment;
import com.hanshaoda.smallreader.modules.me.MeFragment;
import com.hanshaoda.smallreader.modules.news.NewsFragment;
import com.hanshaoda.smallreader.modules.news_category.CategoryActivity;
import com.hanshaoda.smallreader.modules.wechat.WechatFragment;
import com.hanshaoda.smallreader.update.AppUtils;
import com.hanshaoda.smallreader.update.UpdateChecker;
import com.hanshaoda.smallreader.utils.SPUtils;
import com.hanshaoda.smallreader.utils.StateBarTranslucentUtils;
import com.hanshaoda.smallreader.utils.StreamUtils;
import com.hanshaoda.smallreader.widget.TabBar_Mains;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainActivity extends BaseCustomActivity implements View.OnClickListener {
    public static List<String> logList = new CopyOnWriteArrayList<String>();

    private static final String NEWS_FRAGMENT = "NEWS_FRAGMENT";
    private static final String WECHAT_FRAGMENT = "WECHAT_FRAGMENT";
    private static final String FIND_FRAGMENT = "FIND_FRAGMENT";
    public static final String ME_FRAGMENT = "ME_FRAGMENT";
    public static final String FROM_FLAG = "FROM_FLAG";

    public MeFragment mMeFragment;
    public NewsFragment mNewsFragment;
    public WechatFragment mWechatFragment;
    public FindFragment mFindFragment;

    FrameLayout mFramelayoutMains;
    TabBar_Mains mRecommendMains;
    TabBar_Mains mCityfinderMains;
    TabBar_Mains mFindtravelMains;
    TabBar_Mains mMeMains;

    private FragmentManager sBaseFragmentManager;
    /**
     * 存储当前Fragment的标记
     */
    private String mCurrentIndex;

    @Override
    public void initContentView() {
        StateBarTranslucentUtils.setStateBarTranslucent(this);
        setContentView(R.layout.activity_main);
        BaseApplication.setMainActivity(this);
        StatusBarCompat.compat(this);

        findView();
    }

    private void findView() {
        mFramelayoutMains = (FrameLayout) findViewById(R.id.fragment_contain);
        mRecommendMains = (TabBar_Mains) findViewById(R.id.recommend_mains);
        mCityfinderMains = (TabBar_Mains) findViewById(R.id.wechat_mains);
        mFindtravelMains = (TabBar_Mains) findViewById(R.id.finds_mains);
        mMeMains = (TabBar_Mains) findViewById(R.id.me_mains);

        mRecommendMains.setOnClickListener(this);
        mCityfinderMains.setOnClickListener(this);
        mFindtravelMains.setOnClickListener(this);
        mMeMains.setOnClickListener(this);

    }

    boolean isRestart = false;

    private void initByRestart(Bundle savedInstanceState) {

        mCurrentIndex = savedInstanceState.getString("mCurrentIndex");

        isRestart = true;
        Logger.e("恢复状态：" + mCurrentIndex);
        mMeFragment = (MeFragment) sBaseFragmentManager.findFragmentByTag(ME_FRAGMENT);
        if (mRecommendMains.getVisibility() == View.VISIBLE) {
            mNewsFragment = (NewsFragment) sBaseFragmentManager.findFragmentByTag(NEWS_FRAGMENT);
        }
        mWechatFragment = (WechatFragment) sBaseFragmentManager.findFragmentByTag(WECHAT_FRAGMENT);
        mFindFragment = (FindFragment) sBaseFragmentManager.findFragmentByTag(FIND_FRAGMENT);

        switchToFragment(mCurrentIndex);
    }

    @Override
    public void initView(Bundle savedInstanceState) {

//        saveCategoryToDB();
//        saveFunctionToDB();

        sBaseFragmentManager = getBaseFragmentManager();

        String startPage = WECHAT_FRAGMENT;
        String s = (String) SPUtils.get(this, Constant.OPENNEWS, "nomagic");
        if (s.equals("magicopen")) {
            mRecommendMains.setVisibility(View.VISIBLE);
            startPage = NEWS_FRAGMENT;
        }
        if (savedInstanceState != null) {
            initByRestart(savedInstanceState);
        } else {
            Log.e("startPage", startPage);
            switchToFragment(startPage);
            mCurrentIndex = startPage;
        }

        int qbox_version = (int) SPUtils.get(this, Constant.SMALLER_NEW_VERSION, 0);
        if (qbox_version != 0 && qbox_version > AppUtils.getVersionCode(this)) {
            UpdateChecker.checkForNotification(this); //通知提示升级
        }

        //订阅事件
        EventBus.getDefault().register(this);
    }

    private void saveFunctionToDB() {

        Function function = null;
        try {
            function = (new Gson()).fromJson(StreamUtils.get(getApplicationContext(), R.raw.function), Function.class);
        } catch (Exception e) {
            Logger.e("读取raw中的function.json文件异常" + e.getMessage());
        }
        if (function != null && function.getFunction() != null && function.getFunction().size() != 0) {
            List<FunctionBean> function1 = function.getFunction();
            FunctionDao functionDao = new FunctionDao(getApplicationContext());
            functionDao.insertFunctionList(function1);
        }
    }

    private void saveCategoryToDB() {
        CategoryDao categoryDao = new CategoryDao(getApplicationContext());
        categoryDao.deleteCategoryList();
        categoryDao.insertCategoryList(new CategoryManager(this).getAllCategory());
    }

    private void alreadyAtFragment(String mCurrentIndex) {
        switch (mCurrentIndex) {
            case NEWS_FRAGMENT:
                if (mNewsFragment != null) {
//                    sRecommendFragment.scrollToTop(true)
                }
                break;
            case ME_FRAGMENT:
                break;
            default:
                break;
        }

    }

    private void switchToFragment(String index) {
//        sFragmentTransaction = getFragmentTransaction();
        hideAllFragment();
        switch (index) {
            case NEWS_FRAGMENT:
                if (mRecommendMains.getVisibility() == View.VISIBLE) {
                    showNewsFragment();
                    Logger.e("newsopen:101");
                }
                break;
            case WECHAT_FRAGMENT:
                showWechatFragment();
                break;
            case FIND_FRAGMENT:
                showFindFragment();
                break;
            case ME_FRAGMENT:
                showMeFragment();
                break;
            default:

                break;
        }
        mCurrentIndex = index;
    }

    private void showMeFragment() {
        if (false == mMeMains.isSelected())
            mMeMains.setSelected(true);
        if (mMeFragment == null) {
            mMeFragment = MeFragment.getInstance();
            addFragment(R.id.fragment_contain, mMeFragment, ME_FRAGMENT);
        } else if (isRestart = true) {
            getFragmentTransaction().show(mMeFragment).commit();
            isRestart = false;
        } else {
            showFragment(mMeFragment);
        }
    }

    private void showFindFragment() {
        if (false == mFindtravelMains.isSelected()) {
            mFindtravelMains.setSelected(true);
        }
        if (mFindFragment == null) {
            mFindFragment = FindFragment.getInstance("", "");
            addFragment(R.id.fragment_contain, mFindFragment, FIND_FRAGMENT);
        } else if (isRestart = true) {
            isRestart = false;
            getFragmentTransaction().show(mFindFragment).commit();
        } else {
            showFragment(mFindFragment);
        }

    }

    private void showWechatFragment() {
        if (false == mCityfinderMains.isSelected()) {
            mCityfinderMains.setSelected(true);
        }
        if (mWechatFragment == null) {
            mWechatFragment = mWechatFragment.getInstance("", "");
            addFragment(R.id.fragment_contain, mWechatFragment, WECHAT_FRAGMENT);
        } else if (isRestart = true) {
            isRestart = false;
            getFragmentTransaction().show(mWechatFragment).commit();
        } else {
            showFragment(mWechatFragment);
        }

    }

    private void showNewsFragment() {
        if (mRecommendMains.getVisibility() != View.VISIBLE) {
            return;
        }
        if (false == mRecommendMains.isSelected()) {
            mRecommendMains.setSelected(true);
        }
        if (mNewsFragment == null) {
            Logger.e("恢复状态：" + "null");
            mNewsFragment = NewsFragment.getInstance("a", "b");
            addFragment(R.id.fragment_contain, mNewsFragment, NEWS_FRAGMENT);
        } else if (isRestart = true) {
            isRestart = false;
            getFragmentTransaction().show(mNewsFragment).commit();
        } else {
            showFragment(mNewsFragment);
        }

    }

    private void hideAllFragment() {
        if (mNewsFragment != null) {
            hideFragment(mNewsFragment);
        }
        if (mWechatFragment != null) {
            hideFragment(mWechatFragment);
        }
        if (mFindFragment != null) {
            hideFragment(mFindFragment);
        }
        if (mMeFragment != null) {
            hideFragment(mMeFragment);
        }
        if (mRecommendMains.getVisibility() == View.VISIBLE) {
            mRecommendMains.setSelected(false);
        }
        mFindtravelMains.setSelected(false);
        mCityfinderMains.setSelected(false);
        mMeMains.setSelected(false);
    }

    @Override
    public void initPresenter() {

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recommend_mains:
                if (!mCurrentIndex.equals(NEWS_FRAGMENT))
                    switchToFragment(NEWS_FRAGMENT);
                break;
            case R.id.wechat_mains:
                if (!mCurrentIndex.equals(WECHAT_FRAGMENT))
                    switchToFragment(WECHAT_FRAGMENT);
                break;
            case R.id.finds_mains:
                if (!mCurrentIndex.equals(FIND_FRAGMENT))
                    switchToFragment(FIND_FRAGMENT);
                break;
            case R.id.me_mains:
                if (!mCurrentIndex.equals(ME_FRAGMENT))
                    switchToFragment(ME_FRAGMENT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.NEWSFRAGMENT_CATEGORYACTIVITY_REQUESTCODE
                && resultCode == Constant.NEWSFRAGMENT_CATEGORYACTIVITY_RESULTCODE) {
            mNewsFragment.initView();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnRefreshNewsFragmentEvent(RefreshNewsFragmentEvent event) {
        startActivityForResult(new Intent(MainActivity.this, CategoryActivity.class), event.getMark_code());
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        for (Fragment fragment :
                getBaseFragmentManager().getFragments()) {
            getFragmentTransaction().remove(fragment);
        }
        super.onDestroy();
        BaseApplication.setMainActivity(null);
//        IMMLeaks.fixFocusedViewLeak(getApplication());//解决 Android 输入法造成的内存泄漏问题。
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshLogInfo();
    }

    public void refreshLogInfo() {
        String AllLog = "";
        for (String log : logList) {
            AllLog = AllLog + log + "\n\n";
        }
        Logger.e(AllLog);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("mCurrentIndex", mCurrentIndex);
        Logger.e("保存状态");
    }

    /**
     * 监听用户按返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    private boolean mIsExit;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                this.finish();
            } else {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                mIsExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 用于优雅的退出程序(当从其他地方退出应用时会先返回到此页面再执行退出)
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            boolean isExit = intent.getBooleanExtra(Constant.TAG_EXIT, false);
            if (isExit) {
                finish();
            }
        }
    }

}
