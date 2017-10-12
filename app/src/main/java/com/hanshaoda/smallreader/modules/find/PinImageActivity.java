package com.hanshaoda.smallreader.modules.find;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.base.BaseCommonActivity;
import com.hanshaoda.smallreader.utils.ImageUtils;
import com.hanshaoda.smallreader.utils.StateBarTranslucentUtils;
import com.hanshaoda.smallreader.widget.custom.PinchImageView;

import butterknife.BindView;

/**
 * author: hanshaoda
 * created on: 2017/9/27 下午6:00
 * description:
 */
public class PinImageActivity extends BaseCommonActivity {

    public static final String IMG_NAME = "img_name";
    public static final String IMG_URL = "img_url";
    @BindView(R.id.img_pinimg)
    PinchImageView mImgPinimg;
    @BindView(R.id.tv_pinimg)
    TextView mTvPinimg;
    private String mImgUrl;
    private String mImgName;

    @Override
    public void initContentView() {

        StateBarTranslucentUtils.setStateBarTranslucent(this);
        setContentView(R.layout.activity_pin_image);
    }

    @Override
    public void initView() {

        initIntent();
        initImg();
        initImageName();
    }

    private void initImageName() {
        if (TextUtils.isEmpty(mImgName)) {
            if (mTvPinimg.getVisibility() == View.VISIBLE) {
                mTvPinimg.setVisibility(View.GONE);
            }
            return;
        }
        if (mTvPinimg.getVisibility() == View.GONE) {
            mTvPinimg.setVisibility(View.VISIBLE);
        }
        mTvPinimg.setText(mImgName);
    }

    private void initImg() {
        mImgPinimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollback();
            }
        });
        if (TextUtils.isEmpty(mImgUrl)) {
            return;
        }
        if (mImgUrl.toUpperCase().endsWith(".GIF")) {
            Glide.with(this)
                    .load(mImgUrl)
                    .asGif()
                    .placeholder(R.drawable.lodingview)
                    .error(R.mipmap.errorview)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(mImgPinimg);
        } else {
            ImageUtils.loadingPalceImgUrl(this, mImgUrl, R.drawable.lodingview, R.mipmap.errorview, mImgPinimg);
        }
    }

    private void rollback() {

        onBackPressed();
    }

    private void initIntent() {
        Intent intent = getIntent();
        mImgUrl = intent.getStringExtra(IMG_URL);
        mImgName = intent.getStringExtra(IMG_NAME);
    }

    @Override
    public void initPresenter() {

    }

}
