package com.yangmao.yangoj.model.VO;

import cn.hutool.json.JSONUtil;
import com.yangmao.yangoj.model.DTO.question.JudgeConfig;
import com.yangmao.yangoj.model.entity.Question;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Builder
@Data
public class QuestionVO implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;

    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容（背景、题目描述等）")
    private String content;

    @ApiModelProperty(value = "标签")
    private List<String> tags;

    @ApiModelProperty(value = "提交总数")
    private Integer submitNum;

    @ApiModelProperty(value = "题目通过数")
    private Integer acceptedNum;

    @ApiModelProperty(value = "判题配置（json数组）（timeLimit，memoryLimit） ")
    private JudgeConfig judgeConfig;

    @ApiModelProperty(value = "题目创建人")
    private Long userId;

    private Date createTime;

    private Date updateTime;

    /**
     * VO转包装类
     * @param questionVO
     * @return
     */
    public static Question voToObj(QuestionVO questionVO){
        if(ObjectUtils.isEmpty(questionVO)){
            return null;
        }

        Question question = new Question();
        BeanUtils.copyProperties(questionVO,question);
        //把不一样的手动转json格式String
//        private List<String> tags;
//        private JudgeConfig judgeConfig;
//        private String tags;
//        private String judgeConfig;
        List<String> voTags = questionVO.getTags();
        if(!CollectionUtils.isEmpty(voTags)){
            question.setTags(JSONUtil.toJsonStr(voTags));
        }
        JudgeConfig voJudgeConfig = questionVO.getJudgeConfig();
        if(!ObjectUtils.isEmpty(voJudgeConfig)){
            question.setJudgeConfig(JSONUtil.toJsonStr(voJudgeConfig));
        }
        return question;
    }

    /**
     * 包装类转VO
     * @param question
     * @return
     */
    public static QuestionVO objToVo(Question question){
        if(ObjectUtils.isEmpty(question)){
            return null;
        }
        QuestionVO questionVO = QuestionVO.builder().build();
        BeanUtils.copyProperties(question,questionVO);
        String tags = question.getTags();
        String judgeConfig = question.getJudgeConfig();
        List<String> voTags = JSONUtil.toList(tags, String.class);
        JudgeConfig voJudgeConfig = JSONUtil.toBean(judgeConfig, JudgeConfig.class);

        questionVO.setTags(voTags);
        questionVO.setJudgeConfig(voJudgeConfig);

        return questionVO;
    }

}
