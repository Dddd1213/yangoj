package com.yangmao.yangoj.judge.service.impl;

import cn.hutool.json.JSONUtil;
import com.yangmao.yangoj.common.enumeration.ErrorCode;
import com.yangmao.yangoj.common.enumeration.QuestionSubmitStatusEnum;
import com.yangmao.yangoj.common.exception.BusinessException;
import com.yangmao.yangoj.judge.codesandbox.CodeSandbox;
import com.yangmao.yangoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yangmao.yangoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.yangmao.yangoj.judge.service.JudgeService;
import com.yangmao.yangoj.judge.strategy.JudgeContext;
import com.yangmao.yangoj.judge.strategy.JudgeStrategy;
import com.yangmao.yangoj.judge.strategy.JudgeStrategyFactory;
import com.yangmao.yangoj.model.DTO.question.JudgeCase;
import com.yangmao.yangoj.model.DTO.question.JudgeConfig;
import com.yangmao.yangoj.judge.codesandbox.model.JudgeInfo;
import com.yangmao.yangoj.model.VO.QuestionSubmitVO;
import com.yangmao.yangoj.model.entity.Question;
import com.yangmao.yangoj.model.entity.QuestionSubmit;
import com.yangmao.yangoj.service.QuestionService;
import com.yangmao.yangoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionSubmitService questionSubmitService;

    @Resource(name = "${codesandbox.type:example}"+"CodeSandbox")
    CodeSandbox codeSandbox;

//    @Resource(name = "${codesandbox.type:example}"+"JudgeStrategy")
//    JudgeStrategy judgeStrategy;

    @Override
    public QuestionSubmitVO doJudge(long questionSubmitId) {

//    传入题目提交的id，获取对应题目和提交信息
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if(questionSubmit==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"提交信息不存在");
        }

        //遗漏：检查题目状态是否为未判题
        Integer status = questionSubmit.getStatus();
        if(!status.equals(QuestionSubmitStatusEnum.WAITING.getValue())){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"该题正在判题中");
        }
        //遗漏：更新题目状态为判题中
        status = QuestionSubmitStatusEnum.RUNNING.getValue();
        QuestionSubmit updateQuestionSubmit = new QuestionSubmit();
        updateQuestionSubmit.setId(questionSubmitId);
        updateQuestionSubmit.setStatus(status);
        boolean update = questionSubmitService.updateById(updateQuestionSubmit);
        if(!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"更新判题状态失败");
        }

        //获取executeCodeRequest
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if(question==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目不存在");
        }
        String judgeCase = question.getJudgeCase();
//        JudgeCase judgeCase1 = JSONUtil.toBean(judgeCase, JudgeCase.class);
//        注意，judge是从question中获取的，是一对一对的input output组成的集合
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCase, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream()
                .map(JudgeCase::getInput)
                .collect(Collectors.toList());

        String code = questionSubmit.getCode();
        String language = questionSubmit.getLanguage();
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();

        //    调用沙箱，获取执行结果
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        List<String> responseOutputList = executeCodeResponse.getOutputList();
        JudgeInfo responseJudgeInfo = executeCodeResponse.getJudgeInfo();
        Integer responseStatus = executeCodeResponse.getStatus();
        String responseMessage = executeCodeResponse.getMessage();

        JudgeConfig questionJudgeConfig = JSONUtil.toBean(question.getJudgeConfig(), JudgeConfig.class);

        List<String> outputList = judgeCaseList.stream()
                .map(JudgeCase::getOutput)
                .collect(Collectors.toList());

        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setResponseJudgeInfo(responseJudgeInfo);
        judgeContext.setQuestionJudgeConfig(questionJudgeConfig);
        judgeContext.setResponseOutputList(responseOutputList);
        judgeContext.setOutputList(outputList);

        //调用判题策略
        JudgeStrategy judgeStrategy = JudgeStrategyFactory.newInstance(language);
        JudgeInfo strategyjudgeInfo = judgeStrategy.doJudge(judgeContext);

        //修改数据库中的判题结果
        updateQuestionSubmit = new QuestionSubmit();
        updateQuestionSubmit.setId(questionSubmitId);
        updateQuestionSubmit.setJudgeInfo(JSONUtil.toJsonStr(strategyjudgeInfo));
        //TODO:鱼皮这直接设置的成功，我改成用沙箱返回值的status
        updateQuestionSubmit.setStatus(responseStatus);

        update = questionSubmitService.updateById(updateQuestionSubmit);
        if(!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"判题结果更新失败");
        }
        //TODO：鱼皮为啥写的questionSubmitID
        QuestionSubmit questionSubmitResult = questionSubmitService.getById(questionSubmitId);

        QuestionSubmitVO questionSubmitResultVO = QuestionSubmitVO.objToVo(questionSubmitResult);

        return questionSubmitResultVO;
    }
}
