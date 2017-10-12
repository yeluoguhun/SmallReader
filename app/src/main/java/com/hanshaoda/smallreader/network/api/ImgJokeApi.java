package com.hanshaoda.smallreader.network.api;

import com.hanshaoda.smallreader.model.NewTextJokeBean;
import com.hanshaoda.smallreader.model.RandomTextJoke;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author: hanshaoda
 * created on: 2017/9/9 下午3:51
 * description:
 */

public interface ImgJokeApi {
    @GET("joke/img/text.from")
    Observable<NewTextJokeBean> getNewTextJoke(@Query("key") String appkey,
                                                   @Query("page") int pno,
                                                   @Query("pagesize") int ps);

    @GET("joke/randJoke.php")
    Observable<RandomTextJoke> getRandomTextJoke(@Query("key") String key,@Query("type") String type);
}
