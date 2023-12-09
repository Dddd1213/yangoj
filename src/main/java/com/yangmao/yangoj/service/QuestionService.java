package com.yangmao.yangoj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangmao.yangoj.model.DTO.common.DeleteDTO;
import com.yangmao.yangoj.model.DTO.question.AddQuestionDTO;
import com.yangmao.yangoj.model.DTO.question.QueryQuestionDTO;
import com.yangmao.yangoj.model.VO.QuestionVO;
import com.yangmao.yangoj.model.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaoyangii
 * @since 2023-12-08
 */
public interface QuestionService extends IService<Question> {

    Long addQuestion(AddQuestionDTO addQuestionDTO, HttpServletRequest request);

    Boolean delete(DeleteDTO deleteDTO, HttpServletRequest request);

    Page<QuestionVO> getQuestionVOPage(QueryQuestionDTO queryQuestionDTO);
}
