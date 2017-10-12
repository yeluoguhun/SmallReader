package com.hanshaoda.smallreader.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hanshaoda.smallreader.widget.HeaderAndFooterRecyclerViewAdapter;

/**
 * author: hanshaoda
 * created on: 2017/9/6 下午5:46
 * description:
 */

public class RecyclerViewUtils {

    /**
     * 设置headerView
     *
     * @param recyclerView
     * @param view
     */
    public static void setHeaderView(RecyclerView recyclerView, View view) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null || !(adapter instanceof HeaderAndFooterRecyclerViewAdapter)) {
            return;
        }
        HeaderAndFooterRecyclerViewAdapter headerAndFooterAdapter = (HeaderAndFooterRecyclerViewAdapter) adapter;
        if (headerAndFooterAdapter.getHeaderViewCount() == 0) {
            headerAndFooterAdapter.addHeaderView(view);
        }

    }

    /**
     * 设置footerView
     *
     * @param recyclerView
     * @param view
     */
    public static void setFooterView(RecyclerView recyclerView, View view) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();

        if (adapter == null || !(adapter instanceof HeaderAndFooterRecyclerViewAdapter)) {
            return;
        }
        HeaderAndFooterRecyclerViewAdapter headerAndFooterAdapter = (HeaderAndFooterRecyclerViewAdapter) adapter;
        if (headerAndFooterAdapter.getFooterViewCount() == 0) {
            headerAndFooterAdapter.addFooterView(view);
        }
    }

    /**
     * 移除footerView
     */
    public static void removeFooterView(RecyclerView recyclerView) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter != null && adapter instanceof HeaderAndFooterRecyclerViewAdapter) {
            int footerViewCount = ((HeaderAndFooterRecyclerViewAdapter) adapter).getFooterViewCount();
            if (footerViewCount > 0) {
                View footerView = ((HeaderAndFooterRecyclerViewAdapter) adapter).getFooterView();
                ((HeaderAndFooterRecyclerViewAdapter) adapter).removeFooterView(footerView);
            }
        }
    }

    /**
     * 移除headerView
     *
     * @param recyclerView
     */
    public static void removeHeaderView(RecyclerView recyclerView) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter != null && adapter instanceof HeaderAndFooterRecyclerViewAdapter) {
            int headerViewCount = ((HeaderAndFooterRecyclerViewAdapter) adapter).getHeaderViewCount();
            if (headerViewCount > 0) {
                View headerView = ((HeaderAndFooterRecyclerViewAdapter) adapter).getHeaderView();
                ((HeaderAndFooterRecyclerViewAdapter) adapter).removeHeaderView(headerView);
            }
        }
    }

    /**
     * 使用此方法替代RecyclerView.ViewHolder 的getLayoutPosition()方法
     *
     * @param recyclerView
     * @param holder
     * @return
     */
    public static int getLayoutPosition(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter != null && adapter instanceof HeaderAndFooterRecyclerViewAdapter) {
            int headerViewCount = ((HeaderAndFooterRecyclerViewAdapter) adapter).getHeaderViewCount();
            if (headerViewCount > 0) {
                return holder.getLayoutPosition() - headerViewCount;
            }
        }
        return holder.getLayoutPosition();
    }


    /**
     * 用此方法替代RecycerView.ViewHolder 的getAdapterPosition()方法
     *
     * @param recyclerView
     * @param holder
     * @return
     */
    public static int getAdapterPosition(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter != null && adapter instanceof HeaderAndFooterRecyclerViewAdapter) {
            int headerViewCount = ((HeaderAndFooterRecyclerViewAdapter) adapter).getHeaderViewCount();
            if (headerViewCount > 0) {
                return holder.getAdapterPosition() - headerViewCount;
            }
        }
        return holder.getAdapterPosition();
    }
}
