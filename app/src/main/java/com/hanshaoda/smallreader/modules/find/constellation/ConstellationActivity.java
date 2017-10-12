package com.hanshaoda.smallreader.modules.find.constellation;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.base.BaseCommonActivity;
import com.hanshaoda.smallreader.config.Constant;
import com.hanshaoda.smallreader.modules.find.joke.SectionPagerAdapter;
import com.hanshaoda.smallreader.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class ConstellationActivity extends BaseCommonActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.container)
    ViewPager mViewPager;
    @BindView(R.id.activity_constellation)
    CoordinatorLayout activityConstellation;

    private List<String> mTitleStrings;
    private List<Fragment> mFragmentList;
    private String mSelectConstellation;
    private SectionPagerAdapter mSectionPagerAdapter;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_constellation);

    }

    @Override
    public void initView() {

        initToolbar();
        initViewPager();
    }

    private void initViewPager() {

        mFragmentList = new ArrayList<>();

        mTitleStrings = Arrays.asList("今天", "明天", "本周", "下周", "本月", "今年");

        for (String fragmentName : mTitleStrings) {
            switch (fragmentName) {
                case "今天":
                    mFragmentList.add(DayConstellationFragment.newInstance(mSelectConstellation, DayConstellationFragment.DAY_TODAY));
                    break;
                case "明天":
                    mFragmentList.add(DayConstellationFragment.newInstance(mSelectConstellation, DayConstellationFragment.DAY_TOMORROW));
                    break;
                case "本周":
                    mFragmentList.add(WeekAndMonthConstellationFragment.newInstance(WeekAndMonthConstellationFragment.WEEK, mSelectConstellation));
                    break;
                case "下周":
                    mFragmentList.add(WeekAndMonthConstellationFragment.newInstance(WeekAndMonthConstellationFragment.NEXT_WEEK, mSelectConstellation));
                    break;
                case "本月":
                    mFragmentList.add(WeekAndMonthConstellationFragment.newInstance(WeekAndMonthConstellationFragment.MONTH, mSelectConstellation));
                    break;
                case "今年":
                    mFragmentList.add(YearConstellationFragment.newInstance(mSelectConstellation, ""));
                    break;
                default:
                    break;
            }
        }
        mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager(), this, mFragmentList, mTitleStrings);
        mViewPager.setAdapter(mSectionPagerAdapter);
        tabs.setupWithViewPager(mViewPager);

    }

    private void initToolbar() {

        mSelectConstellation = (String) SPUtils.get(this, Constant.USER_STAR, "水瓶座");
        toolbar.setSubtitle(mSelectConstellation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public void initPresenter() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_constellation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        if (android.R.id.home == itemId) {
            finish();
        } else {
            String chengConstellation = item.getTitle().toString();
            getSupportActionBar().setSubtitle(mSelectConstellation = chengConstellation);
            EventBus.getDefault().post(new RefreshConstellationEvent(chengConstellation));
        }
        return true;
    }
}
