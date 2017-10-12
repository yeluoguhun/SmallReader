package com.hanshaoda.smallreader.widget.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * author: hanshaoda
 * created on: 2017/9/13 下午4:30
 * description:
 */

public class IconFontTextView extends TextView {


    public static final String ICON_FONT = "fonts/iconfont.ttf";

    public IconFontTextView(Context context) {
        this(context, null);
    }

    public IconFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(Typeface.createFromAsset(context.getAssets(), ICON_FONT));
    }
}
