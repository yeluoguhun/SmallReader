package com.hanshaoda.smallreader.modules.news;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.model.NewsItem;

import org.greenrobot.greendao.annotation.Id;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * author: hanshaoda
 * created on: 2017/9/13 下午3:43
 * description:
 */
public class DefaultStyleItemAdapter extends BaseQuickAdapter<NewsItem.ResultBean.DataBean, BaseViewHolder> {

    boolean isNotLoad;
    public int imgWidth;
    public int imgHeight;

    public DefaultStyleItemAdapter(int layoutResId) {
        super(layoutResId);
    }

    public DefaultStyleItemAdapter(int layoutResId, List<NewsItem.ResultBean.DataBean> data) {
        super(layoutResId, data);
    }

    public DefaultStyleItemAdapter(int layoutResId, boolean isNotLoad, int imgWidth, int imgHeight) {
        super(layoutResId);
        this.isNotLoad = isNotLoad;
        this.imgHeight = imgHeight;
        this.imgWidth = imgWidth;
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsItem.ResultBean.DataBean item) {
        helper.setText(R.id.title_news_item, item.getTitle())
                .setText(R.id.from_news_item, item.getAuthor_name())
                .setText(R.id.time_news_item, onForMatTime(item.getDate()));

        if (!isNotLoad) {

            Glide.with(mContext)
                    .load(item.getThumbnail_pic_s())
                    .placeholder(R.drawable.lodingview)
                    .error(R.mipmap.errorview)
                    .crossFade(1000)
                    .into((ImageView) helper.getView(R.id.img_news_item));
        }
    }

    private String onForMatTime(String date) {
        Date dateFormate = null;
        if (TextUtils.isEmpty(date)) {
            return getNowTime("MM-dd");
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                dateFormate = simpleDateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        if (dateFormate == null) {
            return getNowTime("MM-dd");
        }
        return simpleDateFormat.format(dateFormate);
    }

    private String getNowTime(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(s);
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        return simpleDateFormat.format(date);
    }
}
