package com.hanshaoda.smallreader.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author: hanshaoda
 * created on: 2017/9/9 上午10:01
 * description:
 */

public class CustomPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> mFragments;
    List<String> mTitle;

    public CustomPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setPagers(List<Fragment> fragments) {
        mFragments = fragments;
    }

    /**
     * 添加一个页面
     *
     * @param fragment
     */
    public void addPager(Fragment fragment) {
        if (mFragments == null) {
            ArrayList<Fragment> fragments = new ArrayList<>();
            fragments.add(fragment);
            setPagers(fragments);
        } else {
            mFragments.add(fragment);
        }

    }

    public void addPagers(List<Fragment> fragments) {
        if (mFragments == null) {
            setPagers(fragments);
        } else {
            mFragments.addAll(fragments);
        }

    }

    public void setTitle(List<String> titles) {
        mTitle = titles;
    }

    public void addTtile(String title) {
        if (mTitle == null) {
            ArrayList<String> titles = new ArrayList<>();
            titles.add(title);
            setTitle(titles);
        } else {
            mTitle.add(title);
        }
    }

    public void addTitles(List<String> titles) {
        if (mTitle == null) {
            setTitle(titles);
        } else {
            mTitle.addAll(titles);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle == null ? null : mTitle.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments == null ? null : mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }
}
