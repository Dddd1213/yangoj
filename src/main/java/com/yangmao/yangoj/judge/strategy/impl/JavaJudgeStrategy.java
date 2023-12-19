package com.yangmao.yangoj.judge.strategy.impl;

import cn.hutool.json.JSONUtil;
import com.yangmao.yangoj.common.enumeration.QuestionSubmitJudgeInfoEnum;
import com.yangmao.yangoj.judge.strategy.JudgeContext;
import com.yangmao.yangoj.judge.strategy.JudgeStrategy;
import com.yangmao.yangoj.model.DTO.question.JudgeCase;
import com.yangmao.yangoj.model.DTO.question.JudgeConfig;
import com.yangmao.yangoj.model.DTO.questionSubmit.JudgeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 默认判题策略
 */
@Component
@Slf4j
public class JavaJudgeStrategy implements JudgeStrategy {

    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {

        log.info("进入Java判题策略....");

        //先把judgeContext中的值都读出来
        JudgeInfo responseJudgeInfo = judgeContext.getResponseJudgeInfo();
        JudgeConfig questionJudgeConfig = judgeContext.getQuestionJudgeConfig();
        List<String> responseOutputList = judgeContext.getResponseOutputList();
        List<String> outputList = judgeContext.getOutputList();

        //判断题目限制
        long responseJudgeInfoTime = responseJudgeInfo.getTime();
        long responseJudgeInfoMemory = responseJudgeInfo.getMemory();
        long timeLimit = questionJudgeConfig.getTimeLimit();
        long memoryLimit = questionJudgeConfig.getMemoryLimit();

        //new需要返回的对象
        JudgeInfo strategyJudgeInfo = new JudgeInfo();
        strategyJudgeInfo.setTime(responseJudgeInfoTime);
        strategyJudgeInfo.setMemory(responseJudgeInfoMemory);

        //    根据沙箱的执行结果，设置题目的判断状态和信息
        QuestionSubmitJudgeInfoEnum judgeInfoEnum;
        //先判断输出结果和预期输出数量是否相等
        if(responseOutputList.size()!=outputList.size()){
            //todo:这里到底是沙箱运行异常还是用户代码错误
            judgeInfoEnum = QuestionSubmitJudgeInfoEnum.WRONG_ANSWER;
            strategyJudgeInfo.setMessage(judgeInfoEnum.getText());
            return strategyJudgeInfo;
        }
        //再判断输出结果和预期输出内容是否相同
        for(int i=0;i<responseOutputList.size();i++){
            String responseOutPut = responseOutputList.get(i);
            String output = outputList.get(i);
            if(!responseOutPut.equals(output)){
                judgeInfoEnum = QuestionSubmitJudgeInfoEnum.WRONG_ANSWER;
                strategyJudgeInfo.setMessage(judgeInfoEnum.getText());
                return strategyJudgeInfo;
            }
        }

        if(timeLimit<responseJudgeInfoTime){
            judgeInfoEnum = QuestionSubmitJudgeInfoEnum.TIME_LIMIT_EXCEEDED;
            strategyJudgeInfo.setMessage(judgeInfoEnum.getText());
            return strategyJudgeInfo;
        }
        if(memoryLimit<responseJudgeInfoMemory){
            judgeInfoEnum = QuestionSubmitJudgeInfoEnum.MEMORY_LIMIT_EXCEEDED;
            strategyJudgeInfo.setMessage(judgeInfoEnum.getText());
            return strategyJudgeInfo;
        }

        judgeInfoEnum = QuestionSubmitJudgeInfoEnum.ACCEPTED;
        strategyJudgeInfo.setMessage(judgeInfoEnum.getValue());
        return strategyJudgeInfo;
    }
}
