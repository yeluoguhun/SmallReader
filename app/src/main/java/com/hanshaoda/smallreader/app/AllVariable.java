package com.hanshaoda.smallreader.app;

import android.annotation.SuppressLint;

import com.hanshaoda.smallreader.utils.SdCardUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * author: hanshaoda
 * created on: 2017/9/8 上午10:22
 * description:
 */

public class AllVariable {

    /**
     * 应用程序名
     */
    public static final String APPNAME = "FastAndroid";
    /**
     * 文件根目录
     */
    public static final String STORAGEPATH = SdCardUtil.getNormalSDCardPath() + "/" + APPNAME + "/";
    /**
     * 自动更新文件下载路径
     */
    public static final String UPDATE_APP_SAVE_PATH = STORAGEPATH + APPNAME + ".apk";
    /**
     * 系统图片
     */
    public static final String APPIMAGE = STORAGEPATH + "img/";
    /**
     * 录音文件保存
     */
    public static final String APPRECORD = STORAGEPATH + "record/";
    /**
     * 打开相机
     */
    public static final int ACTION_CAMERA = 0X01;
    /**
     * 打开相册
     */
    public static final int ACTION_ALBUM = 0X02;
    /**
     * 打开扫描
     */
    public static final int ACTION_BARCODE = 0X03;
    /**
     * 打开录音
     */
    public static final int ACTION_RECORDER = 0X04;
    /**
     * 打开通讯录
     */
    public static final int ACTION_ADDRESSLIST = 0X05;

    @SuppressLint("SimpleDateFormat")
    public static String getPathCurrentTime() {
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
        return date.format(Calendar.getInstance().getTime());
    }
}
