package com.share.shamir.controller.request;

import java.io.Serializable;

public class GetUserShamirKey implements Serializable {
    private static final long serialVersionUID = 3343520825514544913L;
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
