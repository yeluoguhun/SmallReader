package com.hanshaoda.smallreader.modules.me;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.hanshaoda.smallreader.R;
import com.hanshaoda.smallreader.base.BaseCommonActivity;
import com.hanshaoda.smallreader.config.Constant;
import com.hanshaoda.smallreader.model.RefreshMeFragmentEvent;
import com.hanshaoda.smallreader.utils.SPUtils;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * author: hanshaoda
 * created on: 2017/9/14 下午5:27
 * description:
 */
public class UserInfoActivity extends BaseCommonActivity implements Toolbar.OnMenuItemClickListener, InvokeListener, TakePhoto.TakeResultListener {

    public static final String KEY_SELECTED_AREA = "KEY_SELECTED_AREA";
    public final static int mMessageFlag = 0x1010;
    @BindView(R.id.toolbar_user_info)
    Toolbar mToolbarUserInfo;
    @BindView(R.id.user_head_img_user_info)
    CircleImageView mUserHeadImgUserInfo;
    @BindView(R.id.user_name_user_info)
    TextInputEditText mUserNameUserInfo;
    @BindView(R.id.radio_btn_man)
    RadioButton mRadioBtnMan;
    @BindView(R.id.radio_btn_woman)
    RadioButton mRadioBtnWoman;
    @BindView(R.id.radio_man_user_info)
    RadioGroup mRadioManUserInfo;
    @BindView(R.id.user_motto_user_info)
    TextInputEditText mUserMottoUserInfo;
    @BindView(R.id.stars_spinner_user_info)
    Spinner mStarsSpinnerUserInfo;
    @BindView(R.id.user_address_user_info)
    TextInputEditText mUserAddressUserInfo;
    private CharSequence mUserHeaderPath;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_userinfo);
    }

    @Override
    public void initView() {

        initToolbar();
    }

    private void initToolbar() {
        mToolbarUserInfo.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        mToolbarUserInfo.inflateMenu(R.menu.userinfo_menu);
        mToolbarUserInfo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mToolbarUserInfo.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            Editable name = mUserNameUserInfo.getText();
            if (TextUtils.isEmpty(name)) {
                mUserNameUserInfo.setError("姓名不能为空");
                return false;
            }

            if (name.length() >= 3) {
                SPUtils.put(this, Constant.USER_NAME, name.toString());
            } else {
                mUserNameUserInfo.setError("长度不能小于3");
                return false;
            }

            Editable motto = mUserMottoUserInfo.getText();
            if (!TextUtils.isEmpty(motto)) {
                SPUtils.put(this, Constant.USER_MOTTO, motto.toString());
            } else {
                SPUtils.remove(this, Constant.USER_MOTTO);
            }

            SPUtils.put(this, Constant.USER_SEX, mRadioManUserInfo.getCheckedRadioButtonId()
                    == R.id.radio_btn_man ? true : false);
            SPUtils.put(this, Constant.USER_STAR, getStarSelect());

            if (!TextUtils.isEmpty(mUserAddressUserInfo.getText())) {
                SPUtils.put(this, Constant.USER_ADDRESS, mUserAddressUserInfo.getText().toString());
            }
        } else {
            if (TextUtils.isEmpty(mUserHeaderPath)) ;
            else {
                SPUtils.put(this, Constant.USER_HEADER, mUserHeaderPath);
            }
            EventBus.getDefault().post(new RefreshMeFragmentEvent(0x1000));
        }
        return false;
    }


    @OnClick({R.id.user_head_img_user_info, R.id.user_address_user_info})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.user_head_img_user_info:
                showTakePhotoDialog();
                break;
            case R.id.user_address_user_info:
                break;
        }
    }

    private void showTakePhotoDialog() {
        final String items[] = {"拍照", "相册"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择头像");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                takeOrPick(i == 0 ? true : false);
            }
        });
    }

    private void takeOrPick(boolean b) {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        Uri uri = Uri.fromFile(file);
        TakePhoto takePhoto = getTakePhoto();

        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);

        if (b) {
            if (true) {
                takePhoto.onPickFromCaptureWithCrop(uri, getCropOptions());
            } else {
                takePhoto.onPickFromCapture(uri);
            }
        } else {

            int limit = 1;
            if (limit > 1) {
                if (true) {
                    takePhoto.onPickMultipleWithCrop(limit, getCropOptions());
                } else {
                    takePhoto.onPickMultiple(limit);
                }
                return;
            }
            if (false) {
                if (true) {
                    takePhoto.onPickFromCaptureWithCrop(uri, getCropOptions());
                } else {
                    takePhoto.onPickFromCapture(uri);
                }
            }
        }
    }

    private CropOptions getCropOptions() {
        int height = 100;
        int width = 100;

        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(width).setAspectY(height);

        builder.setWithOwnCrop(false);

        return builder.create();
    }

    /**
     * 拍照相关配置
     */
    private void configTakePhotoOption(TakePhoto takePhoto) {

        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        if (false) {
            builder.setWithOwnGallery(true);
        }
        if (true) {
            builder.setCorrectImage(true);
        }
        takePhoto.setTakePhotoOptions(builder.create());
    }

    private void configCompress(TakePhoto takePhoto) {

        int maxSize = 102400;
        int width = 800;
        int height = 800;
        boolean showProgress = true;
        boolean enableRawFile = true;
        CompressConfig config;
        if (false) {
            config = new CompressConfig.Builder()
                    .setMaxSize(maxSize)
                    .setMaxPixel(width >= height ? width : height)
                    .enableReserveRaw(enableRawFile)
                    .create();
        } else {
            LubanOptions options = new LubanOptions.Builder()
                    .setMaxHeight(height)
                    .setMaxWidth(width)
                    .setMaxSize(maxSize)
                    .create();
            config = CompressConfig.ofLuban(options);
            config.enableReserveRaw(enableRawFile);
        }
        takePhoto.onEnableCompress(config, showProgress);
    }

    private TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, (TakePhoto.TakeResultListener) this));
        }
        return null;
    }

    private Object getStarSelect() {
        long selectedItemId = mStarsSpinnerUserInfo.getSelectedItemId();
        String[] stringArray = getResources().getStringArray(R.array.arrays_constellation);
        return stringArray[(int) selectedItemId];
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);

        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public void takeSuccess(TResult result) {
        File file = new File(result.getImages().get(0).getCompressPath());
        mUserHeaderPath = file.getAbsolutePath();

        Message msg = mHandler.obtainMessage();
        msg.what = mMessageFlag;
        msg.obj = file;
        mHandler.sendMessage(msg);
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case mMessageFlag:
                    Glide.with(UserInfoActivity.this).load(((File) msg.obj)).into(mUserHeadImgUserInfo);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
