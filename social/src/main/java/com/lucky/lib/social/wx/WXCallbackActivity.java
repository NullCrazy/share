package com.lucky.lib.social.wx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lucky.lib.social.SocialApi;
import com.lucky.lib.social.internal.PlatformConfig;
import com.lucky.lib.social.internal.PlatformType;
import com.lucky.lib.social.internal.wx.WXHandler;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * @创建时间:2018/5/14 15:19.
 * @负责人: 雷兴国
 * @功能描述: 微信回调界面
 */
public class WXCallbackActivity extends Activity implements IWXAPIEventHandler {

    private WXHandler wxHandler = null;
    private WXHandler wxTimelineHandler = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wxHandler = (WXHandler) SocialApi.getInstance().getSSOHandler(PlatformType.WX);
        wxHandler.init(this, PlatformConfig.getPlatformConfig(PlatformType.WX));

        wxTimelineHandler = (WXHandler) SocialApi.getInstance().getSSOHandler(PlatformType.WX_TIMELINE);
        wxTimelineHandler.init(this, PlatformConfig.getPlatformConfig(PlatformType.WX_TIMELINE));

        wxHandler.mIWXAPI.handleIntent(this.getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        wxHandler = (WXHandler) SocialApi.getInstance().getSSOHandler(PlatformType.WX);
        wxHandler.init(this, PlatformConfig.getPlatformConfig(PlatformType.WX));

        wxTimelineHandler = (WXHandler) SocialApi.getInstance().getSSOHandler(PlatformType.WX_TIMELINE);
        wxTimelineHandler.init(this, PlatformConfig.getPlatformConfig(PlatformType.WX_TIMELINE));

        wxHandler.mIWXAPI.handleIntent(this.getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        this.wxTimelineHandler.wxEventHandler.onReq(baseReq);
        finish();
    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp != null) {
            try {
                this.wxTimelineHandler.wxEventHandler.onResp(baseResp);
                this.wxHandler.wxEventHandler.onResp(baseResp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        finish();
    }
}
