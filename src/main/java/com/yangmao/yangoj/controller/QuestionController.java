package com.yangmao.yangoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangmao.yangoj.common.enumeration.ErrorCode;
import com.yangmao.yangoj.common.exception.BusinessException;
import com.yangmao.yangoj.common.result.Result;
import com.yangmao.yangoj.common.utils.ResultUtils;
import com.yangmao.yangoj.model.DTO.common.DeleteDTO;
import com.yangmao.yangoj.model.DTO.question.AddQuestionDTO;
import com.yangmao.yangoj.model.DTO.question.QueryQuestionDTO;
import com.yangmao.yangoj.model.VO.QuestionVO;
import com.yangmao.yangoj.model.entity.Question;
import com.yangmao.yangoj.service.QuestionService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/question")
@Slf4j
@Api
public class QuestionController {

    @Autowired
    QuestionService questionService;

    /**
     * 新增
     * @param addQuestionDTO
     * @param request
     * @return
     */
    @PostMapping("/add")
    public Result<Long> addQuestion(@RequestBody AddQuestionDTO addQuestionDTO, HttpServletRequest request){
        if(addQuestionDTO==null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if(request==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long questionId = questionService.addQuestion(addQuestionDTO,request);

        return ResultUtils.success(questionId);
    }

    /**
     * 删除
     * @param deleteDTO
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public Result<Boolean> deleteQuestion(@RequestBody DeleteDTO deleteDTO, HttpServletRequest request){

        if(deleteDTO==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if(request==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean result = questionService.delete(deleteDTO,request);

        return ResultUtils.success(result);
    }

    /**
     * 分页查询
     *
     * @param queryQuestionDTO
     * @param request
     * @return
     */
    @PostMapping("/getQuestionVOPage")
    public Result<Page<QuestionVO>> getQuestionVOPage(@RequestBody QueryQuestionDTO queryQuestionDTO,
                                                       HttpServletRequest request) {
        if (queryQuestionDTO == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if(request==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<QuestionVO> questionPage = questionService.getQuestionVOPage(queryQuestionDTO);
        if(CollectionUtils.isEmpty(questionPage.getRecords())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求信息不存在");
        }

        return ResultUtils.success(questionPage);
    }


}
