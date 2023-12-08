package com.yangmao.yangoj.common.utils;


import com.yangmao.yangoj.common.enumeration.ErrorCode;
import com.yangmao.yangoj.common.result.Result;

public class ResultUtils {
    /**
     * 成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data){
        return new Result<>(ErrorCode.SUCCESS.getCode(),"ok",data);
    }

    /**
     * 失败
     * @param errorCode
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(ErrorCode errorCode){
        return new Result<>(errorCode);
    }

    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code,message);
    }
}
