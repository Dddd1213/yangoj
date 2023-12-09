package com.yangmao.yangoj.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangmao.yangoj.common.enumeration.ErrorCode;
import com.yangmao.yangoj.common.exception.BusinessException;
import com.yangmao.yangoj.model.DTO.common.DeleteDTO;
import com.yangmao.yangoj.model.DTO.question.AddQuestionDTO;
import com.yangmao.yangoj.model.DTO.question.JudgeCase;
import com.yangmao.yangoj.model.DTO.question.JudgeConfig;
import com.yangmao.yangoj.model.DTO.question.QueryQuestionDTO;
import com.yangmao.yangoj.model.VO.QuestionVO;
import com.yangmao.yangoj.model.VO.UserVO;
import com.yangmao.yangoj.model.entity.Question;
import com.yangmao.yangoj.mapper.QuestionMapper;
import com.yangmao.yangoj.service.QuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangmao.yangoj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaoyangii
 * @since 2023-12-08
 */
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    private final UserService userService;
    
    @Override
    public Long addQuestion(AddQuestionDTO addQuestionDTO, HttpServletRequest request) {

        Question question = new Question();

        BeanUtils.copyProperties(addQuestionDTO,question);

        UserVO loginUser = userService.getLoginUser(request);
        question.setUserId(loginUser.getId());

        List<String> dtoTags = addQuestionDTO.getTags();
        if(!CollectionUtils.isEmpty(dtoTags)){
            question.setTags(JSONUtil.toJsonStr(dtoTags));
        }
        JudgeConfig dtoJudgeConfig = addQuestionDTO.getJudgeConfig();
        if(!ObjectUtils.isEmpty(dtoJudgeConfig)){
            question.setJudgeConfig(JSONUtil.toJsonStr(dtoJudgeConfig));
        }
        List<JudgeCase> dtoJudgeCase = addQuestionDTO.getJudgeCase();
        if(!CollectionUtils.isEmpty(dtoJudgeCase)){
            question.setJudgeCase(JSONUtil.toJsonStr(dtoJudgeCase));
        }

        boolean result = this.save(question);
        if(!result)
        {
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"添加题目失败，数据库错误");
        }
       
        return question.getId();
       
    }

    @Override
    public Boolean delete(DeleteDTO deleteDTO, HttpServletRequest request) {
        Long deleteId = deleteDTO.getId();
        UserVO loginUser = userService.getLoginUser(request);

        Question question = this.getById(deleteId);
        if(question==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        Long questionUserId = question.getUserId();
        //只有管理员和用户本人可以删除
        if(!userService.isAdmin(request)&&!questionUserId.equals(loginUser.getId())){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        boolean result = this.removeById(deleteId);
        if(!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"删除题目失败");
        }
        return true;
    }

    @Override
    public Page<QuestionVO> getQuestionVOPage(QueryQuestionDTO queryQuestionDTO) {
        Long id = queryQuestionDTO.getId();
        String title = queryQuestionDTO.getTitle();
        List<String> tags = queryQuestionDTO.getTags();
        Long userId = queryQuestionDTO.getUserId();

        //拼接查询条件
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<Question>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(id),Question::getId,id);
        queryWrapper.like(StringUtils.isNotBlank(title),Question::getTitle,title);
        if(!CollectionUtils.isEmpty(tags)){
            for(String tag:tags){
                queryWrapper.like(Question::getTags,"\""+tag+"\"");
            }
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId),Question::getUserId,userId);

        List<Question> questionList = baseMapper.selectList(queryWrapper);
        //用stream流把Question转成QuestionVO
        List<QuestionVO> questionVOList = questionList.stream()
                .map(QuestionVO::objToVo)
                .collect(Collectors.toList());

        //分页
        long current = queryQuestionDTO.getCurrent();
        long size = queryQuestionDTO.getPageSize();
        Page<QuestionVO> questionVOPage = new Page(current, size);
        questionVOPage.setRecords(questionVOList);

        return questionVOPage;
    }


}
