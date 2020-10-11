package com.cyqwsj.arithmetic.service;

import com.cyqwsj.arithmetic.service.impl.Expression;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class GenerateToFileTest {

    @Resource
    private GenerateToFile generateToFile;

    @Resource
    private TopicGenerator topicGenerator;

    @Test
    public void generatorToFile() {
        String questionPath = "question.txt";
        String answerPath = "answer.txt";
        List<Expression> list = topicGenerator.generate(10, 10);
        generateToFile.generatorToFile(list, questionPath, answerPath);
    }

    @Test
    public void checkAnswer() {
        String questionPath = "question.txt";
        String answerPath = "answer.txt";
        String judgePath = "judge.txt";
        generateToFile.checkAnswer(questionPath, answerPath, judgePath);
    }
}