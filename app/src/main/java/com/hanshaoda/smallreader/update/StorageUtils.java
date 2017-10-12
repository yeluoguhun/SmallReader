package com.hanshaoda.smallreader.update;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * author: hanshaoda
 * created on: 2017/9/12 下午6:25
 * description:
 */

public class StorageUtils {

    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

    private StorageUtils() {
    }

    public static File getCacheDirectory(Context context) {
        File appCacheDir = null;

        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
            appCacheDir = getExternalCacheDir(context);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        if (appCacheDir == null) {
            Log.w(Constants.TAG, "Can't define system cache directory !The app should be re-installed");
        }
        return appCacheDir;
    }

    private static File getExternalCacheDir(Context context) {

        File file = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File cache = new File(new File(file, context.getPackageName()), "cache");
        if (!cache.exists()) {
            if (!cache.mkdirs()) {
                return null;
            }
            try {
                new File(cache, "nomedia").createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cache;
    }

    private static boolean hasExternalStoragePermission(Context context) {
        int i = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return i == PackageManager.PERMISSION_GRANTED;
    }

}
