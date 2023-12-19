package com.yangmao.yangoj.model.entity;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.yangmao.yangoj.model.DTO.questionSubmit.JudgeInfo;
import com.yangmao.yangoj.model.VO.QuestionSubmitVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * 题目提交表
 * </p>
 *
 * @author xiaoyangii
 * @since 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("question_submit")
@ApiModel(value="QuestionSubmit对象", description="题目提交表")
public class QuestionSubmit implements Serializable {

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
    private String judgeInfo;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private Integer isDelete;


}
