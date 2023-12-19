package com.yangmao.yangoj.model.DTO.question;

import lombok.Data;

/**
 * 题目限制
 */
@Data
public class JudgeConfig {
    /**
     * 时间限制(ms)
     */
    private long timeLimit;
    /**
     * 内存限制(kB)
     */
    private long memoryLimit;
//    /**
//     * 堆栈限制(kB)
//     */
//    private long stackLimit;

}
