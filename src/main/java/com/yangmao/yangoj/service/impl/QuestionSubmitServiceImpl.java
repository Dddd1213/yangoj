package com.yangmao.yangoj.service.impl;

import com.yangmao.yangoj.common.enumeration.ErrorCode;
import com.yangmao.yangoj.common.enumeration.QuestionSubmitLanguageEnum;
import com.yangmao.yangoj.common.enumeration.QuestionSubmitStatusEnum;
import com.yangmao.yangoj.common.exception.BusinessException;
import com.yangmao.yangoj.model.DTO.questionSubmit.AddQuestionSubmitDTO;
import com.yangmao.yangoj.model.VO.QuestionVO;
import com.yangmao.yangoj.model.entity.QuestionSubmit;
import com.yangmao.yangoj.mapper.QuestionSubmitMapper;
import com.yangmao.yangoj.service.QuestionSubmitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangmao.yangoj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 题目提交表 服务实现类
 * </p>
 *
 * @author xiaoyangii
 * @since 2023-12-08
 */
@Service
@RequiredArgsConstructor
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit> implements QuestionSubmitService {

    private final UserService userService;

    @Override
    public long addQuestionSubmit(AddQuestionSubmitDTO addQuestionSubmitDTO, HttpServletRequest request) {

        //判断编程语言是否合法
        String language = addQuestionSubmitDTO.getLanguage();
        QuestionSubmitLanguageEnum anEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if(anEnum==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"暂不支持该语言");
        }

        QuestionSubmit questionSubmit = new QuestionSubmit();

        BeanUtils.copyProperties(addQuestionSubmitDTO, questionSubmit);
        questionSubmit.setUserId(userService.getLoginUser(request).getId());
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");

        boolean saveResult = this.save(questionSubmit);
        if(!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目提交失败");
        }

        return questionSubmit.getId();
    }
}
