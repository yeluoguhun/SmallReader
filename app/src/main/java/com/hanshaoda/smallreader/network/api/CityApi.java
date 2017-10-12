package com.hanshaoda.smallreader.network.api;

import com.hanshaoda.smallreader.model.City;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author: hanshaoda
 * created on: 2017/9/9 下午3:03
 * description:
 */

public interface CityApi {

    @GET("v5/search")
    Observable<City> getCity(@Query("key") String key, @Query("city") String city);
}
