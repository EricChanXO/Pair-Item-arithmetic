package com.cyqwsj.arithmetic.service;

import com.cyqwsj.arithmetic.service.impl.Expression;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@SpringBootTest
public class GenerateToFileTest {


    private GenerateToFile generateToFile = new GenerateToFile();


    private TopicGenerator topicGenerator = new TopicGenerator();

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