package com.hanshaoda.smallreader.network;

import android.content.ClipData;

import com.hanshaoda.smallreader.model.GankBeauty;
import com.hanshaoda.smallreader.model.GankBeautyResult;
import com.hanshaoda.smallreader.model.Item;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * author: hanshaoda
 * created on: 2017/9/11 上午10:50
 * description:
 */

public class GankBeautyResultToItemsMapper implements Func1<GankBeautyResult, List<Item>> {

    public static GankBeautyResultToItemsMapper INSTANCE = new GankBeautyResultToItemsMapper();

    public GankBeautyResultToItemsMapper() {
    }

    public static GankBeautyResultToItemsMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Item> call(GankBeautyResult gankBeautyResult) {
        List<GankBeauty> beauties = gankBeautyResult.beauties;
        List<Item> items = new ArrayList<>(beauties.size());
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        for (GankBeauty gankBeauty : beauties) {
            Item item = new Item();
            try {
                Date date = inputFormat.parse(gankBeauty.createdAt);
                item.description = outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                item.description = "unknown date";
            }
            item.imageUrl = gankBeauty.url;
            items.add(item);
        }
        return items;
    }
}
