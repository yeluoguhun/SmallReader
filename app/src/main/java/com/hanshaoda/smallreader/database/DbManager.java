package com.hanshaoda.smallreader.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hanshaoda.smallreader.db.DaoMaster;

/**
 * author: hanshaoda
 * created on: 2017/9/11 下午4:54
 * description:
 */

public class DbManager {

    private static final String dbName = "test_db";
    private static DbManager mInstance;
    private Context context;
    private DaoMaster.DevOpenHelper openHelper;

    private DbManager(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DbManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DbManager.class) {
                if (mInstance == null) {
                    mInstance = new DbManager(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取可读数据库
     */
    public SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    public SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }



}
