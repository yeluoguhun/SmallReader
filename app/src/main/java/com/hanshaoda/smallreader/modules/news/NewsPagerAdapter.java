package com.hanshaoda.smallreader.modules.news;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

/**
 * author: hanshaoda
 * created on: 2017/9/13 下午5:56
 * description:
 */
public class NewsPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public NewsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragmentList = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }
}
