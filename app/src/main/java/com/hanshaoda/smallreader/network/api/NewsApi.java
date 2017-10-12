package com.hanshaoda.smallreader.network.api;

import com.hanshaoda.smallreader.model.NewsItem;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author: hanshaoda
 * created on: 2017/9/9 下午4:22
 * description:
 */

public interface NewsApi {

    @GET("toutiao/index")
    Observable<NewsItem> getNews(@Query("type") String type, @Query("key") String key);
}
