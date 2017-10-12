package com.hanshaoda.smallreader.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * author: hanshaoda
 * created on: 2017/9/7 下午12:26
 * description:文件或文件夹操作类
 */

public class FileInfoUtils {

    /**
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFileSizes(File file) throws Exception {
        long s = 0;
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            s = fis.available();
            fis.close();
        } else {
            file.createNewFile();
            System.out.println("文件不存在");
        }
        return s;
    }

    /**
     * 递归
     *
     * @param f 文件
     * @return long
     * @throws Exception
     */
    public static long getFileSize(File f) throws Exception {
        long size = 0;
        File[] files = f.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                size = size + getFileSizes(files[i]);
            } else {
                size = size + files[i].length();
            }
        }
        return size;
    }

    /**
     * 转化文件大小
     */
    public static String formatFileSzie(long fileS) {
        DecimalFormat df = new DecimalFormat("#0.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS) + "k";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS) + "M";
        } else {
            fileSizeString = df.format((double) fileS) + "G";
        }
        return fileSizeString;
    }

    /**
     * 递归求取目录文件个数
     *
     * @param f 目标文件
     * @return 数量
     */
    public static long getList(File f) {
        long size = 0;
        File[] files = f.listFiles();
        size = files.length;
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                size += getList(files[i]);
                size--;
            }
        }
        return size;
    }
}
