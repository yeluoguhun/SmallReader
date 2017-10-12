package com.hanshaoda.smallreader.model;

/**
 * author: hanshaoda
 * created on: 2017/9/9 下午3:31
 * description:
 */
public class WeekConstellation extends BaseConstellation {
    /**
     * weekth : 13
     */

    private int weekth;
    private String job;

    public int getWeekth() {
        return weekth;
    }

    public void setWeekth(int weekth) {
        this.weekth = weekth;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "weekth=" + weekth
                + "job=" + job;
    }
}
