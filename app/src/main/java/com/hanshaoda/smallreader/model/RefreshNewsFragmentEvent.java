package com.hanshaoda.smallreader.model;

/**
 * author: hanshaoda
 * created on: 2017/9/13 上午10:27
 * description:
 */
public class RefreshNewsFragmentEvent {

    private int mark_code;

    public int getMark_code() {
        return mark_code;
    }

    public void setMark_code(int mark_code) {
        this.mark_code = mark_code;
    }

    public RefreshNewsFragmentEvent() {
    }

    public RefreshNewsFragmentEvent(int mark_code) {
        this.mark_code = mark_code;
    }
}
