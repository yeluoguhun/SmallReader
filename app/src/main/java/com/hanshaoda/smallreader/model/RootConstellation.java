package com.hanshaoda.smallreader.model;

/**
 * author: hanshaoda
 * created on: 2017/10/10 下午4:20
 * description:
 */
public class RootConstellation {
    private String name;
    private String date;
    private String resultcode;
    private int error_code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}
