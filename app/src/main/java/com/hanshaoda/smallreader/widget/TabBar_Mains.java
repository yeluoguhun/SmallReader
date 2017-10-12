package com.hanshaoda.smallreader.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hanshaoda.smallreader.R;

/**
 * author: hanshaoda
 * created on: 2017/9/12 上午10:59
 * description:
 */

public class TabBar_Mains extends LinearLayout {

    private String mName;
    private Drawable mIcon;
    private ImageView mIconImgView;
    private TextView mNameText;

    public TabBar_Mains(Context context) {
        this(context, null);
    }

    public TabBar_Mains(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.tabbar_mains, this, true);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabBar_Attr);
        mName = typedArray.getString(R.styleable.TabBar_Attr_name);
        mIcon = typedArray.getDrawable(R.styleable.TabBar_Attr_icon);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mIconImgView = findViewById(R.id.icon_tabber);
        mNameText = findViewById(R.id.name_tabber);
        if (TextUtils.isEmpty(mName)) ;
        else setName(mName);
        if (mIcon != null) {
            setIcon(mIcon);
        }
    }

    public void setIcon(Drawable icon) {
        mIconImgView.setImageDrawable(icon);
    }

    public void setName(String name) {
        mNameText.setText(name);
    }
}
