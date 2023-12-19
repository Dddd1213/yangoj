package com.yangmao.yangoj.common.enumeration;

public enum QuestionSubmitJudgeInfoEnum {

    //判题信息枚举
    WAITING("待判题","waiting"),
    ACCEPTED("成功","Accepted"),
    WRONG_ANSWER("答案错误","Wrong Answer"),
    COMPILE_ERROR("编译错误","Compile Error"),
    MEMORY_LIMIT_EXCEEDED("内存溢出","Memory Limit Exceeded"),
    TIME_LIMIT_EXCEEDED("运行超时","Time Limit Exceeded"),
    PRESENTATION_ERROR("展示错误","Presentation Error"),
    OUTPUT_LIMIT_EXCEEDED("输出错误","Output Limit Exceeded"),
    DANGEROUS_OPERATION("危险操作","Dangerous Operation"),
    RUNTIME_ERROR("运行错误","Runtime Error");

    private final String text;
    private final String value;
    QuestionSubmitJudgeInfoEnum(String text, String value){
        this.text=text;
        this.value=value;
    }
    public String getValue() {
        return value;
    }
    public String getText() {
        return text;
    }

    public static QuestionSubmitJudgeInfoEnum getEnumByText(String text){
        if(text==null){
            return null;
        }

        for(QuestionSubmitJudgeInfoEnum judgeInfoEnum: QuestionSubmitJudgeInfoEnum.values()){
            if(judgeInfoEnum.text.equals(text)){
                return judgeInfoEnum;
            }
        }
        return null;
    }

}
