package com.lucky.lib.social.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.util.LinkedHashMap;

/**
 * @创建时间:2018/5/14 15:04.
 * @负责人: 雷兴国
 * @功能描述: sdk配置文件
 */
public final class PlatformConfig {
    private static LinkedHashMap<PlatformType, Platform> platforms = new LinkedHashMap();

    //注册平台
    private static void register() {
        platforms.put(PlatformType.WX, new WX(PlatformType.WX));
        platforms.put(PlatformType.WX_TIMELINE, new WX(PlatformType.WX_TIMELINE));
    }

    //初始化
    public static void init(Context context) {
        register();
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getApplicationContext().getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (appInfo != null && appInfo.metaData != null) {
            //获取配置文件中的微信id
            if (!TextUtils.isEmpty((appInfo.metaData.getString("WEIXIN_ID", "")))) {
                setWX(appInfo.metaData.getString("WEIXIN_ID", ""));
            }
        } else {
            //没有注册的话，抛出异常
            throw new IllegalStateException("error load social config!");
        }
    }

    private static void setWX(String appId) {
        WX wx = (WX) platforms.get(PlatformType.WX);
        wx.appId = appId;

        WX wxTimeline = (WX) platforms.get(PlatformType.WX_TIMELINE);
        wxTimeline.appId = appId;
    }

    public static class WX extends Platform {
        public String appId;

        public WX(PlatformType type) {
            name = type;
        }
    }

    public static Platform getPlatformConfig(PlatformType name) {
        return platforms.get(name);
    }

    public static abstract class Platform {
        public PlatformType name;
    }
}
