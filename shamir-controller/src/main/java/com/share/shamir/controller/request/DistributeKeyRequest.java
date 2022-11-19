package com.share.shamir.controller.request;

import java.io.Serializable;
import java.util.List;

public class DistributeKeyRequest implements Serializable {
    private static final long serialVersionUID = 5737856810821498215L;

    private String key;

    private List<Integer> users;

    private Integer min;

    private String keyName;

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
    public String getKey() {
        return key;
    }

    public void setkey(String key) {
        this.key = key;
    }

    public List<Integer> getUsers() {
        return users;
    }

    public void setUsers(List<Integer> users) {
        this.users = users;
    }
}
