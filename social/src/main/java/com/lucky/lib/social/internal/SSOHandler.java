package com.lucky.lib.social.internal;

import android.app.Activity;

import com.lucky.lib.social.listener.AuthListener;
import com.lucky.lib.social.listener.ShareListener;
import com.lucky.lib.social.media.BaseShareMedia;

/**
 * @创建时间:2018/5/14 15:01.
 * @负责人: 雷兴国
 */
public interface SSOHandler {

    /**
     * 初始化
     *
     * @param activity
     * @param platform
     */
    void init(Activity activity, PlatformConfig.Platform platform);

    /**
     * 登录
     *
     * @param activity
     * @param authListener
     */
    void authorize(Activity activity, AuthListener authListener);

    /**
     * 分享
     *
     * @param activity
     * @param baseShareMedia
     * @param shareListener
     */
    void share(Activity activity, BaseShareMedia baseShareMedia, ShareListener shareListener);

    /**
     * 是否安装
     *
     * @return
     */
    boolean isInstall();
}
