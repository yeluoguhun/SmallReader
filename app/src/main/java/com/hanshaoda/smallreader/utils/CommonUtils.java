package com.hanshaoda.smallreader.utils;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.app.BaseApplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author: hanshaoda
 * created on: 2017/9/7 下午4:24
 * description:通用工具类
 */

public class CommonUtils {
    private static Gson mGson;
    private static final String GSON_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private SimpleDateFormat formatDay = new SimpleDateFormat("d", Locale.getDefault());
    private SimpleDateFormat formatMonthDay = new SimpleDateFormat("M-d", Locale.getDefault());
    private static SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public CommonUtils() {
    }

    /**
     * 转换object类型的对象为int
     * 作用1：对Object对象进行了判空
     * 作用2：把Object对象转换成Int
     */
    public static final int converToInt(Object value, int defaultValue) {
        if (value == null || "".equals(value.toString().trim())) {
            return defaultValue;
        }
        try {
            return Integer.valueOf(value.toString());
        } catch (Exception e) {
            try {
                return Double.valueOf(value.toString()).intValue();
            } catch (Exception e1) {
                return defaultValue;
            }
        }
    }

    /**
     * 检验json是否合法
     *
     * @param jsonContent 源
     * @return boolean
     */
    public static boolean isJsonFormat(String jsonContent) {
        try {
            new JsonParser().parse(jsonContent);
            return true;
        } catch (JsonParseException e) {
            return false;
        }
    }

    /**
     * 格式化日期
     *
     * @param date 日期
     * @return 年月日
     */
    public static String formatDate(Date date) {
        return formatDate.format(date);
    }

    /**
     * 格式化日期
     *
     * @param date 日期
     * @return 年月日 时分秒
     */
    public static String formatDateTime(Date date) {
        return formatDateTime.format(date);
    }

    /**
     * 将时间戳解析成日期
     *
     * @return 年月日
     */
    public static String parseDate(long timeInMillis) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(timeInMillis);
        Date date = instance.getTime();
        return formatDate(date);
    }

    /**
     * 将时间戳解析成日期
     *
     * @return 年月日时分秒
     */
    public static String parseDateTime(long timeInMillis) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(timeInMillis);
        Date time = instance.getTime();
        return formatDateTime(time);
    }


    /**
     * 解析日期
     *
     * @param date 日期
     * @return 年月日
     */
    public static Date parseDate(String date) {
        Date mDate = null;
        try {
            mDate = formatDate.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mDate;
    }

    /**
     * 解析日期
     *
     * @param datetime 日期
     * @return 年月日 时分秒
     */
    public static Date parseDateTime(String datetime) {
        Date mDate = null;
        try {
            mDate = formatDateTime.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return mDate;
    }

    /**
     * 对指定字符进行MD5加密
     *
     * @param s 要加密的
     * @return 加密后的
     */
    public static String encryprMD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            byte[] btInput = s.getBytes();

            MessageDigest md5 = MessageDigest.getInstance("MD5");//获取MD5摘要算法的MessageDigest对象
            md5.update(btInput);//使用指定字节更新摘要

            byte[] digest = md5.digest();// 获取密文
            int length = digest.length;//把密文转化成十六进制的字符串形式
            char[] chars = new char[length * 2];
            int k = 0;
            for (int i = 0; i < length; i++) {
                byte byte0 = digest[i];
                chars[k++] = hexDigits[byte0 >>> 4 & 0xf];
                chars[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(chars);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断email格式是否正确
     *
     * @param email 邮件地址
     * @return boolean
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 根据系统语言判断是否是中国
     *
     * @return Boolean
     */
    public static boolean isZh() {
        Locale locale = BaseApplication.getInstance().getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.startsWith("zh")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Try to return the absolute file path from the given Uri
     *
     * @param context 上下文
     * @param uri     uri
     * @return the file path or null
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 获取gson对象
     *
     * @return gson
     */
    public static Gson getGson() {
        if (mGson == null) {
            mGson = new GsonBuilder().setDateFormat(GSON_FORMAT).create();
        }
        return mGson;
    }

    /**
     * 调用振动器
     *
     * @param context      调用该方法的context
     * @param milliseconds 震动的时长，单位毫秒
     */
    public static void vibrate(final Context context, long milliseconds) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    /**
     * 调用震动器
     *
     * @param context  调用该方法的Context
     * @param pattern  自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
     * @param isRepeat 是否反复震动，如果是true，反复震动，如果是false，只震动一次
     */
    public static void vibrate(final Context context, long[] pattern, boolean isRepeat) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }

    /**
     * 播放音乐
     *
     * @param context 上下文
     */
    public static void playMusic(Context context) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.beep);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
    }

    private String getContectPhone(Context context, Cursor cursor) {
        int phoneColumn = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        int phoneNum = cursor.getInt(phoneColumn);
        String phoneResult = "";
        if (phoneNum > 0) {
//            获取联系人ID
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String contactId = cursor.getString(idColumn);
//            获取联系人电话号码的cursor
            Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);

            if (phones.moveToFirst()) {
//                遍历所有的电话号码
                for (; !phones.isAfterLast(); phones.moveToNext()) {
                    int index = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int type = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                    int phone_type = phones.getInt(type);
                    String phoneNumber = phones.getString(index);
                    switch (phone_type) {
                        case 2:
                            phoneResult = phoneNumber;
                            break;
                    }
                }
                if (!phones.isClosed()) {
                    phones.close();
                }
            }
        }
        return phoneResult;
    }
}
