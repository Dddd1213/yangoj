package com.yangmao.yangoj.judge.codesandbox.impl;

import com.yangmao.yangoj.annotation.SandboxLog;
import com.yangmao.yangoj.common.enumeration.QuestionSubmitStatusEnum;
import com.yangmao.yangoj.judge.codesandbox.CodeSandbox;
import com.yangmao.yangoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yangmao.yangoj.judge.codesandbox.model.ExecuteCodeResponse;
import org.springframework.stereotype.Component;

/**
 * 实际调用接口的沙箱
 */
@Component
public class RemoteCodeSandbox implements CodeSandbox {
    @Override
    @SandboxLog
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("使用远程代码沙箱.....");
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        return executeCodeResponse;
    }
}
