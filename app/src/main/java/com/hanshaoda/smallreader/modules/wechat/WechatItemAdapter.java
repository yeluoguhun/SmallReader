package com.hanshaoda.smallreader.modules.wechat;

import android.text.TextUtils;
import android.view.TextureView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.model.WechatItem;

import java.util.List;

/**
 * author: hanshaoda
 * created on: 2017/9/14 下午5:41
 * description:
 */
public class WechatItemAdapter extends BaseMultiItemQuickAdapter<WechatItem.ResultBean.ListBean, BaseViewHolder> {

    private boolean isNotLoad;
    private int mImgWidth;
    private int mImgHeight;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public WechatItemAdapter(List<WechatItem.ResultBean.ListBean> data) {
        super(data);
        addItemType(WechatItem.ResultBean.ListBean.STYPE_BIG, R.layout.item_wechat_style1);
        addItemType(WechatItem.ResultBean.ListBean.STYPE_SMALL, R.layout.item_wechat_style2);
    }

    public WechatItemAdapter(List<WechatItem.ResultBean.ListBean> data, boolean isNotLoadImg, int imgWidth, int imgHeight) {
        super(data);
        addItemType(WechatItem.ResultBean.ListBean.STYPE_BIG, R.layout.item_wechat_style1);
        addItemType(WechatItem.ResultBean.ListBean.STYPE_SMALL, R.layout.item_wechat_style2);
        isNotLoad = isNotLoadImg;
        mImgWidth = imgWidth;
        mImgHeight = imgHeight;

    }

    @Override
    protected void convert(BaseViewHolder helper, WechatItem.ResultBean.ListBean item) {

        switch (helper.getItemViewType()) {
            case WechatItem.ResultBean.ListBean.STYPE_BIG:
                helper.setText(R.id.title_wechat_style1, TextUtils.isEmpty(item.getTitle()) ? "微信精选" : item.getTitle());
                if (!isNotLoad) {
                    Glide.with(mContext.getApplicationContext())
                            .load(item.getFirstImg())
                            .override(mImgWidth, mImgHeight)
                            .placeholder(R.drawable.lodingview)
                            .error(R.mipmap.errorview)
                            .crossFade(1000)
                            .into((ImageView) helper.getView(R.id.img_wechat_style));
                }
                break;
            case WechatItem.ResultBean.ListBean.STYPE_SMALL:
                helper.setText(R.id.title_wechat_style2, TextUtils.isEmpty(item.getTitle()) ? "微信精选" : item.getTitle());
                if (!isNotLoad) {
                    Glide.with(mContext.getApplicationContext())
                            .load(item.getFirstImg())
                            .placeholder(R.drawable.lodingview)
                            .error(R.mipmap.errorview)
                            .override(mImgWidth, mImgHeight)
                            .crossFade(1000)
                            .into((ImageView) helper.getView(R.id.img_wechat_style));
                }

                break;
            default:
                break;
        }
    }


}
