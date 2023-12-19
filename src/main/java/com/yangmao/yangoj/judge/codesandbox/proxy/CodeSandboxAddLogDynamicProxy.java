package com.yangmao.yangoj.judge.codesandbox.proxy;

import com.yangmao.yangoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yangmao.yangoj.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class CodeSandboxAddLogDynamicProxy implements InvocationHandler {

    private Object target = null;

    public CodeSandboxAddLogDynamicProxy(Object target){
        this.target=target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        log.info("进入CodeSandboxAddLogProxy......");

        ExecuteCodeRequest executeCodeRequest = (ExecuteCodeRequest)args[0];
        log.info("代码沙箱請求信息: "+executeCodeRequest.toString());
        //执行目标方法
        Object res = method.invoke(target, args);
        ExecuteCodeResponse executeCodeResponse = (ExecuteCodeResponse)res;
        log.info("代码沙箱响应信息："+executeCodeResponse.toString());

        return res;
    }
}
