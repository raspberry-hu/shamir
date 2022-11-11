package com.share.shamir.controller.response;

public class ResponseCode {

    public final static ResponseCode SUCCESS = new ResponseCode(200);
    public final static ResponseCode ERROR = new ResponseCode(500);

    private Integer code;

    /**
     * @param code
     */
    private ResponseCode(Integer code) {
        this.code = code;
    }



    /**
     * @return the code
     */
    public Integer getCode() {
        return this.code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(Integer code) {
        this.code = code;
    }

}