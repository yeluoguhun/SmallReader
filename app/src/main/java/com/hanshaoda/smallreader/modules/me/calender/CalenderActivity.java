package com.hanshaoda.smallreader.modules.me.calender;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.base.BaseCommonActivity;
import com.hanshaoda.smallreader.model.HolidaysManager;
import com.hanshaoda.smallreader.utils.DateUtils;
import com.hanshaoda.smallreader.utils.Lunar;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: hanshaoda
 * created on: 2017/9/14 下午5:21
 * description:
 */
public class CalenderActivity extends BaseCommonActivity implements OnDateSelectedListener, OnMonthChangedListener {

    private static String[] mHolidayNameArray = {"元旦", "春节", "清明节", "劳动节", "端午节", "中秋国庆"};
    private static String[] mHolidayInfoArray = {"1月1日放假，1月2日（星期一）补休。",
            "1月27日至2月2日放假调休，共7天。1月22日（星期日）、2月4日（星期六）上班。",
            "4月2日至4日放假调休，共3天。4月1日（星期六）上班。",
            "5月1日放假，与周末连休。",
            "5月28日至30日放假调休，共3天。5月27日（星期六）上班。",
            "10月1日至8日放假调休，共8天。9月30日（星期六）上班。"};
    private static int[][] mHolidayDate = {{1, 1}, {1, 27}, {4, 2}, {5, 1}, {5, 28}, {10, 1}};
    @BindView(R.id.toolbar_calendar)
    Toolbar mToolbarCalendar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.calendarView_calendar)
    MaterialCalendarView mCalendarViewCalendar;
    @BindView(R.id.distance_today_calendar)
    TextView mDistanceTodayCalendar;
    @BindView(R.id.beforeOrBack_calendar)
    TextView mBeforeOrBackCalendar;
    @BindView(R.id.nongli_date_calendar)
    TextView mNongliDateCalendar;
    @BindView(R.id.holiday_date_calendar)
    TextView mHolidayDateCalendar;
    @BindView(R.id.cyclical_calendar)
    TextView mCyclicalCalendar;
    @BindView(R.id.animals_year_calendar)
    TextView mAnimalsYearCalendar;
    @BindView(R.id.back_today_calendar)
    TextView mBackTodayCalendar;
    @BindView(R.id.month_view_calendar)
    TextView mMonthViewCalendar;
    @BindView(R.id.week_view_calendar)
    TextView mWeekViewCalendar;
    private CalendarDay today;
    private HolidaysManager holidaysManager;
    private String year;
    private String month;
    private LunarDecorator lunarDecorator;
    private Lunar mLunar;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_calender);
    }

    @Override
    public void initView() {

        today = CalendarDay.today();
        holidaysManager = new HolidaysManager();
        initToolbar();
        initTodayInfo(today.getDate(), today.getDate());

        year = DateUtils.date2String(today.getDate(), "yyyy");
        month = DateUtils.date2String(today.getDate(), "MM");

        lunarDecorator = new LunarDecorator(year, month);
        mCalendarViewCalendar.setCurrentDate(today);
        mCalendarViewCalendar.setShowOtherDates(MaterialCalendarView.SHOW_DEFAULTS);
//        点击头尾跳转到其他月份
        mCalendarViewCalendar.setAllowClickDaysOutsideCurrentMonth(true);
        mCalendarViewCalendar.setOnDateChangedListener(this);
        mCalendarViewCalendar.setOnMonthChangedListener(this);

        mCalendarViewCalendar.addDecorators(new TodayDecorator(),
                lunarDecorator,
                new HighlightWeekendsDecorator(),
                new EventDecorator_Holiday(holidaysManager.getHolidays()),
                new EventDecorator_Workday(holidaysManager.getHolidays()));
    }

    private void initTodayInfo(Date date, Date date1) {

        String holidayName = holidaysManager.containsDate(HolidaysManager.formatDate(date1));
        mHolidayDateCalendar.setText(TextUtils.isEmpty(holidayName) ? "" : holidayName);
        mLunar = new Lunar(date1);
        mAnimalsYearCalendar.setText(mLunar.animalsYear() + "年");
        mNongliDateCalendar.setText(mLunar.toString());
        mCyclicalCalendar.setText(mLunar.cyclical());
        int distanceDay = (int) ((date1.getTime() - date.getTime()) / 86400000L);
        String distanceDayStr = null;
        if (distanceDay == 0) {
            mBeforeOrBackCalendar.setText("\b\b");
            distanceDayStr = "今";
        } else if (distanceDay > 0) {
            mBeforeOrBackCalendar.setText("后");
            distanceDayStr = String.valueOf(distanceDay);
        } else if (distanceDay < 0) {
            mBeforeOrBackCalendar.setText("前");
            distanceDayStr = String.valueOf(distanceDay);
        }
        mDistanceTodayCalendar.setText(distanceDayStr);

    }

    private void initToolbar() {
        setSupportActionBar(mToolbarCalendar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

        initTodayInfo(today.getDate(), date.getDate());
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

        lunarDecorator.setYear(DateUtils.date2String(date.getDate(), "yyyy"));
        lunarDecorator.setMonth(DateUtils.date2String(date.getDate(), "MM"));
        widget.invalidateDecorators();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.year_view:
                break;
            case R.id.skip_to_day:
                skipToDay();
                break;
            case R.id.holidays:
                showHolidays();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showHolidays() {
        View view = LayoutInflater.from(this).inflate(R.layout.holiday_list, null);
        RecyclerView recyclerView = view.findViewById(R.id.holidays_recycler);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        HolidayAdapter holidayAdapter = new HolidayAdapter(this, mHolidayNameArray, mHolidayInfoArray);
        recyclerView.setAdapter(holidayAdapter);
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        dialog.show();
        holidayAdapter.setmItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, String text) {
                mCalendarViewCalendar.setCurrentDate(CalendarDay.from(2017,
                        mHolidayDate[position][0] - 1, mHolidayDate[position][1]));
                mCalendarViewCalendar.setSelectedDate(CalendarDay.from(2017,
                        mHolidayDate[position][0] - 1, mHolidayDate[position][1]));
                dialog.dismiss();
            }
        });

    }

    private void skipToDay() {
        Calendar calendar = today.getCalendar();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, null, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                Calendar.DAY_OF_MONTH);
//        设置
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatePicker datePicker = datePickerDialog.getDatePicker();
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int dayOfMonth = datePicker.getDayOfMonth();

                CalendarDay from = CalendarDay.from(year, month, dayOfMonth);
                mCalendarViewCalendar.setCurrentDate(from);
                mCalendarViewCalendar.setSelectedDate(from);
            }
        });

//        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
        datePickerDialog.dismiss();

    }


    @OnClick({R.id.back_today_calendar, R.id.month_view_calendar, R.id.week_view_calendar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_today_calendar:
                mCalendarViewCalendar.setSelectedDate(today);
                break;
            case R.id.month_view_calendar:
                mCalendarViewCalendar.state().edit()
                        .setCalendarDisplayMode(CalendarMode.MONTHS)
                        .commit();
                break;
            case R.id.week_view_calendar:
                mCalendarViewCalendar.state().edit()
                        .setCalendarDisplayMode(CalendarMode.WEEKS)
                        .commit();
                break;
        }
    }
}
