package com.hanshaoda.smallreader.network.api;

import com.hanshaoda.smallreader.model.QueryIDCard;
import com.hanshaoda.smallreader.model.QueryQQ;
import com.hanshaoda.smallreader.model.QueryTel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author: hanshaoda
 * created on: 2017/9/9 下午4:27
 * description:
 */

public interface QueryInfoApi {

    @GET("mobile/get")
    Observable<QueryTel> getTelInfo(@Query("key") String key, @Query("phone") String phone);

    @GET("qqevaluate/qq")
    Observable<QueryQQ> getQQInfo(@Query("key") String key, @Query("qq") String qq);

    @GET("idcard/index")
    Observable<QueryIDCard> getIDCardInfo(@Query("key") String key, @Query("cardno") String cardno);
}
