package com.hanshaoda.smallreader.modules.find.joke;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
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
import com.hanshaoda.smallreader.modules.find.PinImageActivity;
import com.hanshaoda.smallreader.utils.ShareUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.sharesdk.onekeyshare.OnekeyShare;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImgJokeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String JOKE_KEY = "39094c8b40b831b8e7b7a19a20654ed7";

    //每页请求的 item 数量
    public final int mPs = 20;
    public int mPageMark = 1;
    public boolean mRefreshMark;

    int jokestyle;

    private View notDataView;
    private View errorView;

    ImgJokeAdapter textJokeAdapter;

    @BindView(R.id.recycler_img_joke)
    RecyclerView mRecyTextjoke;
    @BindView(R.id.swipe_img_joke)
    SwipeRefreshLayout mSwiperTextjoke;

    private String mParam1;
    private String mParam2;

    public List<TextJokeBean> mJokeBeanArrayList;

    private Subscription mSubscription;


    Observer<BaseJokeBean> mObserver = new Observer<BaseJokeBean>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            onErrorView();
            if (mSwiperTextjoke.isRefreshing()) {
                mSwiperTextjoke.setRefreshing(false);
            }
            if (mSwiperTextjoke.isEnabled()) {
                mSwiperTextjoke.setEnabled(false);
            }
        }

        @Override
        public void onNext(BaseJokeBean baseJokeBean) {
            //先强转baseJokeBean
            setNewDataAddList(baseJokeBean);
        }


    };

    private void setNewDataAddList(BaseJokeBean baseJokeBean) {
        if (baseJokeBean != null && baseJokeBean.getError_code() == 0) {
            mPageMark++;
            List<TextJokeBean> data = null;
            switch (jokestyle) {
                case JokeActivity.JOKESTYLE_NEW:
                    data = ((NewTextJokeBean) baseJokeBean).getResult().getData();
                    break;
                case JokeActivity.JOKESTYLE_RANDOM:
                    data = ((RandomTextJoke) baseJokeBean).getResult();
                    break;
                default:
                    break;
            }
            TextJokeBean textJokeBean = new TextJokeBean();
            textJokeBean.setItemType(TextJokeBean.MORE);
            Logger.e("数据条数" + data.size());
            data.add(textJokeBean);
            if (mRefreshMark) {
                textJokeAdapter.setNewData(data);
                mRefreshMark = false;
            } else {
                textJokeAdapter.addData(data);
            }

            if (mSwiperTextjoke.isRefreshing()) {
                mSwiperTextjoke.setRefreshing(false);
            }
            if (!mSwiperTextjoke.isEnabled()) {
                mSwiperTextjoke.setEnabled(true);
            }
            if (textJokeAdapter.isLoading()) {
                textJokeAdapter.loadMoreComplete();
            }
            if (!textJokeAdapter.isLoadMoreEnable()) {
                textJokeAdapter.setEnableLoadMore(true);
            }
        } else {
            if (textJokeAdapter.isLoading()) {
                Toast.makeText(getContext(), "加载更多数据失败！", Toast.LENGTH_SHORT).show();
                textJokeAdapter.loadMoreFail();
            } else {
                textJokeAdapter.setEmptyView(notDataView);
                if (mSwiperTextjoke.isRefreshing()) {
                    mSwiperTextjoke.setRefreshing(false);
                }
                if (mSwiperTextjoke.isEnabled()) {
                    mSwiperTextjoke.setEnabled(false);
                }
            }
        }
    }

    private void onErrorView() {
        textJokeAdapter.setEmptyView(errorView);
    }


    public static ImgJokeFragment newInstance(String param1, String param2) {
        ImgJokeFragment fragment = new ImgJokeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ImgJokeFragment() {
        // Required empty public constructor
    }


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_img_joke;
    }

    @Override
    public void initView() {
        jokestyle = ((JokeActivity) getActivity()).getJokestyle();
        EventBus.getDefault().register(this);

        initSwiper();
        initEmptyView();
        initRecy();
        onLoading();
        requestData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnRefreshNewsFragmentEvent(RefreshJokeStyleEvent event) {
        jokestyle = event.getJokeStyle();
        Logger.e("切换刷新的" + jokestyle);
        onRefresh();
    }

    private void onLoading() {
        textJokeAdapter.setEmptyView(R.layout.loading_view,
                (ViewGroup) mRecyTextjoke.getParent());
    }


    private void requestData() {
        unsubscribe();
        switch (jokestyle) {
            case JokeActivity.JOKESTYLE_NEW:
                mSubscription = NetWork.getNewImgJokeApi()
                        .getNewTextJoke(JOKE_KEY, mPageMark, mPs)//key,页码,每页条数
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mObserver);
                break;
            case JokeActivity.JOKESTYLE_RANDOM:
                mSubscription = NetWork.getRandomImgJokeApi()
                        .getRandomTextJoke(JOKE_KEY, "pic")//key,页码,每页条数
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mObserver);
                break;
            default:
                break;
        }

    }

    private void initRecy() {

        if (mJokeBeanArrayList == null) {
            mJokeBeanArrayList = new ArrayList<>();
        }

        textJokeAdapter = new ImgJokeAdapter(mJokeBeanArrayList);
        textJokeAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
//        textJokeAdapter.setOnLoadMoreListener(this);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyTextjoke.setLayoutManager(staggeredGridLayoutManager);
        mRecyTextjoke.setAdapter(textJokeAdapter);
        mRecyTextjoke.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                TextJokeBean textJokeBean = (TextJokeBean) adapter.getData().get(position);
                if (textJokeBean.getItemType() == TextJokeBean.MORE) {
                    //加载更多
                    adapter.remove(position);
                    onLoadMoreRequested();
                } else if (textJokeBean.getItemType() == TextJokeBean.JOKE) {
                    Intent intent = new Intent(getContext(), PinImageActivity.class);
                    intent.putExtra(PinImageActivity.IMG_NAME, textJokeBean.getContent());
                    intent.putExtra(PinImageActivity.IMG_URL, textJokeBean.getUrl());

                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            getActivity(),
                            view.findViewById(R.id.img_item_img_joke),
                            "transition_pinchimageview"
                    );
                    ActivityCompat.startActivity((Activity) getContext(), intent, optionsCompat.toBundle());
                }
            }
        });
        mRecyTextjoke.addOnItemTouchListener(new OnItemLongClickListener() {
            @Override
            public void onSimpleItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                TextJokeBean textJokeBean = (TextJokeBean) adapter.getData().get(position);
                if (textJokeBean.getItemType() == TextJokeBean.JOKE) {
                    ShareUtils.showShare(getContext(), textJokeBean.getUrl(), textJokeBean.getContent());
                }
            }
        });
    }

    private void initSwiper() {
        mSwiperTextjoke.setOnRefreshListener(this);
        mSwiperTextjoke.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwiperTextjoke.setEnabled(false);
    }


    @Override
    public void managerArguments() {
        mParam1 = getArguments().getString(ARG_PARAM1);
        mParam2 = getArguments().getString(ARG_PARAM2);
    }

    private void initEmptyView() {
        /**
         * 网络请求失败没有数据
         */
        notDataView = getActivity().getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) mRecyTextjoke.getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();

            }
        });

        /**
         * 网络请求错误 | 没有网络
         */
        errorView = getActivity().getLayoutInflater().inflate(R.layout.error_view, (ViewGroup) mRecyTextjoke.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        textJokeAdapter.setEnableLoadMore(false);
        mRefreshMark = true;
        mPageMark = 1;
        onLoading();
        requestData();
    }


    private void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }


    public void onLoadMoreRequested() {
        mSwiperTextjoke.setEnabled(false);
        requestData();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
