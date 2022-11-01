package com.share.shamir.controller.request;

import java.io.Serializable;

public class QueryUserInfoRequest implements Serializable {
    private static final long serialVersionUID = -7407514594962511887L;

    private Integer id;

    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
