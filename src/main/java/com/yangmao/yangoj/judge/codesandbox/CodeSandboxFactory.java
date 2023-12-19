package com.yangmao.yangoj.judge.codesandbox;

import com.yangmao.yangoj.common.enumeration.CodeSandboxEnum;
import com.yangmao.yangoj.common.enumeration.ErrorCode;
import com.yangmao.yangoj.common.exception.BusinessException;
import com.yangmao.yangoj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.yangmao.yangoj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.yangmao.yangoj.judge.codesandbox.impl.ThirdPartySandbox;

/**
 * 代码沙箱工厂，根据传来的type，确定new哪一个类型的实例
 * @author 31067
 */
public class CodeSandboxFactory {
    /**
     * 根据用户传来的字符串参数创建对应的代码沙箱实例
     */
    public static CodeSandbox newInstance(String type){

        CodeSandboxEnum value = CodeSandboxEnum.getEnumByValue(type);
        if(value==null){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"请求沙箱不存在");
        }

        switch (value){
            case EXAMPLE:
                return new ExampleCodeSandbox();
            case REMOTE:
                return new RemoteCodeSandbox();
            case THIRDPARTY:
                return new ThirdPartySandbox();
            default:
                throw new BusinessException(ErrorCode.OPERATION_ERROR,"请求沙箱不存在");
        }
    }

}
