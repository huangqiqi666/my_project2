package com.hikvision.pbg.sitecodeprj.common;

public class Result<T> {
    public static String RECODE_SUCCESS = "0";
    public static String RECODE_ERROR = "-1";
    public static String FAIL_TYPE = "-2";
    public static String NO_AUTH_TYPE = "-3";
    public static String SUCCESS_MSG = "success";
    private String msg = "failed";
    private String code = RECODE_ERROR;
    private Object data;

    public Result() {
    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int type, String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int type, String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public static <T> Result<T> success(T t) {
        return new Result<>(0, "0", "success", t);
    }

    public static <T> Result<T> fail(String code, String message) {
        return new Result(code, message);
    }

    public static <T> Result<T> error(String code, String message) {
        return new Result(code, message);
    }

    public static String getRecodeSuccess() {
        return RECODE_SUCCESS;
    }

    public static void setRecodeSuccess(String recodeSuccess) {
        RECODE_SUCCESS = recodeSuccess;
    }

    public static String getRecodeError() {
        return RECODE_ERROR;
    }

    public static void setRecodeError(String recodeError) {
        RECODE_ERROR = recodeError;
    }

    public static String getFailType() {
        return FAIL_TYPE;
    }

    public static void setFailType(String failType) {
        FAIL_TYPE = failType;
    }

    public static String getNoAuthType() {
        return NO_AUTH_TYPE;
    }

    public static void setNoAuthType(String noAuthType) {
        NO_AUTH_TYPE = noAuthType;
    }

    public static String getSuccessMsg() {
        return SUCCESS_MSG;
    }

    public static void setSuccessMsg(String successMsg) {
        SUCCESS_MSG = successMsg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}