package com.yangmao.yangoj.judge.strategy;

import com.yangmao.yangoj.common.enumeration.QuestionSubmitLanguageEnum;
import com.yangmao.yangoj.judge.codesandbox.CodeSandbox;
import com.yangmao.yangoj.judge.strategy.impl.DefaultJudgeStrategy;
import com.yangmao.yangoj.judge.strategy.impl.JavaJudgeStrategy;

/**
 * 判题策略工厂，根据questionSubmit的语言信息，决定返回哪一工厂
 */
public class JudgeStrategyFactory {


    public static JudgeStrategy newInstance(String language){

        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if(languageEnum==null){
            return new DefaultJudgeStrategy();
        }

        switch (languageEnum){
            case JAVA:
                return new JavaJudgeStrategy();
            default:
                return new DefaultJudgeStrategy();
        }

    }
}
