package com.hanshaoda.smallreader.app;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.hanshaoda.smallreader.modules.main.MainActivity;
import com.hanshaoda.smallreader.config.Constant;
import com.hanshaoda.smallreader.utils.SPUtils;
import com.orhanobut.logger.Logger;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;
import java.util.Locale;

/**
 * author: hanshaoda
 * created on: 2017/9/7 上午11:36
 * description:
 */
public class BaseApplication extends Application {

    public static final boolean USE_SAMPLE_DATA = false;
    public static final String APP_ID = "2882303761517567158";
    public static final String APP_KEY = "5951756743158";
    public static final String TAG = "com.hanshaoda.smallreader.app";

    private static DemoHandler sHandler = null;
    private static MainActivity sMainActivity = null;

    public static BaseApplication mInstance;
    public static int screenWidth;
    public static int screenHeight;
    public static float screenDensity;

    public static Context getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initScreenSize();

        if (shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, APP_KEY);
        }

        Logger.init("HSD_SMALLER");

        //打开小米推送的Log
        LoggerInterface newLogger = new LoggerInterface() {
            @Override
            public void setTag(String tag) {
                // ignore
            }

            @SuppressLint("LongLogTag")
            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        com.xiaomi.mipush.sdk.Logger.setLogger(this, newLogger);
        if (sHandler == null) {
            sHandler = new DemoHandler(getApplicationContext());
        }
    }

    private boolean shouldInit() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processes = manager.getRunningAppProcesses();
        String packageName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processes) {
            if (info.pid == myPid && packageName.equals(info.processName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 初始化当前屏幕设备的宽高
     */
    private void initScreenSize() {

        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        screenDensity = displayMetrics.density;
    }

    /**
     * 获取应用版本号
     */
    public static String getVersion() {
        try {
            PackageManager packageManager = mInstance.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(mInstance.getPackageName(), 0);
            String versionName = packageInfo.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取当前系统语言
     *
     * @return 系统当前语言
     */
    public static String getLanguage() {
        Locale locale = mInstance.getResources().getConfiguration().locale;
        String language = locale.getDefault().toString();
        return language;
    }

    public static DemoHandler getHandler() {
        return sHandler;
    }

    public static void setMainActivity(MainActivity activity) {
        sMainActivity = activity;
    }

    public static class DemoHandler extends Handler {

        private Context context;

        public DemoHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(Message msg) {
            String s = (String) msg.obj;
            if ((!TextUtils.isEmpty(s)) && s.indexOf("新版本") == 0) {
                //通过小米推送的穿透推送，来推送新版本更新的通知
                //通知的格式：
                //新版本：2
                String[] split = s.split(":");
                try {
                    String new_version = split[1];
                    int integer = Integer.valueOf(new_version);
                    if (integer < 100) {
                        SPUtils.put(context, Constant.SMALLER_NEW_VERSION, integer);
                    } else {
                        switch (integer) {
                            case 101://魔法
                                SPUtils.put(context, Constant.OPENNEWS, "magicopen");
                                break;
                            default:

                                break;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    Log.e("Qbox_version", e.getMessage());
                } catch (Exception e) {
                    Log.e("Qbox_Version", e.getMessage());
                }
            }
            if (sMainActivity != null) {
                sMainActivity.refreshLogInfo();
            }
            if (!TextUtils.isEmpty(s)) {
//                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
