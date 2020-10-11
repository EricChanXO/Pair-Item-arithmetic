package com.cyqwsj.arithmetic.service;

import com.cyqwsj.arithmetic.service.impl.Expression;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

@SpringBootTest
public class TopicGeneratorTest {


    private TopicGenerator topicGenerator = new TopicGenerator();

    @Test
    public void generate() {
        List<Expression> expressions = topicGenerator.generate(10,10);
        Assertions.assertEquals(10,topicGenerator.generate(10,10).size());
        for(Expression expression : expressions){
            System.out.println(expression.printQuestion());
        }
    }
}