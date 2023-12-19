package com.yangmao.yangoj.judge.codesandbox.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExecuteCodeRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *   判题用例（json数组）（input）
     */
    private List<String> inputList;
    /**
     * 提交代码
     */
    private String code;

    /**
     * 编程语言
     */
    private String language;

}
