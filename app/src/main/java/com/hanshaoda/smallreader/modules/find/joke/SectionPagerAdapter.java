package com.hanshaoda.smallreader.modules.find.joke;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * author: hanshaoda
 * created on: 2017/10/9 下午12:25
 * description:
 */
public class SectionPagerAdapter extends FragmentPagerAdapter {

    public Context mContext;
    public List<Fragment> mFragmentList;
    public List<String> mTilteStrings;

    public SectionPagerAdapter(FragmentManager supportFragmentManager, Context context, List<Fragment> mFragmentList, List<String> mTilteStrings) {
        super(supportFragmentManager);

        mContext = context;
        this.mFragmentList = mFragmentList;
        this.mTilteStrings = mTilteStrings;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (mTilteStrings == null || mTilteStrings.size() < position) {
            return null;
        }
        return mTilteStrings.get(position);
    }
}
