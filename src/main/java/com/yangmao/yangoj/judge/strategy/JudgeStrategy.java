package com.yangmao.yangoj.judge.strategy;

import com.yangmao.yangoj.model.DTO.questionSubmit.JudgeInfo;

/**
 * 判题策略
 */
public interface JudgeStrategy {

    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
