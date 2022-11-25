package com.share.shamir.controller.request;

import java.io.Serializable;

public class ApproveShamirKeyRequest implements Serializable {
    private static final long serialVersionUID = 4025434158940724899L;
    private Integer userId;
    private String shamirKeyName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getShamirKeyName() {
        return shamirKeyName;
    }

    public void setShamirKeyName(String shamirKeyName) {
        this.shamirKeyName = shamirKeyName;
    }
}
