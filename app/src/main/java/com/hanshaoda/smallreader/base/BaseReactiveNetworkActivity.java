package com.hanshaoda.smallreader.base;

import android.net.NetworkInfo;

import com.github.pwittchen.reactivenetwork.library.Connectivity;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * author: hanshaoda
 * created on: 2017/9/8 下午6:07
 * description:
 */

public abstract class BaseReactiveNetworkActivity extends BaseActivity {

    private Subscription networkConnectivitySubscription;
    private Subscription internetConnectivitySubscription;

    public abstract void onConnectListener(NetworkInfo.State state);

    public abstract void onDisConnectListener(NetworkInfo.State state);


    @Override
    protected void onResume() {
        super.onResume();
        //正在连接中 的逻辑
//break;
//break;
        networkConnectivitySubscription = ReactiveNetwork.observeNetworkConnectivity(getApplicationContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Connectivity>() {
                    @Override
                    public void call(Connectivity connectivity) {
                        final NetworkInfo.State state = connectivity.getState();
                        final String name = connectivity.getName();

                        switch (name) {
                            case "NONE":
                                onDisConnectListener(state);
                                break;
                            case "WIFI":

                                break;
                            case "MOBILE":
                                break;
                            default:
                                break;
                        }
                        switch (state) {
                            case CONNECTING:
                                //正在连接中 的逻辑
                                //break;
                            case CONNECTED:
                                onConnectListener(state);
                                break;
                            case SUSPENDED:
                                break;
                            case DISCONNECTING:
                                //break;
                            case DISCONNECTED:
                                onDisConnectListener(state);
                                break;
                            case UNKNOWN:
                                break;
                            default:

                                break;
                        }
                    }
                });
        internetConnectivitySubscription = ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {

                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        safelyUnSubscribe(networkConnectivitySubscription, internetConnectivitySubscription);
    }

    private void safelyUnSubscribe(Subscription... subscriptions) {
        for (Subscription subscription : subscriptions) {
            if (subscription != null && !subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }


}
