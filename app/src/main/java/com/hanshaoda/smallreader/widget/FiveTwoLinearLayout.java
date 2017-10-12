package com.hanshaoda.smallreader.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.hanshaoda.smallreader.utils.PixelUtil;

/**
 * author: hanshaoda
 * created on: 2017/9/13 下午4:10
 * description:
 */

public class FiveTwoLinearLayout extends LinearLayout {
    public FiveTwoLinearLayout(Context context) {
        this(context, null);
    }

    public FiveTwoLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FiveTwoLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int fourThreeHeight = MeasureSpec.makeMeasureSpec(
                ((MeasureSpec.getSize(widthMeasureSpec) - PixelUtil.dp2px(40)) / 4) + PixelUtil.dp2px(20),
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, fourThreeHeight);
    }
}
