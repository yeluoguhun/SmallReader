package com.hanshaoda.smallreader.modules.find.constellation;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.base.BaseFragment;
import com.hanshaoda.smallreader.model.YearConstellation;
import com.hanshaoda.smallreader.network.NetWork;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author: hanshaoda
 * created on: 2017/10/10 下午2:04
 * description:
 */
public class YearConstellationFragment extends BaseFragment {
    public static final String CONSTELLATION = "CONSTELLATION";
    public static final String PARAM2 = "PARAM2";
    @BindView(R.id.title_year_star)
    TextView mTitleYearStar;
    @BindView(R.id.luckystone_year_star)
    TextView mLuckystoneYearStar;
    @BindView(R.id.mima_info_year_star)
    TextView mMimaInfoYearStar;
    @BindView(R.id.mima_text_year_star)
    TextView mMimaTextYearStar;
    @BindView(R.id.love_year_star)
    TextView mLoveYearStar;
    @BindView(R.id.career_year_star)
    TextView mCareerYearStar;
    @BindView(R.id.finance_year_star)
    TextView mFinanceYearStar;

    private String mSelectConstellationName;
    private Subscription subscription;

    Observer<YearConstellation> mObserver = new Observer<YearConstellation>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

            Logger.e(e.getMessage());
        }

        @Override
        public void onNext(YearConstellation constellation) {

            Logger.e("得到想要的数据" + constellation.toString());
            if (constellation.getError_code() == 0) {
                setDataToView(constellation);
            }
        }
    };
    private String mParam2;

    /**
     * 填充数据
     *
     * @param constellation 数据源
     */
    private void setDataToView(YearConstellation constellation) {
        mTitleYearStar.setText(constellation.getDate() + constellation.getName() + "整体运势");
        mMimaInfoYearStar.setText(constellation.getMima().getInfo() + "");
        mMimaTextYearStar.setText(constellation.getMima().getText() + "");
        mLoveYearStar.setText(constellation.getLove().get(0) + "");
        mCareerYearStar.setText(constellation.getCareer().get(0) + "");
        mFinanceYearStar.setText(constellation.getFinance().get(0) + "");

        mLuckystoneYearStar.setText("幸运石" + constellation.getLuckeyStone());
    }

    public static YearConstellationFragment newInstance(String param1, String param2) {
        YearConstellationFragment fragment = new YearConstellationFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CONSTELLATION, param1);
        bundle.putString(PARAM2, param2);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_year_constellation;
    }

    @Override
    public void initView() {

        EventBus.getDefault().register(this);
        requestData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshConstellationEvent(RefreshConstellationEvent event) {
        mSelectConstellationName = event.getConstellation();
        requestData();
    }

    private void requestData() {

        unSubscribe();
        subscription = NetWork.getYearConstellationsApi()
                .getYearConstellation(TextUtils.isEmpty(mSelectConstellationName) ? "" : mSelectConstellationName,
                        "year", "35de7ea555a8b5d58ce2d7e4f8cb7c9f")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }

    private void unSubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void managerArguments() {

        mSelectConstellationName = getArguments().getString(CONSTELLATION);
        mParam2 = getArguments().getString(PARAM2);
    }
}
