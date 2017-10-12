package com.hanshaoda.smallreader.network.api;

import com.hanshaoda.smallreader.model.WechatItem;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author: hanshaoda
 * created on: 2017/9/9 下午4:45
 * description:
 */

public interface WechatApi {

    @GET("weixin/query")
    Observable<WechatItem> getWechat(
            @Query("key") String key,
            @Query("pno") int pno,
            @Query("ps") int ps
    );
}
