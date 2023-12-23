package com.yangmao.yangoj.judge.strategy;

import com.yangmao.yangoj.model.DTO.question.JudgeConfig;
import com.yangmao.yangoj.judge.codesandbox.model.JudgeInfo;
import lombok.Data;

import java.util.List;

/**
 * 上下文（用于定义在策略中要传递的参数)
 */
@Data
public class JudgeContext {
    /**
     * 程序执行信息，消耗时间，消耗内存等
     */
    private JudgeInfo responseJudgeInfo;

    /**
     * 题目的时间限制，内存限制等
     */
    private JudgeConfig questionJudgeConfig;

    /**
     * 程序执行输出
     */
    private List<String> responseOutputList;

    /**
     * 示例输出
     */
    private List<String> outputList;
}
