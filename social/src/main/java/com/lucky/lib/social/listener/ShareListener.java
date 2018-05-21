package com.lucky.lib.social.listener;

import com.lucky.lib.social.internal.PlatformType;

/**
 * @创建时间:2018/5/14 13:54.
 * @负责人: 雷兴国
 * @功能描述: 分享回调接口
 */
public interface ShareListener {
    void onShareComplete(PlatformType platformType);

    void onShareError(PlatformType platformType, String errMsg);

    void onShareCancel(PlatformType platformType);
}
