package com.hanshaoda.smallreader.widget.custom;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hanshaoda.smallreader.R;

/**
 * author: hanshaoda
 * created on: 2017/9/7 下午6:24
 * description:自定义对话框
 */

public class CustomConfirmDialog {

    private Button btnPositive, btnNegative;
    private TextView tvTitle;
    private AlertDialog.Builder builder;
    private Context context;
    private AlertDialog alertDialog;

    public CustomConfirmDialog(Context context, String title, View.OnClickListener positiveListener) {
        this.context = context;
        builder = new AlertDialog.Builder(context);
        View customView = getCustomView(title, positiveListener, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        builder.setView(customView);
    }

    public void show() {
        alertDialog = builder.create();
        alertDialog.show();
    }

    private View getCustomView(String title, View.OnClickListener positiveListener, View.OnClickListener negativeListener) {
        View mView = LayoutInflater.from(context).inflate(R.layout.dialog_custom_layout, null);
        tvTitle = mView.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        btnPositive = mView.findViewById(R.id.btn_positive);
        btnPositive.setOnClickListener(positiveListener);
        btnNegative = mView.findViewById(R.id.btn_negative);
        btnNegative.setOnClickListener(negativeListener);
        return mView;
    }

}
