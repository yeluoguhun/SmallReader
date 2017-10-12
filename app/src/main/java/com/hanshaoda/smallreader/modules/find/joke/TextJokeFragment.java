package com.hanshaoda.smallreader.modules.find.joke;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.base.BaseFragment;
import com.hanshaoda.smallreader.model.BaseJokeBean;
import com.hanshaoda.smallreader.model.NewTextJokeBean;
import com.hanshaoda.smallreader.model.RandomTextJoke;
import com.hanshaoda.smallreader.model.RefreshJokeStyleEvent;
import com.hanshaoda.smallreader.model.TextJokeBean;
import com.hanshaoda.smallreader.network.NetWork;
import com.hanshaoda.smallreader.utils.ShareUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author: hanshaoda
 * created on: 2017/10/9 下午12:17
 * description:
 */
public class TextJokeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String ARG_PARAM1 = "ARG_PARAM1";
    public static final String ARG_PARAM2 = "ARG_PARAM2";
    public static final String JOKE_KEY = "39094c8b40b831b8e7b7a19a20654ed7";
    @BindView(R.id.recycler_text_joke)
    RecyclerView mRecyclerTextJoke;
    @BindView(R.id.swipe_text_layout)
    SwipeRefreshLayout mSwipeTextLayout;

    public final int mPageSize = 20;
    public int mPageMark = 1;
    public boolean mRefreshMark;

    public int mJokeStyle;
    private View notDataView;
    private View errorView;
    private String mParam1;
    private String mParam2;

    public List<TextJokeBean> mJokeBeanList;
    private TextJokeAdapter mTextJokeAdapter;

    Observer<BaseJokeBean> mObserver = new Observer<BaseJokeBean>() {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

            Logger.e(e.getMessage());
            onErrorView();
            if (mSwipeTextLayout.isRefreshing()) {
                mSwipeTextLayout.setRefreshing(false);
            }
            if (mSwipeTextLayout.isEnabled()) {
                mSwipeTextLayout.setEnabled(false);
            }
        }

        @Override
        public void onNext(BaseJokeBean baseJokeBean) {

            setNewDataAddList(baseJokeBean);
        }
    };
    private Subscription mSubscribe;

    private void setNewDataAddList(BaseJokeBean baseJokeBean) {

        if (baseJokeBean != null && baseJokeBean.getError_code() == 0) {
            mPageMark++;
            List<TextJokeBean> data = null;
            switch (mJokeStyle) {
                case JokeActivity.JOKESTYLE_NEW:
                    data = ((NewTextJokeBean) baseJokeBean).getResult().getData();
                    break;
                case JokeActivity.JOKESTYLE_RANDOM:
                    data = ((RandomTextJoke) baseJokeBean).getResult();
                    break;
                default:
                    break;
            }
            TextJokeBean bean = new TextJokeBean();
            bean.setItemType(TextJokeBean.MORE);
            Logger.e("数据条数：" + data.size());

            data.add(bean);
            if (mRefreshMark) {
                mTextJokeAdapter.setNewData(data);
                mRefreshMark = false;
            } else {
                mTextJokeAdapter.addData(data);
            }

            if (mSwipeTextLayout.isRefreshing()) {
                mSwipeTextLayout.setRefreshing(false);
            }
            if (!mSwipeTextLayout.isEnabled()) {
                mSwipeTextLayout.setEnabled(true);
            }
            if (mTextJokeAdapter.isLoading()) {
                mTextJokeAdapter.loadMoreComplete();
            }
            if (!mTextJokeAdapter.isLoadMoreEnable()) {
                mTextJokeAdapter.setEnableLoadMore(true);
            }


        } else {
            if (mTextJokeAdapter.isLoading()) {
                Toast.makeText(getContext(), "加载更多数据失败！", Toast.LENGTH_SHORT).show();
                mTextJokeAdapter.loadMoreFail();
            } else {
                mTextJokeAdapter.setEmptyView(notDataView);
                if (mSwipeTextLayout.isRefreshing()) {
                    mSwipeTextLayout.setRefreshing(false);
                }
                if (mSwipeTextLayout.isEnabled()) {
                    mSwipeTextLayout.setEnabled(false);
                }
            }
        }
    }

    private void onErrorView() {
        mTextJokeAdapter.setEmptyView(errorView);
    }

    public static TextJokeFragment newInstance(String param1, String param2) {
        TextJokeFragment fragment = new TextJokeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, param1);
        bundle.putString(ARG_PARAM2, param2);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_text_joke;
    }

    @Override
    public void initView() {

        mJokeStyle = ((JokeActivity) getActivity()).getJokestyle();
        initSwipe();
        initEmptyView();
        initRecycler();
        onLoading();
        requestData();
    }

    private void requestData() {
        unSubscribe();
        switch (mJokeStyle) {
            case JokeActivity.JOKESTYLE_NEW:
                mSubscribe = NetWork.getNewTextJokeApi()
                        .getNewTextJoke(JOKE_KEY, mPageMark, mPageSize)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mObserver);

                break;
            case JokeActivity.JOKESTYLE_RANDOM:
                mSubscribe = NetWork.getRandomTextJokeApi()
                        .getRandomTextJoke(JOKE_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mObserver);
                break;
            default:
                break;
        }
    }

    private void onLoading() {

        mTextJokeAdapter.setEmptyView(R.layout.loading_view, (ViewGroup) mRecyclerTextJoke.getParent());
    }

    private void initRecycler() {

        if (mJokeBeanList == null) {
            mJokeBeanList = new ArrayList<>();
        }

        mTextJokeAdapter = new TextJokeAdapter(mJokeBeanList);
        mTextJokeAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerTextJoke.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerTextJoke.setAdapter(mTextJokeAdapter);
        mRecyclerTextJoke.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (((TextJokeBean) adapter.getData().get(position)).getItemType() == TextJokeBean.MORE) {
                    adapter.remove(position);
                    onLoadMoreRequested();
                }
            }
        });
        mRecyclerTextJoke.addOnItemTouchListener(new OnItemLongClickListener() {
            @Override
            public void onSimpleItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                TextJokeBean bean = (TextJokeBean) adapter.getData().get(position);
                if (bean.getItemType() == TextJokeBean.JOKE) {
                    ShareUtils.showShare(getContext(), bean.getContent());
                }
            }
        });

    }

    /**
     * 加载更多
     */
    private void onLoadMoreRequested() {
        mSwipeTextLayout.setEnabled(false);
        requestData();
    }

    private void initEmptyView() {

        notDataView = getActivity().getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) mRecyclerTextJoke.getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRefresh();
            }
        });
        errorView = getActivity().getLayoutInflater().inflate(R.layout.error_view, (ViewGroup) mRecyclerTextJoke.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRefresh();
            }
        });
    }

    private void initSwipe() {
        mSwipeTextLayout.setOnRefreshListener(this);
        mSwipeTextLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeTextLayout.setEnabled(false);
    }

    @Override
    public void managerArguments() {
        mParam1 = getArguments().getString(ARG_PARAM1);
        mParam2 = getArguments().getString(ARG_PARAM2);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {

        mTextJokeAdapter.setEnableLoadMore(false);
        mRefreshMark = true;
        mPageMark = 1;
        onLoading();
        requestData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefresh(RefreshJokeStyleEvent event) {
        mJokeStyle = event.getJokeStyle();
        onRefresh();
    }

    public void unSubscribe() {
        if (mSubscribe != null && !mSubscribe.isUnsubscribed()) {
            mSubscribe.unsubscribe();
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
