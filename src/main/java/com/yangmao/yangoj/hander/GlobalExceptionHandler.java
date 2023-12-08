package com.yangmao.yangoj.hander;

import com.yangmao.yangoj.common.exception.BusinessException;
import com.yangmao.yangoj.common.result.Result;
import com.yangmao.yangoj.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BusinessException ex){
        log.error("异常信息：{}", ex.getMessage());
        return ResultUtils.error(ex.getCode(),ex.getMessage());
    }

}
