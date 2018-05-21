package com.lucky.lib.social.model;

/**
 * @创建时间:2018/5/14 14:04.
 * @负责人: 雷兴国
 * @功能描述: 用户基本信息
 */
public class UserModel {
    private String code;
    private String openId;
    private String state;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
