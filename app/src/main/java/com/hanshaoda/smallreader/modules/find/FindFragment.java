package com.hanshaoda.smallreader.modules.find;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.base.BaseFragment;
import com.hanshaoda.smallreader.base.Html5Activity;
import com.hanshaoda.smallreader.config.Constant;
import com.hanshaoda.smallreader.database.FunctionDao;
import com.hanshaoda.smallreader.model.ChinaCalendar;
import com.hanshaoda.smallreader.model.Constellation;
import com.hanshaoda.smallreader.model.DayJoke;
import com.hanshaoda.smallreader.model.FindBg;
import com.hanshaoda.smallreader.model.FunctionBean;
import com.hanshaoda.smallreader.model.RefreshFindFragmentEvent;
import com.hanshaoda.smallreader.modules.find.chinacalendar.ChinaCalendarActivity;
import com.hanshaoda.smallreader.modules.find.constellation.ConstellationActivity;
import com.hanshaoda.smallreader.modules.find.joke.JokeActivity;
import com.hanshaoda.smallreader.network.NetWork;
import com.hanshaoda.smallreader.utils.DateUtils;
import com.hanshaoda.smallreader.utils.PixelUtil;
import com.hanshaoda.smallreader.utils.SPUtils;
import com.hanshaoda.smallreader.widget.SlidingLayout;
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
 * created on: 2017/9/12 下午3:58
 * description:发现页面
 */
public class FindFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String BG_BASE_URL = "http://www.bing.com";
    @BindView(R.id.bg_find)
    KenBurnsView mBgFind;
    @BindView(R.id.recycler_find)
    RecyclerView mRecyclerFind;
    @BindView(R.id.xiaohua_find)
    TextView mXiaohuaFind;
    @BindView(R.id.joke_find)
    LinearLayout mJokeFind;
    @BindView(R.id.year_calendar)
    TextView mYearCalendar;
    @BindView(R.id.years_calendar)
    TextView mYearsCalendar;
    @BindView(R.id.day_clendar)
    TextView mDayClendar;
    @BindView(R.id.nongli_calendar)
    TextView mNongliCalendar;
    @BindView(R.id.jieri_calendar)
    TextView mJieriCalendar;
    @BindView(R.id.week_calendar)
    TextView mWeekCalendar;
    @BindView(R.id.yi_calendar)
    TextView mYiCalendar;
    @BindView(R.id.ji_calendar)
    TextView mJiCalendar;
    @BindView(R.id.wannianli_find)
    LinearLayout mWannianliFind;
    @BindView(R.id.qfriend_star_find)
    TextView mQfriendStarFind;
    @BindView(R.id.yunshi_star_find)
    TextView mYunshiStarFind;
    @BindView(R.id.xz_star_find)
    TextView mXzStarFind;
    @BindView(R.id.star_find)
    LinearLayout mStarFind;
    @BindView(R.id.the_footer_find)
    LinearLayout mTheFooterFind;
    @BindView(R.id.web_layout)
    SlidingLayout mWebLayout;
    @BindView(R.id.bg_title_find)
    TextView mBgTitleFind;
    @BindView(R.id.before_find)
    ImageButton mBeforeFind;
    @BindView(R.id.next_find)
    ImageButton mNextFind;
    private int mBgFlag;


    private int[] imgIcon = new int[]{R.mipmap.calendar,
            R.mipmap.fastmail,
            R.mipmap.joke,
            R.mipmap.stars,
            R.mipmap.mobile,
            R.mipmap.ic_add_white_24dp};
    private List<FindBg.ImagesBean> mImages;
    public String mNowBgName;
    public String mNowBgUrl;

    Observer<FindBg> mFindBgObserver = new Observer<FindBg>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(FindBg findBg) {
            if (findBg != null) {
                mImages = findBg.getImages();
            }
            setBg(mBgFlag);
        }
    };
    Observer<Constellation> mConstellationObserver = new Observer<Constellation>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Logger.e(e.getMessage());
        }

        @Override
        public void onNext(Constellation constellation) {

            if (constellation.getError_code() == 0) {
                showConstellation(constellation);
            }
        }
    };

    Observer<ChinaCalendar> mObserver = new Observer<ChinaCalendar>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(ChinaCalendar chinaCalendar) {

            if (chinaCalendar.getError_code() == 0) {
                initDateView(chinaCalendar.getResult().getData());
            } else {
                mYunshiStarFind.setText("请求数据失败");
            }
        }
    };
    private Subscription mDayJokeSubscribe;

    /**
     * 填充数据
     * @param data 数据源
     */
    private void initDateView(ChinaCalendar.ResultBean.DataBean data) {
        mJieriCalendar.setText(data.getHoliday() + "");
        mNongliCalendar.setText("农历" + data.getLunar());
        mYearCalendar.setText(data.getYearmonth() + "");
        mDayClendar.setText(data.getDate().split("-")[2] + "");
        mYearsCalendar.setText(data.getAnimalsYear() + "." + data.getLunarYear());
        mWeekCalendar.setText(data.getWeekday() + "");
        mYiCalendar.setText(data.getSuit() + "");
        mJiCalendar.setText(data.getAvoid() + "");
    }


    Observer<DayJoke> mDayJokeObserver = new Observer<DayJoke>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(DayJoke dayJoke) {

            if (dayJoke.getError_code() == 0 && dayJoke.getResult() != null && dayJoke.getResult().getData() != null) {
                showDayJoke(dayJoke.getResult().getData().get(0));
            }
        }
    };

    private void showDayJoke(DayJoke.ResultBean.DataBean dataBean) {
        String jokeContent = dataBean.getContent();
        if (!TextUtils.isEmpty(jokeContent)) {
            mXiaohuaFind.setText(jokeContent);
        }
    }

    private Subscription mConstellationSubscribe;
    private Subscription mFindBgSubscribtion;
    private List<FunctionBean> mFindList;
    private FindAdapter mFindAdapter;
    private ItemDragAndSwipeCallback mItemDragAndSwipeCallback;
    private ItemTouchHelper mItemTouchHelper;
    private Subscription mCalendarSubscribe;

    private void showConstellation(Constellation constellation) {

        mQfriendStarFind.setText(constellation.getQFriend());
        mYunshiStarFind.setText(constellation.getSummary());
    }


    public static FindFragment getInstance(String param1, String param2) {
        FindFragment findFragment = new FindFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        findFragment.setArguments(args);
        return findFragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_find;
    }

    @Override
    public void initView() {

        EventBus.getDefault().register(this);
        mBgFlag = 0;

        initBg();
        initBottomContext();
        initRecycler();
    }

    /**
     * 初始化recycler
     */
    private void initRecycler() {
        mFindList = initData();

        mRecyclerFind.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mFindAdapter = new FindAdapter(mFindList);
        mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mFindAdapter);
        mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerFind);

        mFindAdapter.enableDragItem(mItemTouchHelper);
        mFindAdapter.setOnItemDragListener(new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {

            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {

                FunctionDao functionDao = new FunctionDao(getContext().getApplicationContext());
                List<FunctionBean> data = mFindAdapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    FunctionBean bean = data.get(i);
                    if (bean.getId() != i) {
                        bean.setId(i);
                        functionDao.updateFunction(bean);
                    }
                }
            }
        });

        mRecyclerFind.setAdapter(mFindAdapter);
        mRecyclerFind.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                String name = ((FunctionBean) adapter.getData().get(position)).getName();
                itemActionEvent(name);
            }
        });
    }

    /**
     * 点击事件
     * @param name 名称
     */
    private void itemActionEvent(String name) {
        switch (name) {
            case "万年历":
                startActivity(new Intent(getContext(), ChinaCalendarActivity.class));
                break;
            case "快递查询":
                Bundle bundle = new Bundle();
                bundle.putString("url", "https://m.kuaidi100.com/");
                startActivity(new Intent(getContext(), Html5Activity.class).putExtra("bundle", bundle));
                break;
            case "笑话大全":
                startActivity(new Intent(getContext(), JokeActivity.class));
                break;
            case "星座运势":
                startActivity(new Intent(getContext(), ConstellationActivity.class));
                break;
            case "手机归属地":
                startActivity(new Intent(getContext(), QueryInfoActivity.class)
                        .putExtra(QueryInfoActivity.QUERY_STYLE, QueryInfoActivity.QUERY_TEL));
                break;
            case "更多":
                Toast.makeText(getActivity(), "暂未开通", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent());
                break;
            default:
                break;

        }
    }

    private List<FunctionBean> initData() {

//        FunctionDao functionDao = new FunctionDao(getContext().getApplicationContext());

        List<FunctionBean> list = new ArrayList<>();
        FunctionBean bean = new FunctionBean("万年历", imgIcon[0]);
        FunctionBean bean1 = new FunctionBean("快递查询", imgIcon[1]);
        FunctionBean bean2 = new FunctionBean("笑话大全", imgIcon[2]);
        FunctionBean bean3 = new FunctionBean("星座运势", imgIcon[3]);
        FunctionBean bean4 = new FunctionBean("手机归属地", imgIcon[4]);
        FunctionBean bean5 = new FunctionBean("更多", imgIcon[5]);

        list.add(bean);
        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        list.add(bean4);
        list.add(bean5);

        return list;
    }

    /**
     * 屏幕下方内容
     */
    private void initBottomContext() {

        boolean startIsOpen = (boolean) SPUtils.get(getContext(), Constant.STAR_IS_OPEN, true);
        boolean jokeIsOpen = (boolean) SPUtils.get(getContext(), Constant.JOKE_IS_OPEN, true);
        boolean wannianIsOpen = (boolean) SPUtils.get(getContext(), Constant.STUFF_IS_OPEN, true);

        if (!startIsOpen && !jokeIsOpen && !wannianIsOpen) {
            mTheFooterFind.setVisibility(View.GONE);
            return;
        } else {
            if (mTheFooterFind.getVisibility() == View.GONE) {
                mTheFooterFind.setVisibility(View.VISIBLE);
            }
        }

        if (startIsOpen) {
            mStarFind.setVisibility(View.VISIBLE);
            String starName = (String) SPUtils.get(getContext(), Constant.USER_STAR, "水瓶座");
            mXzStarFind.setText("-" + starName);
            mStarFind.setOnClickListener(this);
            requestStarData(starName);
        } else {
            mStarFind.setVisibility(View.GONE);
        }

        if (jokeIsOpen) {
            mJokeFind.setVisibility(View.VISIBLE);
            mJokeFind.setOnClickListener(this);
            requestJoke();
        } else {
            mJokeFind.setVisibility(View.GONE);
        }

        if (wannianIsOpen) {
            mWannianliFind.setVisibility(View.VISIBLE);
            mWannianliFind.setOnClickListener(this);
            requestWanNianLi();
        } else {
            mWannianliFind.setVisibility(View.GONE);
        }


    }

    /**
     * 请求万年历
     */
    private void requestWanNianLi() {

        String mDate = new StringBuilder()
                .append(DateUtils.getCurrYear()).append("-")
                .append(DateUtils.getCurrMonth()).append("-")
                .append(DateUtils.getCurrDay()).toString();
        unsubscribe("chinacalendar");
        mCalendarSubscribe = NetWork.getChinaCalendarApi()
                .getChinaCalendar("3f95b5d789fbc83f5d2f6d2479850e7e", mDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);

    }

    /**
     * 请求笑话
     */
    private void requestJoke() {

        unsubscribe("joke");
        mDayJokeSubscribe = NetWork.getDayJokeApi()
                .getDayJoke("39094c8b40b831b8e7b7a19a20654ed7")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mDayJokeObserver);
    }

    /**
     * 请求星座
     * @param starName 星座名称
     */
    private void requestStarData(String starName) {

        unsubscribe("star");
        mConstellationSubscribe = NetWork.getConstellationApi()
                .getConstellation(starName, "today", "35de7ea555a8b5d58ce2d7e4f8cb7c9f")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mConstellationObserver);
    }

    /**
     * 初始化背景图片
     */
    private void initBg() {

        mBeforeFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//按钮切换点击
                if (mImages != null) {
                    setBg(--mBgFlag);
                }
            }
        });

        mNextFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//按钮切换点击
                if (mImages != null) {
                    setBg(++mBgFlag);
                }
            }
        });

        mBgTitleFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//查看图片
                if (!TextUtils.isEmpty(mNowBgUrl)) {
                    Intent intent = new Intent(getContext(), PinImageActivity.class);
                    intent.putExtra(PinImageActivity.IMG_NAME, TextUtils.isEmpty(mNowBgName) ? "" : mNowBgName);
                    intent.putExtra(PinImageActivity.IMG_URL, mNowBgUrl);
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            mBgFind,
                            "transition_pinchimageview");
                    ActivityCompat.startActivity(getContext(), intent, optionsCompat.toBundle());

                }
            }
        });

        requestBg();
    }

    /**
     * 请求图片数据
     */
    private void requestBg() {

        unsubscribe("bg");
        mFindBgSubscribtion = NetWork.getFindBgApi()
                .getFindBg("js", 0, 8)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mFindBgObserver);
    }

    private void setBg(int i) {
        if (i <= 0) {
            mBeforeFind.setEnabled(false);
            if (i == 0)
                showBg(mImages.get(0));
        } else if (i >= mImages.size() - 1) {
            mNextFind.setEnabled(false);
            if (i == mImages.size() - 1)
                showBg(mImages.get(mImages.size() - 1));
        } else {
            showBg(mImages.get(i));
            if (!mBeforeFind.isEnabled()) {
                mBeforeFind.setEnabled(true);
            }
            if (!mNextFind.isEnabled())
                mNextFind.setEnabled(true);
        }
    }

    private void showBg(FindBg.ImagesBean imagesBean) {

        mNowBgUrl = BG_BASE_URL + imagesBean.getUrl();
        Glide.with(getContext())
                .load(mNowBgUrl)
                .override(PixelUtil.getWindowWidth(), PixelUtil.getWindowHeight())
                .placeholder(R.color.colorPrimaryDark)
                .error(R.color.colorPrimaryDark)
                .into(mBgFind);
        mNowBgName = imagesBean.getCopyright();
        mBgTitleFind.setText(mNowBgName);
    }

    @Override
    public void managerArguments() {

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.joke_find:
                startActivity(new Intent(getContext(), JokeActivity.class));
                break;
            case R.id.star_find:
                startActivity(new Intent(getContext(), ConstellationActivity.class));
                break;
            case R.id.wannianli_find:
                startActivity(new Intent(getContext(), ChinaCalendarActivity.class));
                break;
            default:
                break;
        }
    }


    private void unsubscribe(String string) {

        switch (string) {
            case "bg":
                if (mFindBgSubscribtion != null && !mFindBgSubscribtion.isUnsubscribed()) {
                    mFindBgSubscribtion.unsubscribe();
                }
                break;
            case "star":
                if (mConstellationSubscribe != null && !mConstellationSubscribe.isUnsubscribed()) {
                    mConstellationSubscribe.unsubscribe();
                }
                break;
            case "chinacalendar":
                if (mCalendarSubscribe != null && !mCalendarSubscribe.isUnsubscribed()) {
                    mCalendarSubscribe.unsubscribe();
                }
                break;
            case "joke":
                if (mDayJokeSubscribe != null && !mDayJokeSubscribe.isUnsubscribed()) {
                    mDayJokeSubscribe.unsubscribe();
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnRefreshNewsFragmentEvent(RefreshFindFragmentEvent event) {
        if (event.getFlagBig() > 0) {
            initBottomContext();
        }
        if (event.getFlagSmall() > 0) {
//            mFindAdapter.setNewData(initData());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
