package com.hanshaoda.smallreader.network.api;

import com.hanshaoda.smallreader.model.FindBg;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author: hanshaoda
 * created on: 2017/9/9 下午3:43
 * description:
 */

public interface FindBgApi {

    @GET("HPImageArchive.aspx")
    Observable<FindBg> getFindBg(
            @Query("format") String format, @Query("idx") int idx, @Query("n") int n
    );
}
