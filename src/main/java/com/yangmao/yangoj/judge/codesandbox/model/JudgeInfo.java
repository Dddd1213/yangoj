package com.yangmao.yangoj.judge.codesandbox.model;

import lombok.Data;

/**
 * 判题信息
 */
@Data
public class JudgeInfo {

    /**
     * 程序执行信息(Enum)
     */
    private String message;
    /**
     * 消耗时间(ms)
     */
    private long time;
    /**
     * 消耗内存(kB)
     */
    private long memory;
}
