package com.hanshaoda.smallreader.model;

import java.util.List;

/**
 * author: hanshaoda
 * created on: 2017/9/9 下午3:56
 * description:
 */
public class RandomTextJoke extends BaseJokeBean{

    private List<TextJokeBean> result;

    public List<TextJokeBean> getResult() {
        return result;
    }

    public void setResult(List<TextJokeBean> result) {
        this.result = result;
    }
}
