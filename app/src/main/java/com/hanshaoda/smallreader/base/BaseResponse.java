package com.hanshaoda.smallreader.base;

import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * author: hanshaoda
 * created on: 2017/9/8 下午1:59
 * description:公共相应参数
 */
public abstract class BaseResponse {

    private boolean success;
    private String msg;
    private String data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    /**
     * 解析单条数据
     *
     * @throws IllegalArgumentException
     * @throws JsonSyntaxException
     */
    public abstract <T> T getBean(Class<T> clazz) throws IllegalArgumentException, JsonSyntaxException;

    /**
     * 解析数据列表
     *
     * @throws IllegalArgumentException
     * @throws JsonSyntaxException
     */
    public abstract <T> T getBeanList(Type typeOfT) throws IllegalArgumentException, JsonSyntaxException;
}
