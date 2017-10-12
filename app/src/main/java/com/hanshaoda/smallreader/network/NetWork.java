package com.hanshaoda.smallreader.network;

import com.hanshaoda.smallreader.network.api.ChinaCalendarApi;
import com.hanshaoda.smallreader.network.api.CityApi;
import com.hanshaoda.smallreader.network.api.ConstellationApi;
import com.hanshaoda.smallreader.network.api.ConstellationsApi;
import com.hanshaoda.smallreader.network.api.DayJokeApi;
import com.hanshaoda.smallreader.network.api.DemoApi;
import com.hanshaoda.smallreader.network.api.FindBgApi;
import com.hanshaoda.smallreader.network.api.ImgJokeApi;
import com.hanshaoda.smallreader.network.api.NewsApi;
import com.hanshaoda.smallreader.network.api.QueryInfoApi;
import com.hanshaoda.smallreader.network.api.TextJokeApi;
import com.hanshaoda.smallreader.network.api.WechatApi;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author: hanshaoda
 * created on: 2017/9/9 下午3:03
 * description:
 */

public class NetWork {

    public static final String ROOT_URL = "http://v.juhe.cn/";

    private static DemoApi mDemoApi;
    private static NewsApi mNewsApi;
    private static WechatApi mWechatApi;
    private static FindBgApi mFindBgApi;
    private static DayJokeApi mDayJokeApi;
    private static ConstellationApi mConstellationApi;
    private static TextJokeApi mTextJokeApi;
    private static TextJokeApi mRandomTextJokeApi;
    private static ImgJokeApi mRandomImgJokeApi;
    private static ImgJokeApi sNewImgJokeApi;

    private static QueryInfoApi mQueryTelApi;
    private static QueryInfoApi mQueryQQApi;
    private static QueryInfoApi mQueryIDCardApi;

    public static ConstellationsApi mDayConstellationsApi;
    public static ConstellationsApi mWeekConstellationsApi;
    public static ConstellationsApi mMonthConstellationsApi;
    public static ConstellationsApi mYearConstellationsApi;
    public static ChinaCalendarApi mChinaCalendarApi;
    public static CityApi mCityApi;


    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();


    public static DemoApi getGankApi() {
        if (mDemoApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://gank.io/api/")
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(gsonConverterFactory)
                    .build();
            mDemoApi = retrofit.create(DemoApi.class);
        }
        return mDemoApi;
    }

    public static NewsApi getNewsApi() {
        if (mNewsApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mNewsApi = retrofit.create(NewsApi.class);
        }
        return mNewsApi;
    }

    public static WechatApi getWechatApi() {
        if (mWechatApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mWechatApi = retrofit.create(WechatApi.class);
        }
        return mWechatApi;
    }


    public static FindBgApi getFindBgApi() {
        if (mFindBgApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://www.bing.com/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mFindBgApi = retrofit.create(FindBgApi.class);
        }
        return mFindBgApi;
    }

    public static DayJokeApi getDayJokeApi() {
        if (mDayJokeApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://japi.juhe.cn/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mDayJokeApi = retrofit.create(DayJokeApi.class);
        }
        return mDayJokeApi;
    }

    public static ConstellationApi getConstellationApi() {
        if (mConstellationApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://web.juhe.cn:8080/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mConstellationApi = retrofit.create(ConstellationApi.class);
        }
        return mConstellationApi;
    }

    public static TextJokeApi getRandomTextJokeApi() {
        if (mRandomTextJokeApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://v.juhe.cn/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mRandomTextJokeApi = retrofit.create(TextJokeApi.class);

        }
        return mRandomTextJokeApi;
    }

    public static TextJokeApi getNewTextJokeApi() {
        if (mTextJokeApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://japi.juhe.cn/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mTextJokeApi = retrofit.create(TextJokeApi.class);
        }
        return mTextJokeApi;
    }


    public static ImgJokeApi getRandomImgJokeApi() {
        if (mRandomImgJokeApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://v.juhe.cn/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();

            mRandomImgJokeApi = retrofit.create(ImgJokeApi.class);
        }
        return mRandomImgJokeApi;
    }

    public static ImgJokeApi getNewImgJokeApi() {
        if (sNewImgJokeApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://japi.juhe.cn/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            sNewImgJokeApi = retrofit.create(ImgJokeApi.class);
        }
        return sNewImgJokeApi;

    }

    public static QueryInfoApi getQueryIDCardApi() {
        if (mQueryIDCardApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://apis.juhe.cn/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mQueryIDCardApi = retrofit.create(QueryInfoApi.class);
        }
        return mQueryIDCardApi;

    }

    public static QueryInfoApi getQuerQQApi() {
        if (mQueryQQApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://japi.juhe.cn/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mQueryQQApi = retrofit.create(QueryInfoApi.class);
        }
        return mQueryQQApi;
    }

    public static QueryInfoApi getQueryTelApi() {
        if (mQueryTelApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://apis.juhe.cn/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mQueryTelApi = retrofit.create(QueryInfoApi.class);
        }
        return mQueryTelApi;
    }


    public static ConstellationsApi getDayConstellationsApi() {
        if (mDayConstellationsApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://web.juhe.cn:8080/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mDayConstellationsApi = retrofit.create(ConstellationsApi.class);
        }
        return mDayConstellationsApi;
    }

    public static ConstellationsApi getWeekConstellationsApi() {
        if (mWeekConstellationsApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://web.juhe.cn:8080/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mWeekConstellationsApi = retrofit.create(ConstellationsApi.class);
        }
        return mWeekConstellationsApi;
    }

    public static ConstellationsApi getMonthConstellationsApi() {
        if (mMonthConstellationsApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://web.juhe.cn:8080/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mMonthConstellationsApi = retrofit.create(ConstellationsApi.class);
        }
        return mMonthConstellationsApi;
    }

    public static ConstellationsApi getYearConstellationsApi() {
        if (mYearConstellationsApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://web.juhe.cn:8080/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mYearConstellationsApi = retrofit.create(ConstellationsApi.class);
        }
        return mYearConstellationsApi;
    }

    public static ChinaCalendarApi getChinaCalendarApi() {
        if (mChinaCalendarApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://v.juhe.cn/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mChinaCalendarApi = retrofit.create(ChinaCalendarApi.class);
        }
        return mChinaCalendarApi;
    }

    public static CityApi getCityApi() {
        if (mCityApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://api.heweather.com/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mCityApi = retrofit.create(CityApi.class);
        }
        return mCityApi;
    }
}
