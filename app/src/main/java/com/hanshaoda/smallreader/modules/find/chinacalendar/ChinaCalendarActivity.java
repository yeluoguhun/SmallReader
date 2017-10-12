package com.hanshaoda.smallreader.modules.find.chinacalendar;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.base.BaseActivity;
import com.hanshaoda.smallreader.base.BaseCommonActivity;
import com.hanshaoda.smallreader.model.ChinaCalendar;
import com.hanshaoda.smallreader.network.NetWork;
import com.hanshaoda.smallreader.utils.SPUtils;
import com.hanshaoda.smallreader.utils.webviewutils.ObjectSaveManager;
import com.robertlevonyan.views.chip.Chip;

import java.util.Calendar;

import butterknife.BindView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChinaCalendarActivity extends BaseCommonActivity {

    public static final String CHINA_CALENDAR = "chinacalendar";
    @BindView(R.id.lunar1_ccheader)
    TextView mLunar1Ccheader;
    @BindView(R.id.date1_ccheader)
    TextView mDate1Ccheader;
    @BindView(R.id.chinayear1_ccheader)
    TextView mChinayear1Ccheader;
    @BindView(R.id.week1_ccheader)
    TextView mWeek1Ccheader;
    @BindView(R.id.lunar2_ccheader)
    TextView mLunar2Ccheader;
    @BindView(R.id.chinayear2_ccheader)
    TextView mChinayear2Ccheader;
    @BindView(R.id.date2_ccheader)
    TextView mDate2Ccheader;
    @BindView(R.id.animals1_ccheader)
    TextView mAnimals1Ccheader;
    @BindView(R.id.animals2_ccheader)
    TextView mAnimals2Ccheader;
    @BindView(R.id.week2_ccheader)
    TextView mWeek2Ccheader;
    @BindView(R.id.title_china_calendar)
    TextView mTitleChinaCalendar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.chip)
    Chip mChip;
    @BindView(R.id.flexboxlayout_china_calendar)
    FlexboxLayout mFlexboxlayoutChinaCalendar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.activity_china_calendar)
    CoordinatorLayout mActivityChinaCalendar;
    private ObjectSaveManager objectSaveManager;
    private String mDate;
    private Subscription mSubscription;

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
                saveObject(chinaCalendar.getResult().getData());
                initDateView(chinaCalendar.getResult().getData());
            } else {
                Toast.makeText(ChinaCalendarActivity.this, "请求数据失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void saveObject(ChinaCalendar.ResultBean.DataBean data) {
        SPUtils.put(this, CHINA_CALENDAR, data.getDate());
        objectSaveManager.saveObject(CHINA_CALENDAR + mDate, data);
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_china_calendar);
    }

    @Override
    public void initView() {

        objectSaveManager = ObjectSaveManager.getInstance(this.getApplicationContext());

        initToolbar();
        initFab();
    }

    private void initFab() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipToDay();
            }
        });
    }

    private void skipToDay() {
        Calendar instance = Calendar.getInstance();
        final DatePickerDialog mDialog = new DatePickerDialog(this, null, instance.get(Calendar.YEAR),
                instance.get(Calendar.MONTH), instance.get(Calendar.DAY_OF_MONTH));
        mDialog.setButton(DialogInterface.BUTTON_POSITIVE, "完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatePicker datePicker = mDialog.getDatePicker();
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();

                String skipDate = new StringBuffer()
                        .append(year).append("-")
                        .append(month).append("-")
                        .append(day)
                        .toString();
                mDate = skipDate;
                ChinaCalendar.ResultBean.DataBean data = (ChinaCalendar.ResultBean.DataBean)
                        objectSaveManager.getObject(CHINA_CALENDAR + skipDate);

                if (data == null) {
                    requestData();
                } else {
                    initDateView(data);
                }

            }
        });

        mDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        mDialog.show();
    }

    private void initDateView(ChinaCalendar.ResultBean.DataBean data) {

        mTitleChinaCalendar.setText(data.getDate() + "");

        String avoid = getStringFormat(data.getAvoid());
        String animalsYear = getStringFormat(data.getAnimalsYear());
        String weekday = getStringFormat(data.getWeekday());
        String suit = getStringFormat(data.getSuit());
        String lunarYear = getStringFormat(data.getLunarYear());
        String lunar = getStringFormat(data.getLunar());
        String date = getStringFormat(data.getDate());

        mAnimals1Ccheader.setText(animalsYear + "年");
        mAnimals2Ccheader.setText(animalsYear + "年");
        mWeek1Ccheader.setText(weekday);
        mWeek2Ccheader.setText(weekday);
        mChinayear1Ccheader.setText(lunarYear);
        mChinayear2Ccheader.setText(lunarYear);
        mLunar1Ccheader.setText(lunar);
        mLunar2Ccheader.setText(lunar);
        mDate1Ccheader.setText(date);
        mDate2Ccheader.setText(date);

        String[] avoidArr = avoid.split("\\.", 0);
        String[] suitArr = suit.split("\\.", 0);

        if (mFlexboxlayoutChinaCalendar.getChildCount() != 0) {
            mFlexboxlayoutChinaCalendar.removeAllViewsInLayout();
        }

        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        int flag = 0;

        int[] intArray = getResources().getIntArray(R.array.itemcolor);

        for (int i = 0; i < avoidArr.length; i++) {
            final Chip avoidChip = new Chip(this);
            avoidChip.setHasIcon(true);
            avoidChip.setTextColor(Color.WHITE);
            avoidChip.changeBackgroundColor(intArray[flag % intArray.length]);
            avoidChip.setChipText(avoidArr[i]);
            avoidChip.setChipIcon(getResources().getDrawable(R.mipmap.ji));

            Handler avoidHandler = new Handler();
            avoidHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    avoidChip.requestLayout();
                }
            }, 250);
            mFlexboxlayoutChinaCalendar.addView(avoidChip, flag++, layoutParams);
        }

        for (int i = 0; i < suitArr.length; i++) {
            final Chip suitChip = new Chip(this);
            suitChip.setHasIcon(true);
            suitChip.setTextColor(Color.WHITE);
            suitChip.changeBackgroundColor(intArray[flag % intArray.length]);

            suitChip.setChipText(suitArr[i]);
            suitChip.setChipIcon(getResources().getDrawable(R.mipmap.yi));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    suitChip.requestLayout();
                }
            }, 250);
            mFlexboxlayoutChinaCalendar.addView(suitChip, flag++, layoutParams);
        }

        mFlexboxlayoutChinaCalendar.requestLayout();
        mFlexboxlayoutChinaCalendar.invalidate();
    }

    private String getStringFormat(String string) {

        return TextUtils.isEmpty(string) ? "" : string;
    }

    private void requestData() {

        unSubscribe();
        mSubscription = NetWork.getChinaCalendaraPI()
                .getChinaCalendar("", mDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }

    private void unSubscribe() {

        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initPresenter() {

    }
}
