package com.share.shamir.controller.response;

public class ResponseBuilder {

    public static <T> CommonResponse<T> success() {
        return new CommonResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> CommonResponse<T> success(T result) {
        return new CommonResponse<T>(ResponseCode.SUCCESS.getCode(), result);
    }

    public static <T> CommonResponse<T> success(String msg) {
        return new CommonResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> CommonResponse<T> error(String msg) {
        return new CommonResponse<T>(ResponseCode.ERROR.getCode(), msg);
    }

}
