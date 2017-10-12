package com.hanshaoda.smallreader.modules.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.base.BaseFragment;
import com.hanshaoda.smallreader.config.Constant;
import com.hanshaoda.smallreader.modules.me.calender.CalenderActivity;
import com.hanshaoda.smallreader.modules.me.weather.WeatherActivity;
import com.hanshaoda.smallreader.utils.SPUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * author: hanshaoda
 * created on: 2017/9/12 下午3:52
 * description:
 */
public class MeFragment extends BaseFragment {


    @BindView(R.id.img_home)
    ImageView mHomebgMe;
    @BindView(R.id.motto_me)
    TextView mMottoMe;
    @BindView(R.id.userhead_me)
    CircleImageView mUserheadMe;
    @BindView(R.id.username_me)
    TextView mUsernameMe;
    @BindView(R.id.me_calender)
    LinearLayout mCalender;
    @BindView(R.id.me_weather)
    LinearLayout mWeather;
    @BindView(R.id.me_led)
    LinearLayout mLed;
    @BindView(R.id.me_torch)
    LinearLayout mTorch;
    @BindView(R.id.me_code)
    LinearLayout mCode;
    @BindView(R.id.me_setting)
    LinearLayout mSetting;
    @BindView(R.id.fab)
    FloatingActionButton mFab;


    public static MeFragment getInstance() {
        MeFragment fragment = new MeFragment();
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_me;
    }

    @Override
    public void initView() {
        initUserInfo();
    }

    private void initUserInfo() {
        String username = (String) SPUtils.get(getContext(), Constant.USER_NAME, "");
        String userheader = (String) SPUtils.get(getContext(), Constant.USER_HEADER, "");
        String usergeyan = (String) SPUtils.get(getContext(), Constant.USER_GEYAN, "我愿做你世界的太阳，给你温暖");

        if (!TextUtils.isEmpty(username)) {
            mUsernameMe.setText(username);
        }
        if (!TextUtils.isEmpty(userheader)) {
            Glide.with(getContext()).load(new File(userheader)).into(mUserheadMe);
        }
        if (!TextUtils.isEmpty(usergeyan)) {
            mMottoMe.setText(usergeyan);
        }
    }


    @Override
    public void managerArguments() {

    }

    @OnClick({R.id.me_calender, R.id.me_weather, R.id.me_led, R.id.me_torch, R.id.me_code, R.id.me_setting, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.me_calender:
                startActivity(new Intent(getContext(), CalenderActivity.class));
                break;
            case R.id.me_weather:
                startActivity(new Intent(getContext(), WeatherActivity.class));
                break;
            case R.id.me_led:
                startActivity(new Intent(getContext(), LEDActivity.class));
                break;
            case R.id.me_torch:
                startActivity(new Intent(getContext(), FlashActivity.class));
                break;
            case R.id.me_code:
                startActivity(new Intent(getContext(), ZxingActivity.class));
                break;
            case R.id.me_setting:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            case R.id.fab:
                startActivity(new Intent(getContext(), UserInfoActivity.class));
                break;
            default:
                break;
        }
    }
}
