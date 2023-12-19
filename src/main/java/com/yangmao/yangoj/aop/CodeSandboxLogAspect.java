package com.yangmao.yangoj.aop;

import com.yangmao.yangoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yangmao.yangoj.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CodeSandboxLogAspect {
    @Pointcut("@annotation(com.yangmao.yangoj.annotation.SandboxLog)")
    public void sandboxLogPt(){}

    @Around("sandboxLogPt()")
    public Object SandboxLog(ProceedingJoinPoint point) throws Throwable {
        log.info("进入SandboxLogAspect...");
        Object[] args = point.getArgs();
        if(ObjectUtils.isEmpty(args)){
           return point.proceed();
        }
        ExecuteCodeRequest executeCodeRequest = (ExecuteCodeRequest)args[0];
        log.info("代码沙箱請求信息: "+executeCodeRequest.toString());
        Object res = point.proceed();
        ExecuteCodeResponse executeCodeResponse = (ExecuteCodeResponse)res;
        log.info("代码沙箱响应信息："+executeCodeResponse.toString());
        return res;
    }

}
