package com.hanshaoda.smallreader.modules.find;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.model.FunctionBean;

import java.util.List;

/**
 * author: hanshaoda
 * created on: 2017/9/29 上午10:25
 * description:
 */
public class FindAdapter extends BaseItemDraggableAdapter<FunctionBean, BaseViewHolder> {


    public FindAdapter(List<FunctionBean> mFindList) {
        super(R.layout.item_find, mFindList);
    }

    @Override
    protected void convert(BaseViewHolder helper, FunctionBean item) {

        helper.setText(R.id.name_item_find, item.getName());
        ImageView view = helper.getView(R.id.icon_item_find);
        Glide.with(mContext)
                .load(item.getResId())
                .into(view);
    }
}
