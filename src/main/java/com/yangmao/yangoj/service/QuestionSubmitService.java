package com.yangmao.yangoj.service;

import com.yangmao.yangoj.model.DTO.questionSubmit.AddQuestionSubmitDTO;
import com.yangmao.yangoj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 题目提交表 服务类
 * </p>
 *
 * @author xiaoyangii
 * @since 2023-12-08
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    long addQuestionSubmit(AddQuestionSubmitDTO addQuestionSubmitDTO, HttpServletRequest request);
}
