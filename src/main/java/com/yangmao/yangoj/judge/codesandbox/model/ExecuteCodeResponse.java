package com.yangmao.yangoj.judge.codesandbox.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yangmao.yangoj.model.DTO.question.JudgeConfig;
import com.yangmao.yangoj.model.DTO.questionSubmit.JudgeInfo;
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
public class ExecuteCodeResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *   判题用例（json数组）（output）
     */
    private List<String> outputList;

    /**
     * 判题信息（程序执行信息，消耗时间，消耗内存）
     */
    private JudgeInfo judgeInfo;

    /**
     * 程序执行状态
     */
    private Integer status;

    /**
     * 接口信息，比如沙箱发生异常
     */
    private String message;

}
