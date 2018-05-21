package com.lucky.lib.social.media;

import android.graphics.Bitmap;

/**
 * @创建时间:2018/5/14 18:22.
 * @负责人: 雷兴国
 * @功能描述:
 */
public class ShareWebMedia extends BaseShareMedia {

    private String webPageUrl;
    private String title;
    private String description;
    //图片分享大于10M不能分享成功
    private Bitmap thumb;

    public String getWebPageUrl() {
        return webPageUrl;
    }

    public void setWebPageUrl(String webPageUrl) {
        this.webPageUrl = webPageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getThumb() {
        return thumb;
    }

    public void setThumb(Bitmap thumb) {
        this.thumb = thumb;
    }
}
