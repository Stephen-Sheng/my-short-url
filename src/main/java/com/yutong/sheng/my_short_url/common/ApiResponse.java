package com.yutong.sheng.my_short_url.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApiResponse<T> implements Serializable {

    public static final int SUCCESS_CODE = 200;

    private int code;

    private String msg;

    private T data;

    public ApiResponse() {

    }

    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(String msg) {
        this.msg = msg;
    }

    public ApiResponse(int code, String msg, T date) {
        this.code = code;
        this.msg = msg;
        this.data = date;
    }

    public static <T> ApiResponse<T> ok() {
        return new ApiResponse<>();
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(data);
    }


}
