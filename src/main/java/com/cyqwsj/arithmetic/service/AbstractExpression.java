package com.cyqwsj.arithmetic.service;

import com.cyqwsj.arithmetic.po.BracePosition;
import com.cyqwsj.arithmetic.service.impl.Number;
import lombok.Data;

import java.util.List;

/**
 * @author cyq
 * @date 2020/10/9 22:25
 */
@Data
public abstract class AbstractExpression {

    /**
     * 操作数列表
     */
    private List<Number> numberList;

    /**
     * 运算符列表
     */
    private List<String> operatorList;

    /**
     * 是否有减完为负数的情况
     */
    private boolean isExistNegative;

    /**
     * 是否存在除0操作;
     */
    private boolean isExistDivideZero;

    /**
     * 操作符个数
     */
    private int totalOperator;

    /**
     * 括号在操作符的位置
     */
    private List<BracePosition> bracePositions;

    /**
     * 答案
     */
    private Number answer;


    /**
     * 计算当前表达式答案
     */
    public abstract void calculate();

    /**
     * 与当前表达式比较，判断是否重复
     *
     * @param expression 另一个表达式
     * @return 判断结果
     */
    public abstract boolean isRepeat(AbstractExpression expression);

    /**
     * 检查是否存在负数情况
     *
     * @return 是否存在负数情况
     */
    public abstract boolean checkNegative();

    /**
     * 检查是否存在除0的情况
     *
     * @return 是否存在除0的情况
     */
    public abstract boolean checkDivideZero();

    /**
     * 判断表达式的合法性
     *
     * @return 不合法返回true
     */
    public abstract boolean checkLegitimacy();

    /**
     * 打印问题
     *
     * @return 问题的字符串
     */
    public abstract String printQuestion();
}
