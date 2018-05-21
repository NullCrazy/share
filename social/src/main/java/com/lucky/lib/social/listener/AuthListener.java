package com.lucky.lib.social.listener;

import com.lucky.lib.social.internal.PlatformType;
import com.lucky.lib.social.model.UserModel;

/**
 * @创建时间:2018/5/14 13:54.
 * @负责人: 雷兴国
 * @功能描述: 登录回调接口
 */
public interface AuthListener {
    void onAuthComplete(PlatformType platformType, UserModel userModel);

    void onAuthError(PlatformType platformType, String errMsg);

    void onAuthCancel(PlatformType platformType);
}
