package com.hanshaoda.smallreader.update;

import android.content.Context;
import android.util.Log;

import com.hanshaoda.smallreader.config.Constant;

/**
 * author: hanshaoda
 * created on: 2017/9/13 上午9:59
 * description:
 */

public class UpdateChecker {

    public static void checkForDialog(Context context) {
        if (context != null) {
            new CheckUpdateTask(context, Constants.TYPE_DIALOG, true).execute();
        } else {
            Log.e(Constants.TAG, "The arg context is null");
        }
    }

    public static void checkForNotification(Context context) {
        if (context != null) {
            new CheckUpdateTask(context, Constants.TYPE_NOTIFICATION, true).execute();
        } else {
            Log.e(Constants.TAG, "The arg context is null");
        }
    }
}
