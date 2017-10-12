package com.hanshaoda.smallreader.modules.start.welcome;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.cleveroad.slidingtutorial.Direction;
import com.cleveroad.slidingtutorial.IndicatorOptions;
import com.cleveroad.slidingtutorial.OnTutorialPageChangeListener;
import com.cleveroad.slidingtutorial.PageOptions;
import com.cleveroad.slidingtutorial.TransformItem;
import com.cleveroad.slidingtutorial.TutorialFragment;
import com.cleveroad.slidingtutorial.TutorialOptions;
import com.cleveroad.slidingtutorial.TutorialPageProvider;
import com.cleveroad.slidingtutorial.TutorialSupportFragment;
import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.config.Constant;
import com.hanshaoda.smallreader.modules.start.SplashActivity;
import com.hanshaoda.smallreader.utils.SPUtils;

/**
 * author: hanshaoda
 * created on: 2017/9/14 下午12:22
 * description:
 */
public class CustomTutorialSupportFragment extends TutorialFragment implements OnTutorialPageChangeListener {

    private static final String TAG = "CustomTutorialSFragment";
    private static final int TOTAL_PAGES = 3;
    private static final int ACTUAL_PAGES_COUNT = 3;

    private final View.OnClickListener mOnSkipClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(getActivity(), SplashActivity.class));
            SPUtils.put(getActivity(), Constant.FIRST_OPEN, true);
            getActivity().finish();
        }
    };

    private final TutorialPageProvider mTutorialPageOptionsProvider = new TutorialPageProvider() {
        @NonNull
        @Override
        public PageOptions providePage(int position) {
            @LayoutRes int pageLayoutResId;
            TransformItem[] tutorialItems;
            position %= ACTUAL_PAGES_COUNT;
            switch (position) {
                case 0:
                    pageLayoutResId = R.layout.fragment_page_first;
                    tutorialItems = new TransformItem[]{

                            TransformItem.create(R.id.ivFirstImage, Direction.LEFT_TO_RIGHT, 0.2f),
                            TransformItem.create(R.id.ivSecondImage, Direction.RIGHT_TO_LEFT, 0.2f),
                            TransformItem.create(R.id.ivThirdImage, Direction.LEFT_TO_RIGHT, 0.2f),
                            TransformItem.create(R.id.ivFourthImage, Direction.LEFT_TO_RIGHT, 0.2f),
                            TransformItem.create(R.id.ivFifthImage, Direction.LEFT_TO_RIGHT, 0.2f),
                            TransformItem.create(R.id.ivSixthImage, Direction.LEFT_TO_RIGHT, 0.2f),
                            TransformItem.create(R.id.ivSeventhImage, Direction.LEFT_TO_RIGHT, 0.2f),
                            TransformItem.create(R.id.ivEighthImage, Direction.LEFT_TO_RIGHT, 0.2f),

                    };
                    break;
                case 1:
                    pageLayoutResId = R.layout.fragment_page_second;
                    tutorialItems = new TransformItem[]{

                            TransformItem.create(R.id.ivFirstImage, Direction.LEFT_TO_RIGHT, 0.2f),
                            TransformItem.create(R.id.ivSecondImage, Direction.RIGHT_TO_LEFT, 0.2f),
                            TransformItem.create(R.id.ivThirdImage, Direction.LEFT_TO_RIGHT, 0.2f),
                            TransformItem.create(R.id.ivFourthImage, Direction.RIGHT_TO_LEFT, 0.2f),
                            TransformItem.create(R.id.ivFifthImage, Direction.LEFT_TO_RIGHT, 0.2f),
                            TransformItem.create(R.id.ivSixthImage, Direction.RIGHT_TO_LEFT, 0.2f),
                            TransformItem.create(R.id.ivSeventhImage, Direction.LEFT_TO_RIGHT, 0.2f),
                            TransformItem.create(R.id.ivEighthImage, Direction.RIGHT_TO_LEFT, 0.2f),
                    };
                    break;
                case 2:
                    pageLayoutResId = R.layout.fragment_page_third;
                    tutorialItems = new TransformItem[]{

                            TransformItem.create(R.id.ivFirstImage, Direction.LEFT_TO_RIGHT, 0.2f),
                            TransformItem.create(R.id.ivSecondImage, Direction.RIGHT_TO_LEFT, 0.2f),
                            TransformItem.create(R.id.ivThirdImage, Direction.LEFT_TO_RIGHT, 0.2f),
                            TransformItem.create(R.id.ivFourthImage, Direction.RIGHT_TO_LEFT, 0.2f),
                            TransformItem.create(R.id.ivFifthImage, Direction.LEFT_TO_RIGHT, 0.2f),
                            TransformItem.create(R.id.ivSixthImage, Direction.RIGHT_TO_LEFT, 0.2f),
                            TransformItem.create(R.id.ivSeventhImage, Direction.LEFT_TO_RIGHT, 0.2f),
                            TransformItem.create(R.id.ivEighthImage, Direction.RIGHT_TO_LEFT, 0.2f),
                    };
                    break;
                default:
                    throw new IllegalArgumentException("Unknown position" + position);
            }
            return PageOptions.create(pageLayoutResId, position, tutorialItems);
        }
    };
    private final TutorialPageProvider<Fragment> mTutorialPageProvider = new TutorialPageProvider<Fragment>() {
        @NonNull
        @Override
        public Fragment providePage(int position) {
            position %= ACTUAL_PAGES_COUNT;
            switch (position) {
                case 0:
                    return new FirstCustomPageSupportFragment();
                case 1:
                    return new SecondCustomPageSupportFragment();
                case 2:
                    return new ThirdCustomPageSupportFragment();
                default: {
                    throw new IllegalArgumentException("Unknown position: " + position);
                }
            }
        }
    };

    private int[] pagesColor;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (pagesColor == null) {
            pagesColor = new int[]{
                    ContextCompat.getColor(getContext(), android.R.color.holo_orange_dark),
                    ContextCompat.getColor(getContext(), android.R.color.holo_red_dark),
                    ContextCompat.getColor(getContext(), android.R.color.holo_purple),
                    ContextCompat.getColor(getContext(), android.R.color.holo_green_dark),
                    ContextCompat.getColor(getContext(), android.R.color.holo_blue_dark),
                    ContextCompat.getColor(getContext(), android.R.color.darker_gray),
            };
        }
        addOnTutorialPageChangeListener(this);

    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected TutorialOptions provideTutorialOptions() {

        return newTutorialOptionsBuilder(getContext())
                .setUseInfiniteScroll(true)
                .setPagesColors(pagesColor)
                .setPagesCount(TOTAL_PAGES)
                .setTutorialPageProvider(mTutorialPageOptionsProvider)
                .setIndicatorOptions(IndicatorOptions.newBuilder(getContext())
                        .setElementSizeRes(R.dimen.indicator_size)
                        .setElementSpacingRes(R.dimen.indicator_size)
                        .setElementColorRes(android.R.color.darker_gray)
                        .setSelectedElementColor(Color.LTGRAY)
                        .setRenderer(DrawableRenderer.create(getContext()))
                        .build()).onSkipClickListener(mOnSkipClickListener)
                .build();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.custom_tutorial_layout;
    }

    @Override
    public void onPageChanged(int position) {

        if (position == TutorialSupportFragment.EMPTY_FRAGMENT_POSITION) {
            Log.i(TAG, "onPageChanged:Empty fragment is visible");
        }
    }
}
