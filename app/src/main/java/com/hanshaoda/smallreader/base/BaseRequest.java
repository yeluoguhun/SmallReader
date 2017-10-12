package com.hanshaoda.smallreader.base;

import android.text.TextUtils;

import com.hanshaoda.smallreader.utils.CommonUtils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * author: hanshaoda
 * created on: 2017/9/6 下午3:02
 * description:公共请求
 */
public class BaseRequest {


    /**
     * 将实体类转换成请求参数，json字符串形式返回
     *
     * @return 结果
     */
    public String getJsonParams() {
        String s = CommonUtils.getGson().toJson(this);
        if (TextUtils.isEmpty(s)) {
            s = "";
        }
        return s;
    }

    /**
     * 将实体类转换成请求参数，以map形式返回
     *
     * @return
     */
    public Map<String, String> getMapParams() {
        Class<? extends BaseRequest> aClass = this.getClass();
        Class<? extends Object> superclass = aClass.getSuperclass();
        Field[] fields = aClass.getDeclaredFields();
        Field[] superFields = superclass.getDeclaredFields();
        if (fields == null || fields.length == 0) {
            return Collections.emptyMap();
        }
        Map<String, String> params = new HashMap<>();

        try {
            for (Field field : fields) {
                field.setAccessible(true);
                params.put(field.getName(), String.valueOf(field.get(this)));
            }
            for (Field superField : superFields) {
                superField.setAccessible(true);
                params.put(superField.getName(), String.valueOf(superField));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return params;
    }
}
