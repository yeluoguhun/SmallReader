package com.hanshaoda.smallreader.modules.news;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.base.BaseFragment;
import com.hanshaoda.smallreader.config.Constant;
import com.hanshaoda.smallreader.database.CategoryDao;
import com.hanshaoda.smallreader.model.CategoryEntity;
import com.hanshaoda.smallreader.model.RefreshNewsFragmentEvent;
import com.hanshaoda.smallreader.modules.me.MeFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author: hanshaoda
 * created on: 2017/9/12 下午3:54
 * description:
 */
public class NewsFragment extends BaseFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "arg_param1";
    private static final String ARG_PARAM2 = "arg_param2";
    ImageView mLogoNews;
    MagicIndicator mMagicIndicator;
    ImageView mAddBtn;
    ViewPager mViewPager;

    private List<CategoryEntity> mCategoryEntityList;
    private String mParam1;
    private String mParam2;

    public static NewsFragment getInstance(String param1, String param2) {
        NewsFragment newsFragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        newsFragment.setArguments(args);
        return newsFragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_news;
    }

    @Override
    public void initView() {
        findView();
        mCategoryEntityList = getCategoryFromDB();
        if (mCategoryEntityList == null) {
            mCategoryEntityList = new ArrayList<>();
        }
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < mCategoryEntityList.size(); i++) {
            if (true) {
                DefaultStyleFragment fragment = DefaultStyleFragment.newInstance(mCategoryEntityList.get(i).getKey(), "");
                fragments.add(fragment);
            } else {
                MeFragment fragment = new MeFragment();
                fragments.add(fragment);
            }
        }

        mViewPager.setAdapter(new NewsPagerAdapter(getChildFragmentManager(), fragments));
        initMagicIndicator();
    }

    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setScrollPivotX(0.35f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mCategoryEntityList == null ? 0 : mCategoryEntityList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(mCategoryEntityList.get(index).getName());
                simplePagerTitleView.setNormalColor(Color.WHITE);
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.colorAccent));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {

                WrapPagerIndicator wrapPagerIndicator = new WrapPagerIndicator(context);
                wrapPagerIndicator.setFillColor(Color.WHITE);
                return wrapPagerIndicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }

    private List<CategoryEntity> getCategoryFromDB() {

        CategoryDao categoryDao = new CategoryDao(getContext().getApplicationContext());
        return categoryDao.getCategoryAll();
    }

    private void findView() {
        mLogoNews = getView().findViewById(R.id.logo_news);
        mAddBtn = getView().findViewById(R.id.add_btn);
        mViewPager = getView().findViewById(R.id.view_pager);
        mMagicIndicator = getView().findViewById(R.id.magic_indicator);

        mAddBtn.setOnClickListener(this);
    }

    @Override
    public void managerArguments() {
        mParam1 = getArguments().getString(ARG_PARAM1);
        mParam2 = getArguments().getString(ARG_PARAM2);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (mLogoNews.getFitsSystemWindows() == false) {
                mLogoNews.setFitsSystemWindows(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                    mLogoNews.requestApplyInsets();
                }
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (mLogoNews != null) {
            if (hidden) {
                mLogoNews.setFitsSystemWindows(false);
            } else {
                mLogoNews.setFitsSystemWindows(true);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                mLogoNews.requestApplyInsets();
            }
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onClick(View view) {
        EventBus.getDefault().post(new RefreshNewsFragmentEvent(Constant.NEWSFRAGMENT_CATEGORYACTIVITY_REQUESTCODE));
    }
}
