package com.share.shamir.management.model;

import com.alibaba.fastjson.JSONObject;

public class ToString {
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}

