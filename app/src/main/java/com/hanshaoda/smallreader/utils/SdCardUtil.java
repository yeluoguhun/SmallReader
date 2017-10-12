package com.hanshaoda.smallreader.utils;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.RequiresApi;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * author: hanshaoda
 * created on: 2017/9/6 下午5:00
 * description:
 */

public class SdCardUtil {
    private static final String TAG = SdCardUtil.class.getSimpleName();

    /**
     * Get {@link StatFs}
     */
    public static StatFs getStatFs(String path) {
        return new StatFs(path);
    }

    /**
     * Get phone data path
     */
    public static String getDataPath() {
        return Environment.getDataDirectory().getPath();
    }

    /**
     * Get SD Card path
     */
    public static String getNormalSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static String getSDCardPath() {
        String cmd = "cat/proc/mounts";
        String sdcard = null;
        Runtime runtime = Runtime.getRuntime();
        BufferedReader bufferedReader = null;
        try {
            Process p = runtime.exec(cmd);
            bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(p.getInputStream())));
            String lineStr;
            while ((lineStr = bufferedReader.readLine()) != null) {
                if (lineStr.contains("sdcard") && lineStr.contains(".android_secure")) {
                    String[] strArray = lineStr.split(" ");
                    if (strArray.length >= 5) {
                        sdcard = strArray[1].replace("./android_secure", "");
                        return sdcard;
                    }
                }

                if (p.waitFor() != 0 && p.exitValue() == 1) {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        sdcard = Environment.getExternalStorageDirectory().getPath();
        return sdcard;
    }

    /**
     * Get SD Card path list
     *
     * @return
     */
    public static ArrayList<String> getSDCardPathEx() {
        ArrayList<String> list = new ArrayList<>();
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("secure")) {
                    continue;
                }
                if (line.contains("asec")) {
                    continue;
                }
                if (line.contains("fat")) {
                    String columns[] = line.split(" ");
                    if (columns.length > 1) {
                        list.add(columns[1]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Get available size of SD Card
     *
     * @param path
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getAvailableSize(String path) {
        File base = new File(path);
        StatFs statFs = new StatFs(base.getPath());
        return statFs.getBlockCountLong() * statFs.getAvailableBlocksLong();
    }

    /**
     * Get SD Card info detail
     */
    public static SDCardInfo getSDCardInfo() {
        SDCardInfo sdCardInfo = new SDCardInfo();
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            sdCardInfo.isExist = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                File sdcardDir = Environment.getExternalStorageDirectory();
                StatFs statFs = new StatFs(sdcardDir.getPath());
                sdCardInfo.totalBlocks = statFs.getBlockCountLong();
                sdCardInfo.blockByteSize = statFs.getBlockSizeLong();
                sdCardInfo.availableBlocks = statFs.getAvailableBlocksLong();
                sdCardInfo.availableBytes = statFs.getAvailableBytes();
                sdCardInfo.freeBlocks = statFs.getFreeBlocksLong();
                sdCardInfo.freeBytes = statFs.getFreeBytes();

                sdCardInfo.totalBytes = statFs.getTotalBytes();
            }
        }
        return sdCardInfo;
    }


    public static class SDCardInfo {
        public boolean isExist;
        public long totalBlocks;
        public long freeBlocks;
        public long availableBlocks;
        public long blockByteSize;
        public long totalBytes;
        public long freeBytes;
        public long availableBytes;

        @Override
        public String toString() {
            return "SDCardInfo{" +
                    "isExist=" + isExist +
                    ", totalBlocks=" + totalBlocks +
                    ", freeBlocks=" + freeBlocks +
                    ", availableBlocks=" + availableBlocks +
                    ", blockByteSize=" + blockByteSize +
                    ", totalBytes=" + totalBytes +
                    ", freeBytes=" + freeBytes +
                    ", availableBytes=" + availableBytes +
                    '}';
        }
    }
}
