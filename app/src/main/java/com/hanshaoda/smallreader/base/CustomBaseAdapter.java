package com.hanshaoda.smallreader.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * author: hanshaoda
 * created on: 2017/9/9 上午9:48
 * description:
 */

public abstract class CustomBaseAdapter<T> extends BaseAdapter {


    private Context mContext;
    private LayoutInflater mInflater;
    private List<T> mData;

    public CustomBaseAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    public Context getContext() {
        return mContext;
    }

    /**
     * 获取item布局
     */
    public View getItemView(int resource, ViewGroup parent) {
        return mInflater.inflate(resource, parent, false);
    }

    public T getItemData(int position) {
        return mData == null ? null : mData.get(position);

    }

    public List<T> getmData() {
        return mData;
    }

    public void setmData(List<T> mData) {
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData == null ? null : mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public abstract View getView(int i, View view, ViewGroup viewGroup);
}
