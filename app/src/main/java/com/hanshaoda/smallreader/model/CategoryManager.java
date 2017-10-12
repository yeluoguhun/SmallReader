package com.hanshaoda.smallreader.model;

import android.content.Context;
import android.content.res.Resources;

import com.hanshaoda.smallreader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author: hanshaoda
 * created on: 2017/9/14 下午12:11
 * description:
 */
public class CategoryManager {
    private Context mContext;

    public CategoryManager(Context context) {
        mContext = context;
    }

    public List<CategoryEntity> getAllCategory() {
        List<CategoryEntity> list = new ArrayList<>();
        Resources resources = mContext.getResources();
        String[] nameArray = resources.getStringArray(R.array.category_name);
        String[] typeArray = resources.getStringArray(R.array.category_type);

        for (int i = 0; i < (nameArray.length > typeArray.length ? typeArray.length : nameArray.length); i++) {
            CategoryEntity entity = new CategoryEntity(null, nameArray[i], typeArray[i], i);
            list.add(entity);
        }

        return list;
    }
}
