package com.hanshaoda.smallreader.widget;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * author: hanshaoda
 * created on: 2017/9/6 下午6:29
 * description:
 */
public class HeaderAndFooterRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER_VIEW = Integer.MIN_VALUE;
    private static final int TYPE_FOOTER_VIEW = Integer.MIN_VALUE + 1;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mInnerAdapter;

    private ArrayList<View> mHeaderViews = new ArrayList<>();
    private ArrayList<View> mFooterViews = new ArrayList<>();

    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {

        @Override
        public void onChanged() {
            super.onChanged();
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            notifyItemRangeChanged(positionStart + getHeaderViewCount(), itemCount);
        }


        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            notifyItemRangeRemoved(positionStart + getHeaderViewCount(), itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            int headerViewCount = getHeaderViewCount();
            notifyItemRangeChanged(fromPosition + headerViewCount, toPosition + headerViewCount + itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            notifyItemRangeInserted(positionStart + getHeaderViewCount(), itemCount);
        }
    };

    public HeaderAndFooterRecyclerViewAdapter() {
    }

    public HeaderAndFooterRecyclerViewAdapter(RecyclerView.Adapter innerAdapter) {
        setAdapter(innerAdapter);
    }

    /**
     * 设置adapter
     *
     * @param innerAdapter
     */
    private void setAdapter(RecyclerView.Adapter innerAdapter) {
        if (innerAdapter != null) {
            if (!(innerAdapter instanceof RecyclerView.Adapter)) {
                throw new RuntimeException("your adapter must be a RecyclerView.Adapter");
            }
        }

        if (mInnerAdapter != null) {
            notifyItemRangeRemoved(getHeaderViewCount(), mInnerAdapter.getItemCount());
            mInnerAdapter.unregisterAdapterDataObserver(mDataObserver);
        }
        this.mInnerAdapter = innerAdapter;
        notifyItemRangeInserted(getHeaderViewCount(), mInnerAdapter.getItemCount());
    }

    public RecyclerView.Adapter getInnerAdapter() {
        return mInnerAdapter;
    }


    public int getHeaderViewCount() {
        return mHeaderViews.size();
    }

    public int getFooterViewCount() {
        return mFooterViews.size();
    }

    public void addHeaderView(View header) {
        if (header == null) {
            throw new RuntimeException("header is null");
        }
        mHeaderViews.add(header);
        this.notifyDataSetChanged();
    }

    public void addFooterView(View footer) {
        if (footer == null) {
            throw new RuntimeException("footer is null");
        }
        mFooterViews.add(footer);
        this.notifyDataSetChanged();
    }

    public View getFooterView() {
        return getFooterViewCount() > 0 ? mFooterViews.get(0) : null;
    }

    public View getHeaderView() {
        return getHeaderViewCount() > 0 ? mHeaderViews.get(0) : null;
    }

    public void removeFooterView(View view) {
        mFooterViews.remove(view);
        this.notifyDataSetChanged();
    }

    public void removeHeaderView(View view) {
        mHeaderViews.remove(view);
        this.notifyDataSetChanged();
    }

    public boolean isHeader(int position) {
        return getHeaderViewCount() > 0 && position == 0;
    }

    public boolean isFooter(int postion) {
        int lastPosition = getItemCount() - 1;
        return getFooterViewCount() > 0 && postion == lastPosition;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int headerViewCount = getHeaderViewCount();
        if (viewType < TYPE_HEADER_VIEW + headerViewCount) {
            return new ViewHolder(mHeaderViews.get(viewType - TYPE_HEADER_VIEW));
        } else if (viewType > TYPE_FOOTER_VIEW && viewType < Integer.MAX_VALUE / 2) {
            return new ViewHolder(mFooterViews.get(viewType - TYPE_FOOTER_VIEW));
        } else {
            return mInnerAdapter.onCreateViewHolder(parent, viewType - Integer.MAX_VALUE / 2);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int headerViewCount = getHeaderViewCount();
        if (position >= headerViewCount && position < headerViewCount + mInnerAdapter.getItemCount()) {
            mInnerAdapter.onBindViewHolder(holder, position - headerViewCount);
        } else {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        int itemCount = mInnerAdapter.getItemCount();
        int headerViewCount = getHeaderViewCount();
        if (position < headerViewCount) {
            return TYPE_HEADER_VIEW + position;
        } else if (headerViewCount <= position && position < headerViewCount + itemCount) {
            int itemViewType = mInnerAdapter.getItemViewType(position - headerViewCount);
            if (itemViewType >= Integer.MAX_VALUE / 2) {
                throw new RuntimeException("your adapter's return value of getViewTypeCount() must <Integer.MAX_VALUE/2");
            }
            return itemViewType + Integer.MAX_VALUE / 2;
        } else {
            return TYPE_FOOTER_VIEW + position - headerViewCount - itemCount;
        }

    }

    @Override
    public int getItemCount() {
        return getHeaderViewCount() + getFooterViewCount() + mInnerAdapter.getItemCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
