package com.hanshaoda.smallreader.utils;

import android.content.Context;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * author: hanshaoda
 * created on: 2017/10/9 下午4:48
 * description:
 */

public class ShareUtils {

    public static void showShare(Context context, String content) {
        OnekeyShare oks = new OnekeyShare();

        oks.disableSSOWhenAuthorize();//关闭sso授权
        oks.setTitle("笑话一则");//设置标题QQ、微信的使用
        oks.setTitleUrl(content);//标题的网络连接
        oks.setText("笑话一则：" + content + "\n-来自：小小分享");
        oks.setUrl(content);
        oks.setComment("太搞笑了");
        oks.setSite("小小分享");
        oks.setSiteUrl("hhtp://ocnyang.com");
        oks.show(context);//启动分享GUI
    }

    public static void showShare(Context context, String url, String title) {
//        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(title + "");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(url + "");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("趣图：" + title + "\n图片地址：" + url + "\n-来自：小秋魔盒");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url + "");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("太搞笑了，太逗了");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("小小分享");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://ocnyang.com");

        // 启动分享GUI
        oks.show(context);
    }
}
