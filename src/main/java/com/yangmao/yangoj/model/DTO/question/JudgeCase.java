package com.yangmao.yangoj.model.DTO.question;

import lombok.Data;

/**
 * 输入输出用例
 */
@Data
public class JudgeCase {
    /**
     * 输入用例
     */
    private String input;

    /**
     * 输出用例
     */
    private String output;
}
