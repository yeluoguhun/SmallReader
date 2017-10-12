package com.hanshaoda.smallreader.model;

import java.util.List;

/**
 * author: hanshaoda
 * created on: 2017/9/9 下午3:54
 * description:
 */
public class NewTextJokeBean extends BaseJokeBean{

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<TextJokeBean> data;

        public List<TextJokeBean> getData() {
            return data;
        }

        public void setData(List<TextJokeBean> data) {
            this.data = data;
        }
    }
}
