package com.share.shamir.controller.request;

import java.io.Serializable;

public class GetKeyNameRequest implements Serializable {
    private static final long serialVersionUID = -6802273698742294772L;
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
