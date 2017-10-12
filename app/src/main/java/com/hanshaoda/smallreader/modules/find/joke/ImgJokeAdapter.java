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
 * created on: 2017/10/9 下午4:03
 * description:
 */
public class ImgJokeAdapter extends BaseMultiItemQuickAdapter<TextJokeBean, BaseViewHolder> {
    public ImgJokeAdapter(List<TextJokeBean> mJokeBeanList) {
        super(mJokeBeanList);
        addItemType(TextJokeBean.JOKE, R.layout.item_img_joke_joke);
        addItemType(TextJokeBean.MORE, R.layout.item_text_joke_more);
    }

    @Override
    protected void convert(BaseViewHolder helper, TextJokeBean item) {

        if (helper.getItemViewType()==TextJokeBean.JOKE){
            helper.setText(R.id.tv_item_img,item.getContent());
            ImageUtils.loadingImgUrl(mContext,item.getUrl(),R.mipmap.errorview,500, (ImageView) helper.getView(R.id.img_item_img_joke));
        }else if (TextJokeBean.MORE==helper.getItemViewType()){
            ImageUtils.loadingImgRes(mContext,R.drawable.loadingjoke, (ImageView) helper.getView(R.id.img_item_more_joke));
        }
    }
}
