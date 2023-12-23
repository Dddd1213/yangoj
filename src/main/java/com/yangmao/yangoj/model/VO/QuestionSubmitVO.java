package com.yangmao.yangoj.model.VO;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yangmao.yangoj.judge.codesandbox.model.JudgeInfo;
import com.yangmao.yangoj.model.entity.QuestionSubmit;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

//主要就改了judgeInfo的类型
@Builder
@Data
public class QuestionSubmitVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "题目id")
    private Long questionId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "编程语言")
    private String language;

    @ApiModelProperty(value = "提交代码")
    private String code;

    @ApiModelProperty(value = "判题状态 0-待判题，1-判题中，2-成功，3-失败")
    private Integer status;

    @ApiModelProperty(value = "判题信息（json）")
    private JudgeInfo judgeInfo;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private Integer isDelete;

//    public QuestionSubmitVO() {
//
//    }

    /**
     * 包装类转对象
     *
     * @param questionSubmitVO
     * @return
     */
    public static QuestionSubmit voToObj(QuestionSubmitVO questionSubmitVO) {
        if (questionSubmitVO == null) {
            return null;
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        BeanUtils.copyProperties(questionSubmitVO, questionSubmit);
        JudgeInfo judgeInfoObj = questionSubmitVO.getJudgeInfo();
        if (judgeInfoObj != null) {
            questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfoObj));
        }
        return questionSubmit;
    }

    /**
     * 对象转包装类
     *
     * @param questionSubmit
     * @return
     */
    public static QuestionSubmitVO objToVo(QuestionSubmit questionSubmit) {
        if (questionSubmit == null) {
            return null;
        }
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.builder().build();
        BeanUtils.copyProperties(questionSubmit, questionSubmitVO);
        String judgeInfoStr = questionSubmit.getJudgeInfo();
        questionSubmitVO.setJudgeInfo(JSONUtil.toBean(judgeInfoStr, JudgeInfo.class));
        return questionSubmitVO;
    }
}
