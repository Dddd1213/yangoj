package com.yangmao.yangoj.service.impl;

import com.yangmao.yangoj.common.enumeration.ErrorCode;
import com.yangmao.yangoj.common.enumeration.QuestionSubmitLanguageEnum;
import com.yangmao.yangoj.common.enumeration.QuestionSubmitStatusEnum;
import com.yangmao.yangoj.common.exception.BusinessException;
import com.yangmao.yangoj.judge.service.JudgeService;
import com.yangmao.yangoj.model.DTO.questionSubmit.AddQuestionSubmitDTO;
import com.yangmao.yangoj.model.VO.QuestionSubmitVO;
import com.yangmao.yangoj.model.VO.QuestionVO;
import com.yangmao.yangoj.model.entity.QuestionSubmit;
import com.yangmao.yangoj.mapper.QuestionSubmitMapper;
import com.yangmao.yangoj.service.QuestionSubmitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangmao.yangoj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * 题目提交表 服务实现类
 * </p>
 *
 * @author xiaoyangii
 * @since 2023-12-08
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit> implements QuestionSubmitService {

    @Resource
    private UserService userService;

    @Autowired
    @Lazy
    private JudgeService judgeService;


    @Override
    public long addQuestionSubmit(AddQuestionSubmitDTO addQuestionSubmitDTO, HttpServletRequest request) {

        //判断编程语言是否合法
        String language = addQuestionSubmitDTO.getLanguage();
        QuestionSubmitLanguageEnum anEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if(anEnum==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"暂不支持该语言");
        }

        //将题目保存到数据库中，status设waiting
        QuestionSubmit questionSubmit = new QuestionSubmit();

        BeanUtils.copyProperties(addQuestionSubmitDTO, questionSubmit);
        questionSubmit.setUserId(userService.getLoginUser(request).getId());
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");

        boolean saveResult = this.save(questionSubmit);
        if(!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目提交失败");
        }

        //调用判题功能(异步)
        CompletableFuture.runAsync(()->{
            judgeService.doJudge(questionSubmit.getId());
        });

        return questionSubmit.getId();
    }
}
