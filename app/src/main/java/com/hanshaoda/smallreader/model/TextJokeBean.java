package com.hanshaoda.smallreader.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * author: hanshaoda
 * created on: 2017/9/9 下午4:00
 * description:
 */
public class TextJokeBean implements MultiItemEntity {
    public static final int JOKE=0;
    public static final int MORE=1;

    private int itemType;
    private String content;
    private String url;

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
