package com.hanshaoda.smallreader.update;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat.Builder;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * author: hanshaoda
 * created on: 2017/9/12 下午5:25
 * description:
 */
public class DownloadService extends IntentService {

    private static final int BUFFER_SIZE = 10 * 1024; // 8k ~ 32K
    private static final int NOTIFICATION_ID = 100;
    private static final String TAG = "DownloadService";
    private NotificationManager mNotificationManager;
    private Builder mBuilder;

    public DownloadService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new Builder(this);
        String appName = getString(getApplicationInfo().labelRes);
        int icon = getApplicationInfo().icon;
        mBuilder.setContentTitle(appName).setSmallIcon(icon);

        String strUrl = intent.getStringExtra(Constants.APK_DOWNLOAD_URL);

        InputStream is = null;
        FileOutputStream out = null;
        try {
            URL url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.setConnectTimeout(10 * 1000);
            connection.setReadTimeout(10 * 1000);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Accept-Encoding", "gzip,deflate");

            connection.connect();
            long byte_total = connection.getContentLength();
            long byte_sum = 0;
            int byte_read = 0;
            is = connection.getInputStream();

            File dir = StorageUtils.getCacheDirectory(this);
            String apkNmae = strUrl.substring(strUrl.lastIndexOf("/") + 1, strUrl.length());
            File apkFile = new File(dir, apkNmae);

            out = new FileOutputStream(apkFile);

            byte[] buffer = new byte[BUFFER_SIZE];


            int oldprogress = 0;
            while ((byte_read = is.read(buffer)) != -1) {
                byte_sum += byte_read;
                out.write(buffer, 0, byte_read);

                int progress = (int) (byte_sum * 100L / byte_total);

                if (progress != oldprogress) {
                    updateProgress(progress);
                }
                oldprogress = progress;
            }

            installAPK(apkFile);
            mNotificationManager.cancel(NOTIFICATION_ID);

        } catch (Exception e) {
            Logger.e(TAG, "download apk file error");
        }finally {
            if (out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void installAPK(File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            String[] command = {"chomd", "777", apkFile.toString()};
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void updateProgress(int progress) {
        mBuilder.setContentText("正在下载：{progress}%%").setProgress(100, progress, false);

        PendingIntent activity = PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(activity);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
