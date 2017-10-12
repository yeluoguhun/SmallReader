package com.hanshaoda.smallreader.network.api;

import com.hanshaoda.smallreader.model.Constellation;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author: hanshaoda
 * created on: 2017/9/9 下午3:27
 * description:
 */

public interface ConstellationApi {

    @GET("constellation/getAll")
    Observable<Constellation> getConstellation(
            @Query("consName") String consName, @Query("type") String type, @Query("key") String key
    );
}
