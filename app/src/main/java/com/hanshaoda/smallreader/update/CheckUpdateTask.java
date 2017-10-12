package com.hanshaoda.smallreader.update;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * author: hanshaoda
 * created on: 2017/9/12 下午4:54
 * description:
 */

public class CheckUpdateTask extends AsyncTask<Void, Void, String> {
    private Context mContext;
    private int mType;
    private boolean mShowprogressDialog;
    private ProgressDialog dialog;
    private static final String url = Constants.UPDATE_URL;

    public CheckUpdateTask(Context context, int type, boolean showProgressDialog) {
        mContext = context;
        mType = type;
        mShowprogressDialog = showProgressDialog;
    }

    protected void onPreExecute() {
        if (mShowprogressDialog) {
            dialog = new ProgressDialog(mContext);
            dialog.setMessage("正在检查版本");
            dialog.show();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        if (!TextUtils.isEmpty(s)) {
            parseJson(s);
        }
    }

    private void parseJson(String s) {
        try {
            JSONArray versionArray = new JSONArray(s);
            JSONObject newVersion = versionArray.getJSONObject(0);
            String updateMessage = newVersion.getString(Constants.APK_UPDATE_CONTENT);
            String apkUrl = newVersion.getString(Constants.APK_DOWNLOAD_URL);

            int apkCode = newVersion.getInt(Constants.APK_VERSION_CODE);

            int versionCode = AppUtils.getVersionCode(mContext);

            if (apkCode > versionCode) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    skipGooglePlayUpdate();
                } else {
                    if (mType == Constants.TYPE_NOTIFICATION) {
                        showNotifiction(mContext, updateMessage, apkUrl);
                    } else if (mType == Constants.TYPE_DIALOG) {
                        showDialog(mContext, updateMessage, apkUrl);
                    }
                }
            } else {
                Toast.makeText(mContext, "已经是最新版本", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showDialog(Context mContext, String updateMessage, String apkUrl) {

    }

    private void showNotifiction(Context mContext, String updateMessage, String apkUrl) {

        Intent intent = new Intent(mContext, DownloadService.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constants.APK_DOWNLOAD_URL, apkUrl);
        PendingIntent service = PendingIntent.getService(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int icon = mContext.getApplicationInfo().icon;
        Notification notify = new NotificationCompat.Builder(mContext)
                .setTicker("发现新版本点击更新")
                .setContentTitle("发现新版本，点击进行升级")
                .setContentText(updateMessage)
                .setSmallIcon(icon)
                .setContentIntent(service).build();

        notify.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notify);
    }

    private void skipGooglePlayUpdate() {
        try {
            Uri uri = Uri.parse("market://details?=" + "com.ocnyang,qbox.app");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(mContext, "你的手机没有安装应用商城", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        return HttpUtils.get(url);
    }
}
