package com.lucky.lib.social.internal.wx;

import android.app.Activity;
import android.graphics.Bitmap;

import com.lucky.lib.social.internal.PlatformConfig;
import com.lucky.lib.social.internal.PlatformType;
import com.lucky.lib.social.internal.SSOHandler;
import com.lucky.lib.social.internal.util.BitmapUtils;
import com.lucky.lib.social.listener.AuthListener;
import com.lucky.lib.social.listener.ShareListener;
import com.lucky.lib.social.media.BaseShareMedia;
import com.lucky.lib.social.media.ShareTextMedia;
import com.lucky.lib.social.media.ShareWebMedia;
import com.lucky.lib.social.model.UserModel;
import com.lucky.lib.social.media.ShareImageMedia;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @创建时间:2018/5/14 15:44.
 * @负责人: 雷兴国
 * @功能描述: 微信分享/登录 相关实现
 */
public class WXHandler implements SSOHandler {
    private Activity mActivity;
    private AuthListener mAuthListener;
    private ShareListener mShareListener;
    public IWXAPI mIWXAPI;
    private PlatformConfig.WX mPlatForm = null;
    public static final String SCOPE = "snsapi_userinfo";
    public static final String STATE = "lucky_coffee_wx_login";

    @Override
    public void init(Activity activity, PlatformConfig.Platform platform) {
        mActivity = activity;
        mPlatForm = (PlatformConfig.WX) platform;
        mIWXAPI = WXAPIFactory.createWXAPI(mActivity.getApplicationContext(), mPlatForm.appId);
        mIWXAPI.registerApp(mPlatForm.appId);
    }

    @Override
    public void authorize(Activity activity, AuthListener authListener) {
        mActivity = activity;
        mAuthListener = authListener;

        SendAuth.Req req1 = new SendAuth.Req();
        req1.scope = SCOPE;
        req1.state = STATE;
        if (!mIWXAPI.sendReq(req1)) {
            mAuthListener.onAuthError(mPlatForm.name, "sendReq fail");
        }
    }

    private void onAuthCallback(SendAuth.Resp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if (mAuthListener != null) {
                    UserModel userModel = new UserModel();
                    userModel.setOpenId(resp.openId);
                    userModel.setCode(resp.code);
                    userModel.setState(resp.state);
                    mAuthListener.onAuthComplete(PlatformType.WX, userModel);
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                this.mAuthListener.onAuthCancel(PlatformType.WX);
                break;
            default:
                String errMsg = "wx auth error :" + resp.errCode + "->" + resp.errStr;
                mAuthListener.onAuthError(PlatformType.WX, errMsg);
                break;
        }
    }


    @Override
    public void share(Activity activity, BaseShareMedia baseShareMedia, ShareListener shareListener) {
        mActivity = activity;
        mShareListener = shareListener;
        String type = null;
        WXMediaMessage msg = new WXMediaMessage();

        if (baseShareMedia instanceof ShareWebMedia) {
            type = "webpage";

            ShareWebMedia shareWebMedia = ((ShareWebMedia) baseShareMedia);
            WXWebpageObject webPage = new WXWebpageObject();
            webPage.webpageUrl = shareWebMedia.getWebPageUrl();

            msg.title = shareWebMedia.getTitle();
            msg.description = shareWebMedia.getDescription();
            msg.thumbData = BitmapUtils.bitmap2Bytes(shareWebMedia.getThumb());
            msg.mediaObject = webPage;
        } else if (baseShareMedia instanceof ShareTextMedia) {
            type = "text";

            ShareTextMedia shareTextMedia = ((ShareTextMedia) baseShareMedia);
            WXTextObject wxTextObject = new WXTextObject();
            wxTextObject.text = shareTextMedia.getText();
            msg.mediaObject = wxTextObject;
            msg.description = shareTextMedia.getText();

        } else if (baseShareMedia instanceof ShareImageMedia) {
            type = "image";

            ShareImageMedia shareImageMedia = (ShareImageMedia) baseShareMedia;
            WXImageObject wxImageObject = new WXImageObject();
            //image限制10M
            wxImageObject.imageData = BitmapUtils.compressBitmap(BitmapUtils.bitmap2Bytes(shareImageMedia.getImage()), 10 * 1024 * 1024);
            msg.mediaObject = wxImageObject;
            Bitmap thumb = Bitmap.createScaledBitmap(shareImageMedia.getImage(), 200, 200, true);
            msg.thumbData = BitmapUtils.bitmap2Bytes(thumb);
            thumb.recycle();
        } else {
            shareListener.onShareError(mPlatForm.name, "weixin is not support this shareMedia");
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction(type);
        req.message = msg;
        if (mPlatForm.name == PlatformType.WX) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else if (mPlatForm.name == PlatformType.WX_TIMELINE) {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        if (!this.mIWXAPI.sendReq(req)) {
            mShareListener.onShareError(mPlatForm.name, "sendReq fail");
        }
    }

    private void onShareCallback(SendMessageToWX.Resp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                mShareListener.onShareComplete(mPlatForm.name);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                mShareListener.onShareCancel(mPlatForm.name);
                break;
            default:
                String errMsg = "weixin share error (" + resp.errCode + "):" + resp.errStr;
                mShareListener.onShareError(mPlatForm.name, errMsg);
                break;
        }
    }

    @Override
    public boolean isInstall() {
        return mIWXAPI.isWXAppInstalled();
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public IWXAPIEventHandler wxEventHandler = new IWXAPIEventHandler() {

        @Override
        public void onReq(BaseReq baseReq) {

        }

        @Override
        public void onResp(BaseResp baseResp) {
            int type = baseResp.getType();
            if (type == ConstantsAPI.COMMAND_SENDAUTH) {//登陆
                onAuthCallback((SendAuth.Resp) baseResp);
            }
            if (type == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {//分享回调
                onShareCallback((SendMessageToWX.Resp) baseResp);
            }
        }
    };
}
