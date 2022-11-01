package com.share.shamir.controller.request;

import java.io.Serializable;

public class LoginRequest implements Serializable {
    private static final long serialVersionUID = -3467900899210988586L;

    private String userName;

    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
