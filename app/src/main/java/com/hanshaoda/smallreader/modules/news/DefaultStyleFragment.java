package com.hanshaoda.smallreader.modules.news;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.base.BaseFragment;
import com.hanshaoda.smallreader.config.Constant;
import com.hanshaoda.smallreader.model.NewsItem;
import com.hanshaoda.smallreader.network.NetWork;
import com.hanshaoda.smallreader.utils.PixelUtil;
import com.hanshaoda.smallreader.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * author: hanshaoda
 * created on: 2017/9/13 下午2:59
 * description:
 */
public class DefaultStyleFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private static final String ARG_PARAM1 = "param1";
    //    private static final String ARG_PARAM2 = "param2";
    private static final String JUHE_NEWS_APP_KEY = "509e1319694893a9df49d9b6ea7b2ed0";
    // 最多加载的条目个数
    private static final int TOTAL_COUNTER = 30;
    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView mNewsRecyclerView;


    private List<NewsItem.ResultBean.DataBean> mNewsItemList;
    private DefaultStyleItemAdapter mAdapter;
    private int mCurrentCounter;
    private View mNoDataView;
    private View mErrorView;
    private Subscription mSubscripation;


    Observer<NewsItem> mObserver = new Observer<NewsItem>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            onErrorView();
            if (mSwipeLayout.isRefreshing()) {
                mSwipeLayout.setRefreshing(false);
            }
            if (mSwipeLayout.isEnabled()) {
                mSwipeLayout.setEnabled(false);
            }
        }

        @Override
        public void onNext(NewsItem newsItem) {
            setNewsDataAddList(newsItem);
        }
    };
    private String mParam1;

    private void setNewsDataAddList(NewsItem newsItem) {

        if (newsItem != null && newsItem.getError_code() == 0) {
            mAdapter.setNewData(newsItem.getResult().getData());
            if (mSwipeLayout.isRefreshing()) {
                mSwipeLayout.setRefreshing(false);
            }
            if (!mSwipeLayout.isEnabled()) {
                mSwipeLayout.setEnabled(true);
            }
            if (!mAdapter.isLoadMoreEnable()) {
                mAdapter.setEnableLoadMore(true);
            }
        } else {
            mAdapter.setEmptyView(mNoDataView);
            if (mSwipeLayout.isRefreshing()) {
                mSwipeLayout.setRefreshing(false);
            }
        }
    }

    private void onErrorView() {
        mAdapter.setEmptyView(mErrorView);
    }

    public static DefaultStyleFragment newInstance(String param1, String param2) {
        DefaultStyleFragment fragment = new DefaultStyleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_default_style;
    }

    @Override
    public void initView() {
        findView();
        initSwipeRefresh();
        initEmptyView();
        initRecyclerView();
        onLoading();

        requestNews();

    }

    /**
     * 发出请求
     */
    private void requestNews() {
        unsubscribed();
        mSubscripation = NetWork.getNewsApi()
                .getNews(mParam1, JUHE_NEWS_APP_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);

    }

    private void unsubscribed() {
        if (mSubscripation != null && !mSubscripation.isUnsubscribed()) {
            mSubscripation.unsubscribe();
        }
    }

    private void onLoading() {
        mAdapter.setEmptyView(R.layout.loading_view, (ViewGroup) mNewsRecyclerView.getParent());
    }

    private void initRecyclerView() {

        mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        mNewsItemList = new ArrayList<>();
        boolean isNotLoad = (boolean) SPUtils.get(getContext(), Constant.SLLMS, false);
        int imgWidth = (PixelUtil.getWindowWidth() - PixelUtil.dp2px(40)) / 3;
        int imgHeight = imgWidth / 4 * 3;
        mAdapter = new DefaultStyleItemAdapter(R.layout.news_item_default, isNotLoad, imgWidth, imgHeight);
        mAdapter.openLoadAnimation();
        mAdapter.setOnLoadMoreListener(this);
        mNewsRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<NewsItem.ResultBean.DataBean> data = adapter.getData();

                Bundle bundle = new Bundle();
                bundle.putString("url", data.get(position).getUrl());
                bundle.putString("title", data.get(position).getTitle());
                Intent intent = new Intent(getContext(), NewsDetailsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mNewsRecyclerView.setAdapter(mAdapter);
        mCurrentCounter = mAdapter.getData().size();

    }

    private void initEmptyView() {

        /**
         * 没有数据
         */
        mNoDataView = getActivity().getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) mNewsRecyclerView.getParent(), false);
        mNoDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRequestAgain();
            }
        });
/**
 * 网络请求错误或没有联网
 */
        mErrorView = getActivity().getLayoutInflater().inflate(R.layout.error_view, (ViewGroup) mNewsRecyclerView.getParent(), false);
        mErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRequestAgain();
            }
        });
    }

    /**
     * 再次请求
     */
    private void onRequestAgain() {
        requestNews();
    }

    private void initSwipeRefresh() {
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeLayout.setEnabled(false);
    }

    private void findView() {
        mSwipeLayout = getView().findViewById(R.id.swipe_layout);
        mNewsRecyclerView = getView().findViewById(R.id.news_list);
    }

    @Override
    public void managerArguments() {
        mParam1 = getArguments().getString(ARG_PARAM1);
    }

    @Override
    public void onRefresh() {

        mAdapter.setEnableLoadMore(false);
        onRequestAgain();
    }

    @Override
    public void onLoadMoreRequested() {

        mSwipeLayout.setEnabled(false);
        mNewsRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mCurrentCounter >= TOTAL_COUNTER) {
                    mAdapter.loadMoreEnd();
                } else {
                    mAdapter.addData(mNewsItemList);
                    mCurrentCounter = mAdapter.getData().size();
                    mAdapter.loadMoreComplete();//加载完成
                }
                mSwipeLayout.setEnabled(true);
            }
        }, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unsubscribed();
    }
}
