package com.hanshaoda.smallreader.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * author: hanshaoda
 * created on: 2017/9/14 下午6:03
 * description:
 */

public class FourThreeImageView extends ImageView {
    public FourThreeImageView(Context context) {
        super(context);
    }

    public FourThreeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int fourThreeHeight = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec) * 3 / 4, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, fourThreeHeight);
    }
}
