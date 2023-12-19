package com.yangmao.yangoj.judge.codesandbox.proxy;

import com.yangmao.yangoj.judge.codesandbox.CodeSandbox;
import com.yangmao.yangoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yangmao.yangoj.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeSandboxAddLogStaticProxy implements CodeSandbox {

    private CodeSandbox codeSandbox;

    public CodeSandboxAddLogStaticProxy(CodeSandbox codeSandbox){
        this.codeSandbox=codeSandbox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {

        log.info("进入CodeSandboxAddLogStaticProxy......");

        log.info("代码沙箱請求信息: "+executeCodeRequest.toString());
        //执行目标方法
        ExecuteCodeResponse res = codeSandbox.executeCode(executeCodeRequest);
        ExecuteCodeResponse executeCodeResponse = res;
        log.info("代码沙箱响应信息："+executeCodeResponse.toString());

        return res;
    }
}
