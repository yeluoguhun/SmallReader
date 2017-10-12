package com.hanshaoda.smallreader.model;

/**
 * author: hanshaoda
 * created on: 2017/10/9 下午3:45
 * description:
 */
public class RefreshJokeStyleEvent {

    public RefreshJokeStyleEvent(int jokeStyle) {
        this.jokeStyle = jokeStyle;
    }

    private int jokeStyle;

    public int getJokeStyle() {
        return jokeStyle;
    }

    public void setJokeStyle(int jokeStyle) {
        this.jokeStyle = jokeStyle;
    }
}
