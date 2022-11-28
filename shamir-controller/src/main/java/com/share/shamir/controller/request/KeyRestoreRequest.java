package com.share.shamir.controller.request;

import java.io.Serializable;

public class KeyRestoreRequest implements Serializable {
    private static final long serialVersionUID = 1123705237763423962L;
    private String keyName;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
}
