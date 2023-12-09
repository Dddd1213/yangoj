package com.yangmao.yangoj.common.enumeration;

import org.apache.commons.lang3.ObjectUtils;

public enum QuestionSubmitLanguageEnum {
    //题目提交编程语言枚举
    JAVA("java","java"),
    CPLUSPLUS("c++","c++"),
    GOLANG("golang","golang");

    private final String text;
    private final String value;
    QuestionSubmitLanguageEnum(String text,String value){
        this.text=text;
        this.value=value;
    }
    public String getValue() {
        return value;
    }
    public String getText() {
        return text;
    }

    public static QuestionSubmitLanguageEnum getEnumByValue(String value){
        if(ObjectUtils.isEmpty(value)){
            return null;
        }
        for(QuestionSubmitLanguageEnum anEnum : QuestionSubmitLanguageEnum.values()){
            if(anEnum.value.equals(value)){
                return anEnum;
            }
        }
        return null;
    }

}
