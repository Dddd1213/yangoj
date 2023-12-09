package com.yangmao.yangoj.common.enumeration;

public enum QuestionSubmitStatusEnum {
    //判题状态
    WAITING("待判题",0),
    RUNNING("判题中",1),
    SUCCEED("成功",2),
    FAILED("失败",3);
    private final String text;
    private final Integer value;
    QuestionSubmitStatusEnum(String text,Integer value){
        this.text=text;
        this.value=value;
    }
    public Integer getValue() {
        return value;
    }
    public String getText() {
        return text;
    }
}
