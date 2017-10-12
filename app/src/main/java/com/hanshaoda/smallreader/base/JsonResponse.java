package com.hanshaoda.smallreader.base;

import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.hanshaoda.smallreader.utils.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * author: hanshaoda
 * created on: 2017/9/8 下午1:58
 * description:
 */

public class JsonResponse extends BaseResponse {

    /**
     * 解析json获取响应对象
     */
    public static JsonResponse getResponse(String json) {
        JsonResponse jsonResponse = new JsonResponse();
        boolean success = false;
        String msg = "";
        String data = "";

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(json);
            boolean hasCode = jsonObject.has("success");
            if (hasCode) {
                success = jsonObject.getBoolean("success");
            }
            boolean hasMessage = jsonObject.has("message");
            if (hasMessage) {
                msg = jsonObject.getString("message");
            }

            boolean hasData = jsonObject.has("data");
            if (hasData) {
                data = jsonObject.getString("data");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonResponse.setSuccess(success);
        jsonResponse.setMsg(msg);
        jsonResponse.setData(data);
        return jsonResponse;
    }

    /**
     * 不解析json,直接作为响应的data封装后返回
     *
     * @param json
     * @return
     */
    public static JsonResponse getOnlyDataResponse(String json) {
        JsonResponse mResponse = new JsonResponse();

        mResponse.setData(json);

        return mResponse;
    }

    public <T> T getBean(Class<T> clazz) throws IllegalArgumentException,
            JsonSyntaxException {
        if (TextUtils.isEmpty(getData()))
            throw new IllegalArgumentException(
                    "In the JsonResponse, data can't be empty");

        T object = CommonUtils.getGson().fromJson(getData(), clazz);

        return object;
    }

    public <T> T getBeanList(Type typeOfT) throws IllegalArgumentException,
            JsonSyntaxException {
        if (TextUtils.isEmpty(getData()))
            throw new IllegalArgumentException(
                    "In the JsonResponse, data can't be empty");

        T object = CommonUtils.getGson().fromJson(getData(), typeOfT);

        return object;
    }
}
