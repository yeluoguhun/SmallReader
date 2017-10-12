package com.hanshaoda.smallreader.network.api;

import com.hanshaoda.smallreader.model.DayJoke;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author: hanshaoda
 * created on: 2017/9/9 下午3:15
 * description:
 */

public interface DayJokeApi {

    @GET("joke/content/text.from")
    Observable<DayJoke> getDayJoke(@Query("key") String key);
}
