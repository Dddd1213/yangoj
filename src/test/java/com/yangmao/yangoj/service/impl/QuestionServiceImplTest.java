package com.yangmao.yangoj.service.impl;

import com.yangmao.yangoj.model.DTO.question.QueryQuestionDTO;
import com.yangmao.yangoj.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionServiceImplTest {

    @Autowired
    QuestionService questionService;

    @Test
    void getQuestionVOPage() {
        QueryQuestionDTO queryQuestionDTO = new QueryQuestionDTO();
        queryQuestionDTO.setId(1L);
        queryQuestionDTO.setPageSize(10);
        queryQuestionDTO.setCurrent(0);
        System.out.println(questionService.getQuestionVOPage(queryQuestionDTO).toString());
    }
}