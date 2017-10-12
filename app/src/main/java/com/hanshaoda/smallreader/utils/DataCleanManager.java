package com.hanshaoda.smallreader.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * author: hanshaoda
 * created on: 2017/9/7 下午3:57
 * description:
 */

public class DataCleanManager {

    /**
     * 清楚本应用内部缓存
     *
     * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * 清楚本应用所有的数据库
     *
     * @param context
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("data/data/" + context.getPackageName() + "/databases"));
    }

    /**
     * 清楚本应用sharedPreference
     *
     * @param context
     */
    public static void cleanSharedPreferece(Context context) {
        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * 按名字清除本应用数据库 * * @param context * @param dbName
     */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * 清除/data/data/com.xxx.xxx/files下的内容 * * @param context
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache) * * @param
     * context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * * @param filePath
     */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    private static void deleteFilesByDirectory(File cacheDir) {
        if (cacheDir != null && cacheDir.exists() && cacheDir.isDirectory()) {
            for (File item :
                    cacheDir.listFiles()) {
                if (item.isFile()) {
                    item.delete();
                } else {
                    deleteFilesByDirectory(item);
                }
            }
        }
    }
}
