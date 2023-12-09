package com.yangmao.yangoj.controller;


import com.yangmao.yangoj.common.enumeration.ErrorCode;
import com.yangmao.yangoj.common.exception.BusinessException;
import com.yangmao.yangoj.common.result.Result;
import com.yangmao.yangoj.common.utils.ResultUtils;
import com.yangmao.yangoj.model.DTO.questionSubmit.AddQuestionSubmitDTO;
import com.yangmao.yangoj.service.QuestionSubmitService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/questionSubmit")
@Slf4j
@Api
public class QuestionSubmitController {

    @Autowired
    QuestionSubmitService questionSubmitService;

    /**
     * 提交题目
     */
    @PostMapping("/add")
    public Result<Long> addQuestionSubmit(@RequestBody AddQuestionSubmitDTO addQuestionSubmitDTO, HttpServletRequest request){
        if(addQuestionSubmitDTO==null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if(request==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long questionId= questionSubmitService.addQuestionSubmit(addQuestionSubmitDTO,request);

        return ResultUtils.success(questionId);
    }


}
