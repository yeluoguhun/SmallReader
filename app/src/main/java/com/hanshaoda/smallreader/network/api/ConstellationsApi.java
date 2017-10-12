package com.hanshaoda.smallreader.network.api;

import com.hanshaoda.smallreader.model.DayConstellation;
import com.hanshaoda.smallreader.model.MonthConstellation;
import com.hanshaoda.smallreader.model.WeekConstellation;
import com.hanshaoda.smallreader.model.YearConstellation;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author: hanshaoda
 * created on: 2017/9/9 下午3:29
 * description:
 */

public interface ConstellationsApi {
    @GET("constellation/getAll")
    Observable<DayConstellation> getDayConstellation(
            @Query("consName") String consName,
            @Query("type") String type,
            @Query("key") String key);

    @GET("constellation/getAll")
    Observable<WeekConstellation> getWeekConstellation(
            @Query("consName") String consName,
            @Query("type") String type,
            @Query("key") String key);

    @GET("constellation/getAll")
    Observable<MonthConstellation> getMonthConstellation(
            @Query("consName") String consName,
            @Query("type") String type,
            @Query("key") String key);

    @GET("constellation/getAll")
    Observable<YearConstellation> getYearConstellation(
            @Query("consName") String consName,
            @Query("type") String type,
            @Query("key") String key);
}
