package com.hanshaoda.smallreader.utils.webviewutils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.webkit.JavascriptInterface;

import com.hanshaoda.smallreader.modules.main.MainActivity;
import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.app.AllVariable;
import com.hanshaoda.smallreader.app.BaseApplication;
import com.hanshaoda.smallreader.base.BaseActivity;
import com.hanshaoda.smallreader.base.BaseFragment;
import com.hanshaoda.smallreader.utils.CommonUtils;

/**
 * author: hanshaoda
 * created on: 2017/9/7 下午5:36
 * description:
 */

public class JSInterface {

    private BaseActivity mContext;
    private BaseFragment mFragment;

    public JSInterface(BaseFragment mFragment) {
        this.mFragment = mFragment;
        this.mContext = mFragment.getBaseActivity();
    }

    public JSInterface(BaseActivity mContext) {
        this.mContext = mContext;
    }

    /**
     * 依据type类型弹出Toast/Dialog
     *
     * @param type    1 Toast 2 Dialog
     * @param message 显示的信息
     */
    @JavascriptInterface
    public void showMessage(String type, String message) {
        mContext.showToast(message);
    }

    /**
     * 调用系统相册
     */
    @JavascriptInterface
    public void openPhotoAlbum() {
        Intent albumIntent = new Intent();
        albumIntent.setType("image/*");
        albumIntent.setAction(Intent.ACTION_GET_CONTENT);
        if (mFragment != null) {
            mFragment.startActivityForResult(albumIntent, AllVariable.ACTION_ALBUM);
        } else {
            mContext.startActivityForResult(albumIntent, AllVariable.ACTION_ALBUM);
        }
    }

    @JavascriptInterface
    public void openNativeCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mFragment.startActivityForResult(intent, AllVariable.ACTION_CAMERA);

    }

    /**
     * 打开第三方应用
     *
     * @param appPackageName 应用包名
     * @param className      应用的主activity
     */
    @JavascriptInterface
    public void openThirdApp(String appPackageName, String className) {
        if (mFragment != null) {
            Intent intent = new Intent();
            ComponentName componentName = new ComponentName(appPackageName, className);
            intent.setComponent(componentName);
            mContext.startActivity(intent);
        }
    }

    /**
     * 显示进度条
     *
     * @param message 显示信息
     */
    @JavascriptInterface
    public void showProgressBar(String message) {
        mContext.showProgress(message);
    }

    /**
     * 隐藏进度条
     */
    @JavascriptInterface
    public void hideProgressBar() {
        mContext.hideProgress();
    }

    /**
     * 启动一个通知栏
     *
     * @param titleName   通知标题栏
     * @param contentText 通知栏内容
     */
    @JavascriptInterface
    public void ontificationBar(String titleName, String contentText) {
        Context context = BaseApplication.getInstance();
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
//        实例化通知栏构造器
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, 0, intent, 0);
//        对builder 配置
        builder.setContentTitle(titleName)//设置通知的标题
                .setContentText(contentText)//内容
                .setContentIntent(activity)//通知栏点击意图
                .setTicker(titleName) //通知首次出现在通知栏，带上升动画的效果
                .setWhen(System.currentTimeMillis()) //通知产生的时间，显示在信息里，一般用系统时间
                .setPriority(Notification.PRIORITY_DEFAULT) //该通知的优先级
                .setAutoCancel(true) //点击面板就可以让通知自动取消
                .setOngoing(false)
                .setDefaults(Notification.DEFAULT_ALL) // 通知的声音、震动、闪灯使用用户当前默认设置
                .setSmallIcon(R.mipmap.ic_launcher);//通知的icon

//此方法生成标题栏消息通知
        mNotificationManager.notify(Notification.FLAG_ONLY_ALERT_ONCE, builder.build());
    }


    /**
     * 发送短信
     *
     * @param type        类型
     * @param phoneNumber 要发送的号码
     * @param message     要发送的内容
     */
    @JavascriptInterface
    public void sendMessage(String type, String phoneNumber, String message) {
        if ("1".equals(type)) {
            String SENT_SMS_ACTION = "SENT_SMS_ACTION";
            Intent intent = new Intent(SENT_SMS_ACTION);
            PendingIntent sentPI = PendingIntent.getBroadcast(mContext, 0, intent, 0);
            SmsManager aDefault = SmsManager.getDefault();
            aDefault.sendTextMessage(phoneNumber, null, message, sentPI, null);
        } else if ("2".equals(type)) {
            Uri parse = Uri.parse("smsto:" + phoneNumber);
            Intent intent = new Intent(Intent.ACTION_VIEW, parse);
            intent.putExtra("sms_body", message);
            mContext.startActivity(intent);
        }
    }

    /**
     * 打开录音机
     */
    @JavascriptInterface
    public void openRecorder() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/amr");
        intent.setClassName("com.android.soundrecorder", "com.android.soundrecorder.SoundRecorder");
        mFragment.startActivityForResult(intent, AllVariable.ACTION_RECORDER);
    }

    /**
     * 调用震动器
     */
    @JavascriptInterface
    public void deviceVibrate() {
        CommonUtils.vibrate(mContext, 300);
    }

    /**
     * 播放音乐
     */
    @JavascriptInterface
    public void playAudio() {
        CommonUtils.playMusic(mContext);
    }

    /**
     * 拨打电话
     *
     * @param phoneNumber
     */
    @JavascriptInterface
    public void openMobilePhone(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
//        mContext.startActivity(intent);
    }

    /**
     * 打开通讯录
     */
    @JavascriptInterface
    public void openAddressList() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        mFragment.startActivityForResult(intent, AllVariable.ACTION_ADDRESSLIST);
    }
}
