package com.goyo.project.core;

/**
 * @author: JinYue
 * @ClassName: ResultGenerator 
 * @Description: 响应结果生成工具
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result<String> genSuccessResult() {
        return new Result<String>()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }
    public static Result<Object> genSuccessResultToObject() {
        return new Result<Object>()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }
    public static <T> Result<T> genSuccessResult(T data) {
        return new Result<T>()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }
    public static <T> Result<T> genSuccessResult(String message,T data) {
        return new Result<T>()
                .setCode(ResultCode.SUCCESS)
                .setMessage(message)
                .setData(data);
    }
    public static Result<String> genFailResult(String message) {
        return new Result<String>()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }
    public static Result<Object> genFailResultToObject(String message) {
        return new Result<Object>()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }
    public static <T> Result<T> genFailResult(String message,T data) {
        return new Result<T>()
                .setCode(ResultCode.FAIL)
                .setMessage(message)
                .setData(data);
    }
}
