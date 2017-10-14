package com.hanshaoda.smallreader.model;

/**
 * author: hanshaoda
 * created on: 2017/10/13 下午4:04
 * description:
 */
public class RefreshMeFragmentEvent {

    private int mark_code;

    public int getMark_code() {
        return mark_code;
    }

    public void setMark_code(int mark_code) {
        this.mark_code = mark_code;
    }

    public RefreshMeFragmentEvent(int i) {
        mark_code = i;
    }
}
