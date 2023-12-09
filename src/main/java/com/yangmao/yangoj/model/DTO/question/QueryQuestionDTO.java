package com.yangmao.yangoj.model.DTO.question;

import com.yangmao.yangoj.model.DTO.common.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QueryQuestionDTO extends PageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "标签")
    private List<String> tags;

    @ApiModelProperty(value = "题目创建人")
    private Long userId;


}
