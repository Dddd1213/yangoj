package com.yangmao.yangoj.common.enumeration;

import com.yangmao.yangoj.judge.codesandbox.impl.RemoteCodeSandbox;
import org.apache.commons.lang3.ObjectUtils;

public enum CodeSandboxEnum {

    //沙箱枚举类
    EXAMPLE("example","example"),
    REMOTE("remote","remote"),
    THIRDPARTY("thirdParty","thirdParty");

    private final String text;
    private final String value;

    CodeSandboxEnum(String text,String value){
        this.text=text;
        this.value=value;
    }
    public String getValue() {
        return value;
    }
    public String getText() {
        return text;
    }

    public static CodeSandboxEnum getEnumByValue(String value){
        if(ObjectUtils.isEmpty(value)){
            return null;
        }
        for(CodeSandboxEnum anEnum : CodeSandboxEnum.values()){
            if(anEnum.value.equals(value)){
                return anEnum;
            }
        }
        return null;
    }
}
