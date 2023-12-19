package com.yangmao.yangoj.judge.codesandbox.impl;

import com.yangmao.yangoj.annotation.SandboxLog;
import com.yangmao.yangoj.judge.codesandbox.CodeSandbox;
import com.yangmao.yangoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yangmao.yangoj.judge.codesandbox.model.ExecuteCodeResponse;
import org.springframework.stereotype.Component;

/**
 * 使用第三方代码沙箱
 */
@Component
public class ThirdPartySandbox implements CodeSandbox {
    @Override
    @SandboxLog
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("使用第三方代码沙箱");
        return null;
    }
}
