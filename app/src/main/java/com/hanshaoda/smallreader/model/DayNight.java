package com.hanshaoda.smallreader.model;

/**
 * author: hanshaoda
 * created on: 2017/9/7 下午2:34
 * description:
 */

public enum DayNight {
    DAY("DAY", 0),
    NIGHT("NIGHT", 1);

    private String name;
    private int code;

    private DayNight(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }
}
