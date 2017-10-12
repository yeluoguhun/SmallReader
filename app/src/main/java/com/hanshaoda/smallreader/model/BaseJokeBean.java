package com.hanshaoda.smallreader.model;

/**
 * author: hanshaoda
 * created on: 2017/10/9 下午3:15
 * description:
 */

public class BaseJokeBean {

    private int error_code;
    private String reason;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
