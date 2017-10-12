package com.hanshaoda.smallreader.modules.find.constellation;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.base.BaseFragment;
import com.hanshaoda.smallreader.model.BaseConstellation;
import com.hanshaoda.smallreader.model.MonthConstellation;
import com.hanshaoda.smallreader.model.WeekConstellation;
import com.hanshaoda.smallreader.network.NetWork;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author: hanshaoda
 * created on: 2017/10/10 下午1:53
 * description:
 */
public class WeekAndMonthConstellationFragment extends BaseFragment {

    public static final String WEEKORMONTH = "WEEKORMONTH";
    public static final String CONSTELLATION = "CONSTELLATION";
    public static final String WEEK = "WEEK";
    public static final String NEXT_WEEK = "NEXT_WEEK";
    public static final String MONTH = "MONTH";
    @BindView(R.id.date_week)
    TextView mDateWeek;
    @BindView(R.id.allorwork_name_week)
    TextView mAllorworkNameWeek;
    @BindView(R.id.allorwork_text_week)
    TextView mAllorworkTextWeek;
    @BindView(R.id.health_text_week)
    TextView mHealthTextWeek;
    @BindView(R.id.health_name_week)
    TextView mHealthNameWeek;
    @BindView(R.id.love_name_week)
    TextView mLoveNameWeek;
    @BindView(R.id.love_text_week)
    TextView mLoveTextWeek;
    @BindView(R.id.money_text_week)
    TextView mMoneyTextWeek;
    @BindView(R.id.money_name_week)
    TextView mMoneyNameWeek;
    @BindView(R.id.job_name_week)
    TextView mJobNameWeek;
    @BindView(R.id.job_text_week)
    TextView mJobTextWeek;
    private String mConstellation;

    private String mWeekOrMonth;
    private Subscription mSubscription;
    //请求数据
    Observer<BaseConstellation> mObserver = new Observer<BaseConstellation>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

            Logger.e(e.getMessage());
        }

        @Override
        public void onNext(BaseConstellation baseConstellation) {

            Logger.e("得到了想要的数据！！！"+baseConstellation.toString());
            if (baseConstellation.getError_code() == 0) {
                setDataToView(baseConstellation);
            }
        }
    };

    private void setDataToView(BaseConstellation baseConstellation) {
        String allOrwork = null;
        String allOrworkText = null;
        String healthText = baseConstellation.getHealth();
        String jobText = baseConstellation.getWork();
        String loveText = baseConstellation.getLove();
        String moneyText = baseConstellation.getMoney();
        String date = baseConstellation.getDate();
        if (mWeekOrMonth.equals(WEEK) || mWeekOrMonth.equals(NEXT_WEEK)) {
            WeekConstellation weekConstellation = (WeekConstellation) baseConstellation;
            allOrwork = "求职";
            allOrworkText = weekConstellation.getJob();
        } else if (mWeekOrMonth.equals(MONTH)) {
            MonthConstellation monthConstellation = (MonthConstellation) baseConstellation;
            allOrwork = "全部";
            allOrworkText = monthConstellation.getAll();
        }
        mAllorworkNameWeek.setText(allOrwork);
        mAllorworkTextWeek.setText(allOrworkText);
        mHealthTextWeek.setText(healthText);
        mJobTextWeek.setText(jobText);
        mLoveTextWeek.setText(loveText);
        mMoneyTextWeek.setText(moneyText);
        mDateWeek.setText(date);
    }

    public static WeekAndMonthConstellationFragment newInstance(String flag, String constellation) {
        WeekAndMonthConstellationFragment fragment = new WeekAndMonthConstellationFragment();
        Bundle bundle = new Bundle();
        bundle.putString(WEEKORMONTH, flag);
        bundle.putString(CONSTELLATION, constellation);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_week_month_layout;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        requestData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshConstellationEvent(RefreshConstellationEvent event) {
        mConstellation = event.getConstellation();
        requestData();
    }

    private void requestData() {
        unSubscribe();
        if (mWeekOrMonth.equals(WEEK) || mWeekOrMonth.equals(NEXT_WEEK)) {
            mSubscription = NetWork.getWeekConstellationsApi()
                    .getWeekConstellation(TextUtils.isEmpty(mConstellation) ? "水瓶座" : mConstellation,
                            mWeekOrMonth, "35de7ea555a8b5d58ce2d7e4f8cb7c9f")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mObserver);
        } else if (mWeekOrMonth.equals(MONTH)) {
            mSubscription = NetWork.getMonthConstellationsApi()
                    .getMonthConstellation(TextUtils.isEmpty(mConstellation) ? "水瓶座" : mConstellation,
                            mWeekOrMonth, "35de7ea555a8b5d58ce2d7e4f8cb7c9f")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mObserver);
        }

    }

    private void unSubscribe() {

        if (mSubscription != null && mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void managerArguments() {
        mWeekOrMonth = getArguments().getString(WEEKORMONTH);
        mConstellation = getArguments().getString(CONSTELLATION);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
