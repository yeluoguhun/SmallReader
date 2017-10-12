package com.hanshaoda.smallreader.network.api;

import com.hanshaoda.smallreader.model.GankBeautyResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * author: hanshaoda
 * created on: 2017/9/9 下午3:20
 * description:
 */

public interface DemoApi {

    @GET("data/福利/{number}/{page}")
    Observable<GankBeautyResult> getBeauties(@Path("number") int number, @Path("page") int page);
}
