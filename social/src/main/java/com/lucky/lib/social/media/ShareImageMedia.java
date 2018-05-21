package com.lucky.lib.social.media;

import android.graphics.Bitmap;

/**
 * @创建时间:2018/5/14 18:19.
 * @负责人: 雷兴国
 * @功能描述: 分享的图片数据
 */
public class ShareImageMedia extends BaseShareMedia {
    private Bitmap image;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
