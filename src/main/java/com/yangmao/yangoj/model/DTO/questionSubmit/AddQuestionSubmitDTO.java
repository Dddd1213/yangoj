package com.yangmao.yangoj.model.DTO.questionSubmit;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddQuestionSubmitDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "题目id")
    private Long questionId;

    @ApiModelProperty(value = "编程语言")
    private String language;

    @ApiModelProperty(value = "提交代码")
    private String code;

}
