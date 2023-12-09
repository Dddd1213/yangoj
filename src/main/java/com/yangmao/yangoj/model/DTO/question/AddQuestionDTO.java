package com.yangmao.yangoj.model.DTO.question;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class AddQuestionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容（背景、题目描述等）")
    private String content;

    @ApiModelProperty(value = "标签")
    private List<String> tags;

    @ApiModelProperty(value = "答案")
    private String answer;

    @ApiModelProperty(value = "判题配置（json数组）（timeLimit，memoryLimit） ")
    private JudgeConfig judgeConfig;

    @ApiModelProperty(value = "判题用例（json数组）（input，output）")
    private List<JudgeCase> judgeCase;

}
