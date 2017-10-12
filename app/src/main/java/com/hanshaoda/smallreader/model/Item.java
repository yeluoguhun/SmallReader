package com.hanshaoda.smallreader.model;

/**
 * author: hanshaoda
 * created on: 2017/9/11 上午10:51
 * description:
 */
public class Item {

    public String description;
    public String imageUrl;


    @Override
    public String toString() {
        return "Item{" +
                "description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
