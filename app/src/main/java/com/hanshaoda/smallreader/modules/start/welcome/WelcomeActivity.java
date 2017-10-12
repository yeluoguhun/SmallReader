package com.hanshaoda.smallreader.modules.start.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.gson.Gson;
import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.config.Constant;
import com.hanshaoda.smallreader.database.CategoryDao;
import com.hanshaoda.smallreader.database.FunctionDao;
import com.hanshaoda.smallreader.model.CategoryManager;
import com.hanshaoda.smallreader.model.Function;
import com.hanshaoda.smallreader.model.FunctionBean;
import com.hanshaoda.smallreader.modules.me.MeFragment;
import com.hanshaoda.smallreader.utils.SPUtils;
import com.hanshaoda.smallreader.utils.StateBarTranslucentUtils;
import com.hanshaoda.smallreader.utils.StreamUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * author: hanshaoda
 * created on: 2017/9/14 上午10:53
 * description:
 */

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    public static void start(Context context) {
        SPUtils.put(context, Constant.FIRST_OPEN, true);
        context.startActivity(new Intent(context, WelcomeActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
//        设置标题栏
        StateBarTranslucentUtils.setStateBarTranslucent(this);

        findViewById(R.id.btn_retry).setOnClickListener(this);
        if (savedInstanceState != null) {
            replaceTutorialFragment();
        }
        saveCategoryToDB();
        saveFunctionToDB();

    }

    private void saveFunctionToDB() {
        Function function = null;
        try {
            function = (new Gson()).fromJson(StreamUtils.get(getApplicationContext(), R.raw.function), Function.class);

        } catch (Exception e) {
            Logger.e("读取raw中的function文件");
        }
        if (function != null && function.getFunction() != null && function.getFunction().size() != 0) {
            List<FunctionBean> functionBeanList = function.getFunction();
            FunctionDao functionDao = new FunctionDao(getApplicationContext());
            functionDao.insertFunctionList(functionBeanList);
        }
    }

    private void saveCategoryToDB() {

        CategoryDao categoryDao = new CategoryDao(getApplicationContext());
        categoryDao.deleteCategoryList();
        categoryDao.insertCategoryList(new CategoryManager(this).getAllCategory());
    }

    private void replaceTutorialFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_welcome, new MeFragment())
                .commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_retry:
                replaceTutorialFragment();
                break;
        }
    }
}
