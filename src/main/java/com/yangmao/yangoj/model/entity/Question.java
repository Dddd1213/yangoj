package com.yangmao.yangoj.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiaoyangii
 * @since 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("question")
@ApiModel(value="Question对象", description="")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容（背景、题目描述等）")
    private String content;

    @ApiModelProperty(value = "标签")
    private String tags;

    @ApiModelProperty(value = "答案")
    private String answer;

    @ApiModelProperty(value = "提交总数")
    private Integer submitNum;

    @ApiModelProperty(value = "题目通过数")
    private Integer acceptedNum;

    @ApiModelProperty(value = "判题配置（json数组）（timeLimit，memoryLimit） ")
    private String judgeConfig;

    @ApiModelProperty(value = "判题用例（json数组）（input，output）")
    private String judgeCase;

    @ApiModelProperty(value = "题目创建人")
    private Long userId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private Integer isDelete;


}
