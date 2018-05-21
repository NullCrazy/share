package com.lucky.lib.social;

import android.app.Activity;

import com.lucky.lib.social.internal.PlatformConfig;
import com.lucky.lib.social.internal.PlatformType;
import com.lucky.lib.social.internal.SSOHandler;
import com.lucky.lib.social.listener.AuthListener;
import com.lucky.lib.social.listener.ShareListener;
import com.lucky.lib.social.media.BaseShareMedia;
import com.lucky.lib.social.internal.wx.WXHandler;

import java.util.WeakHashMap;

/**
 * @创建时间:2018/5/14 15:35.
 * @负责人: 雷兴国
 * @功能描述: 向外暴露的api
 */
public final class SocialApi {
    /**
     * 缓存Handler避免重复创建
     */
    private WeakHashMap<PlatformType, SSOHandler> ssoHandlerMap = new WeakHashMap<>();

    private SocialApi() {
    }

    /**
     * @return 暴露给外部的唯一实例
     */
    public static SocialApi getInstance() {
        return Holder.INSTANCE;
    }

    public SSOHandler getSSOHandler(PlatformType platformType) {
        if (ssoHandlerMap.get(platformType) == null) {
            if (platformType == PlatformType.WX || platformType == PlatformType.WX_TIMELINE) {
                ssoHandlerMap.put(platformType, new WXHandler());
            }
        }
        return ssoHandlerMap.get(platformType);
    }

    /**
     * 三方登录相关
     *
     * @param activity     调用的activity
     * @param platformType 传入的类型
     * @param authListener 回调接口
     */
    public void doAuthVerify(Activity activity, PlatformType platformType, AuthListener authListener) {
        SSOHandler ssoHandler = getSSOHandler(platformType);
        ssoHandler.init(activity, PlatformConfig.getPlatformConfig(platformType));
        ssoHandler.authorize(activity, authListener);
    }

    /**
     * 第三方分享
     *
     * @param activity
     * @param platformType  平台类型
     * @param shareMedia    分享的数据
     * @param shareListener 回调监听
     */
    public void doShare(Activity activity, PlatformType platformType, BaseShareMedia shareMedia, ShareListener shareListener) {
        SSOHandler ssoHandler = getSSOHandler(platformType);
        ssoHandler.init(activity, PlatformConfig.getPlatformConfig(platformType));
        ssoHandler.share(activity, shareMedia, shareListener);
    }

    /**
     * 是否安装此类型APP
     *
     * @param activity
     * @param platformType
     * @return
     */
    public boolean isInstall(Activity activity, PlatformType platformType) {
        SSOHandler ssoHandler = getSSOHandler(platformType);
        ssoHandler.init(activity, PlatformConfig.getPlatformConfig(platformType));
        return ssoHandler.isInstall();
    }

    private static class Holder {
        static final SocialApi INSTANCE = new SocialApi();
    }
}
