package com.hanshaoda.smallreader.modules.wechat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.base.BaseFragment;
import com.hanshaoda.smallreader.config.Constant;
import com.hanshaoda.smallreader.model.WechatItem;
import com.hanshaoda.smallreader.network.NetWork;
import com.hanshaoda.smallreader.utils.PixelUtil;
import com.hanshaoda.smallreader.utils.SPUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.greendao.annotation.Id;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author: hanshaoda
 * created on: 2017/9/12 下午3:57
 * description:
 */
public class WechatFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String WECHAT_APPKEY = "26ce25ffcfc907a26263e2b0e3e23676";
    @BindView(R.id.recycler_wechat)
    RecyclerView mRecyclerWechat;
    @BindView(R.id.swiper_wechat)
    SwipeRefreshLayout mSwiperWechat;
    @BindView(R.id.fab_wechat)
    FloatingActionButton mFabWechat;


    private WechatItemAdapter mWechatItemAdapter;
    private boolean mRefreshMark;
    private int mPageMark = 1;
    private int mPageSize = 21;
    private View notDataView;
    private View errorView;
    private List<WechatItem.ResultBean.ListBean> mListBeanList;

    Observer<WechatItem> mObserver = new Observer<WechatItem>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

            onErrorView();
            if (mSwiperWechat.isRefreshing()) {
                mSwiperWechat.setRefreshing(false);
            }
            if (mSwiperWechat.isEnabled()) {
                mSwiperWechat.setEnabled(false);
            }
        }


        @Override
        public void onNext(WechatItem wechatItem) {
            setNewDataAddList(wechatItem);
        }
    };
    private Subscription mSubscribe;

    private void setNewDataAddList(WechatItem wechatItem) {
        if (wechatItem != null && wechatItem.getError_code() == 0) {
            mPageMark++;
            List<WechatItem.ResultBean.ListBean> list = wechatItem.getResult().getList();
            WechatItem.ResultBean.ListBean listBean = list.get(0);
            listBean.setItemType(1);
            listBean.setSpansize(2);
            if (mRefreshMark) {
                mWechatItemAdapter.setNewData(list);
                mRefreshMark = false;
            } else {
                mWechatItemAdapter.addData(list);
            }
            if (mSwiperWechat.isRefreshing()) {
                mSwiperWechat.setRefreshing(false);
            }
            if (!mSwiperWechat.isEnabled()) {
                mSwiperWechat.setEnabled(true);
            }
            if (mWechatItemAdapter.isLoading()) {
                mWechatItemAdapter.loadMoreComplete();
            }
            if (!mWechatItemAdapter.isLoadMoreEnable()) {
                mWechatItemAdapter.setEnableLoadMore(true);
            }
        } else {
            if (mWechatItemAdapter.isLoading()) {
                Toast.makeText(getContext(), "加载更多数据失败", Toast.LENGTH_SHORT).show();
                mWechatItemAdapter.loadMoreFail();
            } else {
                mWechatItemAdapter.setEmptyView(notDataView);
                if (mSwiperWechat.isRefreshing()) {
                    mSwiperWechat.setRefreshing(false);
                }
                if (mSwiperWechat.isEnabled()) {
                    mSwiperWechat.setEnabled(false);
                }
            }

        }
    }

    private void onErrorView() {
        mWechatItemAdapter.setEmptyView(errorView);
    }


    public static WechatFragment getInstance(String param1, String param2) {
        WechatFragment fragment = new WechatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_wechat;
    }

    @Override
    public void initView() {

        initFab();
        initSwipeRefresh();
        initEmptyView();
        initRecyclerView();
        onLoading();
        requestData();
    }

    private void requestData() {
        unsubscribe();
        mSubscribe = NetWork.getWechatApi()
                .getWechat(WECHAT_APPKEY, mPageMark, mPageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }

    private void unsubscribe() {
        if (mSubscribe != null && !mSubscribe.isUnsubscribed()) {
            mSubscribe.unsubscribe();
        }
    }

    private void onLoading() {

        mWechatItemAdapter.setEmptyView(R.layout.loading_view, (ViewGroup) mRecyclerWechat.getParent());
    }

    private void initRecyclerView() {

        mRecyclerWechat.setLayoutManager(new GridLayoutManager(getContext().getApplicationContext(), 2));
        mListBeanList = new ArrayList<>();
        boolean isNotLoad = (boolean) SPUtils.get(getContext(), Constant.SLLMS, false);
        int imgWidth = PixelUtil.getWindowWidth();
        int imgHeight = imgWidth * 3 / 4;

        mWechatItemAdapter = new WechatItemAdapter(mListBeanList, isNotLoad, imgWidth, imgHeight);
        mWechatItemAdapter.openLoadAnimation();
        mWechatItemAdapter.setOnLoadMoreListener(this);
        mWechatItemAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {

                if (position == 0) {
                    return 2;
                } else {
                    return mWechatItemAdapter.getData().get(position).getSpansize();
                }
            }
        });
        mRecyclerWechat.setAdapter(mWechatItemAdapter);
        mRecyclerWechat.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Logger.e("是否点击");
                WechatItem.ResultBean.ListBean listBean = (WechatItem.ResultBean.ListBean) adapter.getData().get(position);
                Intent intent = new Intent(getContext(), WechatDetailsActivity.class);
                intent.putExtra("wechat", listBean);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        view.findViewById(R.id.img_wechat_style),
                        "transition_wechat_img");
                ActivityCompat.startActivity(getContext(), intent, optionsCompat.toBundle());
            }
        });
    }

    private void initEmptyView() {
        notDataView = getActivity().getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) mRecyclerWechat.getParent(), false);


        errorView = getActivity().getLayoutInflater().inflate(R.layout.error_view, (ViewGroup) mRecyclerWechat.getParent(), false);
    }

    private void initSwipeRefresh() {

        mSwiperWechat.setOnRefreshListener(this);
        mSwiperWechat.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwiperWechat.setEnabled(false);
    }

    private void initFab() {
        mFabWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWechatItemAdapter != null && mWechatItemAdapter.getData() != null && mWechatItemAdapter.getData().size() > 0) {
                    mRecyclerWechat.scrollToPosition(0);
                }
            }
        });
    }

    @Override
    public void managerArguments() {

    }

    @Override
    public void onRefresh() {
        mWechatItemAdapter.setEnableLoadMore(false);
        mRefreshMark = true;
        mPageMark = 1;
        requestData();
    }

    @Override
    public void onLoadMoreRequested() {
        mSwiperWechat.setEnabled(false);
        requestData();
    }
}
