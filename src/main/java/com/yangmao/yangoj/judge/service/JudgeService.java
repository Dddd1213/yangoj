package com.yangmao.yangoj.judge.service;

import com.yangmao.yangoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.yangmao.yangoj.model.VO.QuestionSubmitVO;
import com.yangmao.yangoj.model.entity.QuestionSubmit;

/**
 * 判题服务
 * @author 31067
 */
public interface JudgeService {

    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    QuestionSubmitVO doJudge(long questionSubmitId);
}
