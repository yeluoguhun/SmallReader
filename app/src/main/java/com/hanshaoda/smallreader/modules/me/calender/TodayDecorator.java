package com.hanshaoda.smallreader.modules.me.calender;

import com.hanshaoda.smallreader.utils.DateUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Date;


public class TodayDecorator implements DayViewDecorator {
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        Date date = new Date();
        String dateStr = DateUtils.date2String(date, "yyyy-MM-dd");
        Date parse = DateUtils.string2Date(dateStr, "yyyy-MM-dd");
        if (day.getDate().equals(parse)) {
            return true;
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new AnnulusSpan());
    }
}
