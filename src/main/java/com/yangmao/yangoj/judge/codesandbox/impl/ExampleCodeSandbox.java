package com.yangmao.yangoj.judge.codesandbox.impl;

import com.yangmao.yangoj.annotation.SandboxLog;
import com.yangmao.yangoj.common.enumeration.QuestionSubmitJudgeInfoEnum;
import com.yangmao.yangoj.common.enumeration.QuestionSubmitStatusEnum;
import com.yangmao.yangoj.judge.codesandbox.CodeSandbox;
import com.yangmao.yangoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yangmao.yangoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.yangmao.yangoj.model.DTO.questionSubmit.JudgeInfo;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.util.ArrayList;

/**
 * 假数据，为了跑通流程
 */
@Component
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    @SandboxLog
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("使用示例代码沙箱.....");

        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage("示例代码沙箱");
        judgeInfo.setTime(10);
        judgeInfo.setMemory(10);

        ArrayList<String> outPutList = new ArrayList<>();
        outPutList.add("3 4");

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        executeCodeResponse.setMessage(QuestionSubmitJudgeInfoEnum.ACCEPTED.getText());
        executeCodeResponse.setJudgeInfo(judgeInfo);
        executeCodeResponse.setOutputList(outPutList);
        return executeCodeResponse;
    }
}
