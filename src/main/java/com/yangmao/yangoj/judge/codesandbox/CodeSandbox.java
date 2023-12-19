package com.yangmao.yangoj.judge.codesandbox;

import com.yangmao.yangoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yangmao.yangoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口
 */
public interface CodeSandbox {
    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
