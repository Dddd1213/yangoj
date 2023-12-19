package com.yangmao.yangoj.judge.codesandbox;

import com.yangmao.yangoj.annotation.SandboxLog;
import com.yangmao.yangoj.common.enumeration.QuestionSubmitLanguageEnum;
import com.yangmao.yangoj.judge.codesandbox.proxy.CodeSandboxAddLogDynamicProxy;
import com.yangmao.yangoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yangmao.yangoj.judge.codesandbox.model.ExecuteCodeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class CodeSandboxTest {

    @Value("${codesandbox.type:example}")
    private String type;

    @Resource(name = "${codesandbox.type:example}"+"CodeSandbox")
    CodeSandbox codeSandbox;

    /**
     * 工厂模式
     */
    @Test
    void executeCode() {

        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);

        String code = "int main{return 0;}";
        String java = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "3,4");

        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(java)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
       //Assertions.assertNotNull(executeCodeResponse);

    }
    /**
     * 工厂模式+动态代理
     */
    @Test
    void executeCode2() {

//        创建动态代理
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        InvocationHandler handler = new CodeSandboxAddLogDynamicProxy(codeSandbox);

        CodeSandbox proxy = (CodeSandbox)Proxy.newProxyInstance(codeSandbox.getClass().getClassLoader(),
                codeSandbox.getClass().getInterfaces(),
                handler
        );

        //创建 executeCodeRequest
        String code = "int main{return 0;}";
        String java = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "3,4");

        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(java)
                .inputList(inputList)
                .build();
        //这里执行的其实是proxy中的invoke方法
       proxy.executeCode(executeCodeRequest);

    }

/**
 *   aop+自定义注解（实现log）+依赖注入
  */
    @Test
    void executeCode3(){
        String code = "int main{return 0;}";
        String java = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "3,4");

        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(java)
                .inputList(inputList)
                .build();
        codeSandbox.executeCode(executeCodeRequest);

    }
    /**
     * 测试自定义注解在接口上是否生效
     */
    @Test
    void interfaceTest(){
        Method[] methods = CodeSandbox.class.getDeclaredMethods();
        SandboxLog annotation = methods[0].getAnnotation(SandboxLog.class);
        if(annotation!=null){
            System.out.println("自定义注解生效");
        }
    }




}