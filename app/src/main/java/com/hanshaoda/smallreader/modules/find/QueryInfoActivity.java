package com.hanshaoda.smallreader.modules.find;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.base.BaseCommonActivity;
import com.hanshaoda.smallreader.model.QueryTel;
import com.hanshaoda.smallreader.network.NetWork;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class QueryInfoActivity extends BaseCommonActivity {

    public static final String QUERY_TEL_KEY = "576f995fdbc2c0e9db5fa785da34efd6";

    public static final String QUERY_STYLE = "style";
    public static final int QUERY_TEL = 1;
    @BindView(R.id.toolbar_query)
    Toolbar mToolbarQuery;
    @BindView(R.id.input_text_query_info)
    TextInputEditText mInputTextQueryInfo;
    @BindView(R.id.input_layout_query_info)
    TextInputLayout mInputLayoutQueryInfo;
    @BindView(R.id.result_query_info)
    TextView mResultQueryInfo;
    @BindView(R.id.find_query_info)
    FloatingActionButton mFindQueryInfo;
    @BindView(R.id.activity_query_info)
    CoordinatorLayout mActivityQueryInfo;
    private int mQueryStyle;


    Observer<Object> mObserver = new Observer<Object>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

            Logger.e(e.getMessage());
        }

        @Override
        public void onNext(Object o) {

            QueryTel queryTel = (QueryTel) o;
            Logger.e("得到数据："+queryTel.getResult().toString());
            showResult(queryTel.getResult().toString());
        }
    };
    private Subscription mSubscription;

    private void showResult(String string) {

        if (TextUtils.isEmpty(string)) {
            mResultQueryInfo.setText("查询结果有误，请检查输入的手机号");
        } else {
            mResultQueryInfo.setText(string);
        }
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_query_info);
    }

    @Override
    public void initView() {

        initGetIntent();
        initToolbar();
        initEditText();
        initFab();
    }

    private void initFab() {
        mFindQueryInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mInputTextQueryInfo.getText())) {
                    mInputLayoutQueryInfo.setError("请输入要查询的内容");
                } else {
                    requestData();
                }
            }
        });
    }

    /**
     * 请求数据
     */
    private void requestData() {
        unSubscribe();
        mSubscription = NetWork.getQueryTelApi()
                .getTelInfo(QUERY_TEL_KEY, mInputTextQueryInfo.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }

    private void unSubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    /**
     * 输入框条件限制
     */
    private void initEditText() {
        mInputTextQueryInfo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
    }

    private void initToolbar() {
        mToolbarQuery.setTitle("手机归属地查询");
        mToolbarQuery.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        mToolbarQuery.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initGetIntent() {
        Intent intent = getIntent();
        mQueryStyle = intent.getIntExtra(QUERY_STYLE, 0);
        if (mQueryStyle == 0) {
            Toast.makeText(this, "页面入口有误", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void initPresenter() {

    }
}
