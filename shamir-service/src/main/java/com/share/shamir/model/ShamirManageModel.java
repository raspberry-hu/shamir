package com.share.shamir.model;

public class ShamirManageModel extends ToString{
    private static final long serialVersionUID = -8683261837580068195L;

    private Integer id;

    private String shamirID;

    private String shamirKey;

    private Integer shamirUserKey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShamirID() {
        return shamirID;
    }

    public void setShamirID(String shamirID) {
        this.shamirID = shamirID;
    }

    public String getShamirKey() {
        return shamirKey;
    }

    public void setShamirKey(String shamirKey) {
        this.shamirKey = shamirKey;
    }

    public Integer getShamirUserKey() {
        return shamirUserKey;
    }

    public void setShamirUserKey(Integer shamirUserKey) {
        this.shamirUserKey = shamirUserKey;
    }
}
