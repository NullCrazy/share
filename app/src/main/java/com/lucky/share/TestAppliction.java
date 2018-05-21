package com.lucky.share;

import android.app.Application;

import com.lucky.lib.social.internal.PlatformConfig;

/**
 * @创建时间:2018/5/15 10:03.
 * @负责人: 雷兴国
 * @功能描述:
 */
public class TestAppliction extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化分享
        PlatformConfig.init(this);
    }
}
