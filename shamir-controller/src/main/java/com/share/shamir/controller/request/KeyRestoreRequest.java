package com.share.shamir.controller.request;

import java.io.Serializable;

public class KeyRestoreRequest implements Serializable {
    private static final long serialVersionUID = 1123705237763423962L;
    private String keyName;

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
}
