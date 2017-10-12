package com.hanshaoda.smallreader.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * author: hanshaoda
 * created on: 2017/10/9 下午2:46
 * description:
 */

public class ImageUtils {
    public static void loadingImgUrl(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .into(view);
    }

    public static void loadingImgUrl(Context context, String url, int errorImage, ImageView view) {
        Glide.with(context)
                .load(url)
                .error(errorImage)
                .into(view);
    }

    public static void loadingPalceImgUrl(Context context, String url, int errorImage, int palceImage, ImageView view) {
        Glide.with(context)
                .load(url)
                .placeholder(palceImage)
                .error(errorImage)
                .into(view);
    }

    public static void loadingImgUrl(Context context, String url, int errorImage, int crossFade, ImageView view) {
        Glide.with(context)
                .load(url)
                .error(errorImage)
                .crossFade(crossFade)
                .into(view);
    }

    public static void loadingImgRes(Context context, int resId, ImageView view) {
        Glide.with(context)
                .load(resId)
                .into(view);
    }

}
