package com.hanshaoda.smallreader.modules.find.joke;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.model.TextJokeBean;
import com.hanshaoda.smallreader.utils.ImageUtils;

import java.util.List;

/**
 * author: hanshaoda
 * created on: 2017/10/9 下午2:16
 * description:
 */
public class TextJokeAdapter extends BaseMultiItemQuickAdapter<TextJokeBean, BaseViewHolder> {
    public TextJokeAdapter(List<TextJokeBean> mJokeBeanList) {
        super(mJokeBeanList);
        addItemType(TextJokeBean.JOKE, R.layout.item_text_joke);
        addItemType(TextJokeBean.MORE, R.layout.item_text_joke_more);
    }

    @Override
    protected void convert(BaseViewHolder helper, TextJokeBean item) {

        if (helper.getItemViewType() == TextJokeBean.JOKE) {
            helper.setText(R.id.tv_item_text, item.getContent());
        } else if (helper.getItemViewType() == TextJokeBean.MORE) {
            ImageUtils.loadingImgRes(mContext, R.drawable.loadingjoke,
                    (ImageView) helper.getView(R.id.img_item_more_joke));
        }
    }
}
