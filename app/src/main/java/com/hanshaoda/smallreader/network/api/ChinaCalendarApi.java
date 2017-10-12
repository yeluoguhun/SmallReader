package com.hanshaoda.smallreader.network.api;

import com.hanshaoda.smallreader.model.ChinaCalendar;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author: hanshaoda
 * created on: 2017/9/9 下午3:25
 * description:
 */

public interface ChinaCalendarApi {

    @GET("calendar/day")
    Observable<ChinaCalendar> getChinaCalendar(@Query("key") String key, @Query("date") String date);
}
