分享sdk

1，集成到项目配置必要的权限和id

    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

    <!-- wx -->
    <meta-data
        android:name="WEIXIN_ID"
        android:value="WEIXIN_ID"/>//需要加入你申请到的id

2,在你的包名相应目录下新建一个wxapi目录，并在该wxapi目录下新增一个WXEntryActivity类，该类继承自com.lucky.social.wx.WXCallbackActivity

并配置到AndroidManifest

    <activity
        android:name="com.lucky.social.wx.WXCallbackActivity"
        android:exported="true"
        android:launchMode="singleTop"/>
    <activity
        android:name=".wxapi.WXEntryActivity"
        android:exported="true"/>

3，初始化sdk

    PlatformConfig.init(context);

4，使用

    private void testShare() {
        ShareWebMedia shareWebMedia = new ShareWebMedia();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.launcher);
        shareWebMedia.setWebPageUrl("http://mtest.luckincoffee.com/invited/register?activityNo=NR201712190003&inviteCode=L3KXR2UNaPVm4yAV_DXSfw==&secondfrom=1&title=%E4%B9%B0%E4%B8%80%E8%B5%A0%E4%B8%80%E5%8D%83%EF%BC%8C%E5%BF%AB%E6%9D%A5%E4%B9%B0%E5%95%8A%E6%88%91%E7%9A%84%E5%A4%A9&timestamp=1526350259856");
        shareWebMedia.setTitle("买一赠一千，快来买啊我的天");
        shareWebMedia.setDescription("买一赠一千，快来买啊啊啊啊");
        shareWebMedia.setThumb(bitmap);
        SocialApi.getInstance().doShare(this, PlatformType.WX, shareWebMedia, new ShareListener() {
            @Override
            public void onShareComplete(PlatformType platformType) {
                Log.i(TAG, "share success" + platformType.name());
            }

            @Override
            public void onShareError(PlatformType platformType, String errMsg) {
                Log.i(TAG, "share Error " + platformType.name() + errMsg);
            }

            @Override
            public void onShareCancel(PlatformType platformType) {
                Log.i(TAG, "share Cancel" + platformType.name());
            }
        });
    }

    private void testLogin() {
        SocialApi.getInstance().doAuthVerify(this, PlatformType.WX, new AuthListener() {
            @Override
            public void onAuthComplete(PlatformType platformType, UserModel userModel) {

            }

            @Override
            public void onAuthError(PlatformType platformType, String errMsg) {

            }

            @Override
            public void onAuthCancel(PlatformType platformType) {

            }
        });
    }

5，混淆相关

如果需要混淆代码，为了保证sdk的正常使用，需要在proguard.cfg加上下面两行配置：

-keep class com.tencent.mm.opensdk.** {

*;

}

-keep class com.tencent.wxop.** {

*;

}

-keep class com.tencent.mm.sdk.** {

*;

}
