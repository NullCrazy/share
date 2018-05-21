package com.lucky.lib.social.media;

/**
 * @创建时间:2018/5/14 18:21.
 * @负责人: 雷兴国
 * @功能描述:
 */
public class ShareTextMedia extends BaseShareMedia {
    private String text;
    private String title;
    private String actionUrl;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }
}
