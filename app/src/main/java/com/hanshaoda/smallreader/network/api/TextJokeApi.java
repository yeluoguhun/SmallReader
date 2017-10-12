package com.hanshaoda.smallreader.network.api;

import com.hanshaoda.smallreader.model.NewTextJokeBean;
import com.hanshaoda.smallreader.model.RandomTextJoke;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author: hanshaoda
 * created on: 2017/9/9 下午4:42
 * description:
 */

public interface TextJokeApi {
    @GET("joke/content/text.from")
    Observable<NewTextJokeBean> getNewTextJoke(
            @Query("key") String key,
            @Query("page") int page,
            @Query("pagesize") int pagesize
    );

    @GET("joke/randJoke.php")
    Observable<RandomTextJoke> getRandomTextJoke(
            @Query("key") String key
    );
}
