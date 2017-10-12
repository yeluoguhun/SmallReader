package com.hanshaoda.smallreader.modules.find.constellation;

/**
 * author: hanshaoda
 * created on: 2017/10/10 上午11:39
 * description:
 */
public class RefreshConstellationEvent {
    private String constellation;

    public RefreshConstellationEvent(String chengConstellation) {
        constellation = chengConstellation;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }
}
