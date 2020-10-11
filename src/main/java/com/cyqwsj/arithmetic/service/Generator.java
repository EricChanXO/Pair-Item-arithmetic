package com.cyqwsj.arithmetic.service;

import com.cyqwsj.arithmetic.po.BracePosition;
import com.cyqwsj.arithmetic.service.impl.Expression;
import com.cyqwsj.arithmetic.service.impl.Number;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static com.cyqwsj.arithmetic.constant.OperationalConstant.*;

/**
 * 题目生成器
 *
 * @author wsj
 * @date 2020/10/9 22:47
 */
@Component
public class Generator {

    private final Random random = new Random();

    /**
     * 生成问题列表
     *
     * @param totalNum 问题个数
     * @param range    数字范围
     * @return 问题列表
     */
    public List<Expression> generate(int totalNum, int range) {
        List<Expression> expressionList = new LinkedList<>();
        int i = 0;
        while (i < totalNum) {
            Expression expression = new Expression();

            int totalOperator = randInt(1, 3);
            List<Number> numberList = generateNumList(totalOperator + 1, range);
            List<String> optList = generateOptList(totalOperator);
            List<BracePosition> positionList = generateBracketsPos(totalOperator);

            expression.setTotalOperator(totalOperator);
            expression.setNumberList(numberList);
            expression.setOperatorList(optList);
            expression.setBracePositions(positionList);

            //产生的表达式有问题
            if (expression.checkLegitimacy()) {
                continue;
            }
            if (isRepeat(expressionList, expression)) {
                continue;
            }
            expressionList.add(expression);
            i++;

        }
        return expressionList;
    }

    /**
     * 判断是否重复出题
     *
     * @param expressionList 题目列表
     * @param expression     要判断的列表
     * @return 是否重复
     */
    private boolean isRepeat(List<Expression> expressionList, Expression expression) {
        for (Expression s : expressionList) {
            if (expression.isRepeat(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 随机生成一个分数
     *
     * @param range      范围
     * @param isFraction 要分数还是自然数
     * @return 生成的数
     */
    private Number generateNum(int range, boolean isFraction) {
        if (isFraction) {
            return new Number(randInt(1, range), randInt(1, range));
        } else {
            return new Number(random.nextInt(range + 1));
        }
    }

    /**
     * 随机生成运算符
     *
     * @return 运算符
     */
    private String generateOpt() {
        List<String> list = new ArrayList<>(4);
        list.add(PLUS);
        list.add(SUBTRACT);
        list.add(MULTIPLY);
        list.add(DIVIDE);
        return choice(list);
    }

    /**
     * 随机生成括号位置列表
     *
     * @param optNum 运算符个数
     * @return 括号位置列表
     */
    private List<BracePosition> generateBracketsPos(int optNum) {
        List<BracePosition> positionList = new LinkedList<>();
        if (optNum < 2) {
            return positionList;
        } else if (optNum == 2) {
            BracePosition pos1 = new BracePosition(0, 1);
            BracePosition pos2 = new BracePosition(1, 2);
            List<BracePosition> list = new LinkedList<>();
            list.add(pos1);
            list.add(pos2);
            positionList.add(choice(list));
            return positionList;
        } else if (optNum == 3) {
            int begin;
            int end;
            //一个括号
            if (random.nextBoolean()) {
                do {
                    begin = random.nextInt(3);
                    end = randInt(begin + 1, 3);
                } while (end - begin >= 3);
                positionList.add(new BracePosition(begin, end));
                return positionList;
                //两个括号
            } else {
                //两个没有嵌套的括号
                if (random.nextBoolean()) {
                    positionList.add(new BracePosition(0, 1));
                    positionList.add(new BracePosition(2, 3));
                    return positionList;
                } else {
                    BracePosition pos = random.nextBoolean() ? new BracePosition(0, 2) : new BracePosition(1, 3);
                    begin = randInt(pos.getBegin(), pos.getEnd() - 1);
                    end = randInt(begin, pos.getEnd());
                    BracePosition pos1 = new BracePosition(begin, end);
                    positionList.add(pos);
                    positionList.add(pos1);
                    return positionList;
                }
            }
        }
        return null;
    }

    /**
     * 随机生成数字列表
     *
     * @param totalNum 要生成的数量
     * @param range    范围
     * @return 数字列表
     */
    private List<Number> generateNumList(int totalNum, int range) {
        List<Number> list = new LinkedList<>();
        for (int i = 0; i < totalNum; i++) {
            list.add(generateNum(range, random.nextBoolean()));
        }
        return list;
    }

    /**
     * 随机生成运算符列表
     *
     * @param optNum 运算符数量
     * @return 运算符列表
     */
    private List<String> generateOptList(int optNum) {
        List<String> list = new LinkedList<>();
        for (int i = 0; i < optNum; i++) {
            list.add(generateOpt());
        }
        return list;
    }

    /**
     * 随机选择列表中的一项
     *
     * @param list 列表
     * @param <T>  泛型
     * @return 选择的结果
     */
    private <T> T choice(List<T> list) {
        int i = random.nextInt(list.size());
        return list.get(i);

    }

    /**
     * 返回范围内的随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 随机数
     */
    private int randInt(int min, int max) {
        return random.nextInt(max) % (max - min + 1) + min;
    }
}