package com.lucky.share;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.lucky.lib.social.SocialApi;
import com.lucky.lib.social.internal.PlatformType;
import com.lucky.lib.social.listener.AuthListener;
import com.lucky.lib.social.listener.ShareListener;
import com.lucky.lib.social.media.ShareWebMedia;
import com.lucky.lib.social.model.UserModel;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_test_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testShare();
            }
        });

        findViewById(R.id.btn_test_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testLogin();
            }
        });
    }

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
}
