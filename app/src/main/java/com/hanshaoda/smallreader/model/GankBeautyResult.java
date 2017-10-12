package com.hanshaoda.smallreader.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * author: hanshaoda
 * created on: 2017/9/9 下午3:22
 * description:
 */
public class GankBeautyResult {
    public boolean error;
    public
    @SerializedName("result")
    List<GankBeauty> beauties;
}
