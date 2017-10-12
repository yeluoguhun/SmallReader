package com.hanshaoda.smallreader.utils.webviewutils;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;

/**
 * author: hanshaoda
 * created on: 2017/9/8 下午12:05
 * description:
 */

public class ObjectSaveManager {

    private static ObjectSaveManager mInstance;
    Context mContext;

    private ObjectSaveManager(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 获取单例引用
     *
     * @param context 上下文
     * @return 获取单例
     */
    public static ObjectSaveManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (ObjectSaveManager.class) {
                if (mInstance == null) {
                    mInstance = new ObjectSaveManager(context);
                }
            }
        }
        return mInstance;
    }

    public void saveObject(String name, Object parcelable) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = mContext.openFileOutput(name, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(parcelable);
            com.orhanobut.logger.Logger.e("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            com.orhanobut.logger.Logger.e("保存失败");
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Object getObject(String name) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = mContext.openFileInput(name);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
