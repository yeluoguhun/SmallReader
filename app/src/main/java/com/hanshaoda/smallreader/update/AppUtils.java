package com.hanshaoda.smallreader.update;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * author: hanshaoda
 * created on: 2017/9/12 下午4:44
 * description:
 */
public class AppUtils {

    public static int getVersionCode(Context context) {
        if (context != null) {
            try {
                return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static String getVersionName(Context context) {
        if (context != null) {
            try {
                return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
