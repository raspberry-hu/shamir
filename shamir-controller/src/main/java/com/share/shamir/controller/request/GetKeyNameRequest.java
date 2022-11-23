package com.share.shamir.controller.request;

import java.io.Serializable;

public class GetKeyNameRequest implements Serializable {
    private static final long serialVersionUID = -6802273698742294772L;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
