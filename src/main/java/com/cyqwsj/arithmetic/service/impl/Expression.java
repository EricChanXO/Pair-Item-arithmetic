package com.cyqwsj.arithmetic.service.impl;

import com.cyqwsj.arithmetic.po.BracePosition;
import com.cyqwsj.arithmetic.service.AbstractExpression;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.cyqwsj.arithmetic.constant.OperationalConstant.*;

/**
 * 表达式（题目）
 *
 * @author cyq
 * @date 2020/10/9 22:28
 */
public class Expression extends AbstractExpression {

    @Override
    public void calculate() {
        Expression temp = copy(this);
        while (temp.getOperatorList().size() > 0) {
            int index = getPrioritizedOperation(temp);
            if (index == -1) {
                throw new RuntimeException("error");
            }
            Number number1 = temp.getNumberList().get(index);
            Number number2 = temp.getNumberList().get(index + 1);
            String opt = temp.getOperatorList().get(index);
            Number result = operate(number1, number2, opt);

            temp.getNumberList().set(index, result);
            temp.getNumberList().remove(index + 1);
            temp.getOperatorList().remove(index);

        }
        this.setAnswer(temp.getNumberList().get(0));
    }

    /**
     * 复制一个表达式
     *
     * @param expression 要复制的表达式
     * @return 复制好的表达式
     */
    private Expression copy(Expression expression) {
        Expression temp = new Expression();
        List<Number> numberList = new LinkedList<>(expression.getNumberList());
        temp.setNumberList(numberList);
        List<String> optList = new LinkedList<>(expression.getOperatorList());
        temp.setOperatorList(optList);
        List<BracePosition> positionList = new LinkedList<>();
        for (BracePosition p : expression.getBracePositions()) {
            BracePosition position = new BracePosition(p.getBegin(), p.getEnd());
            positionList.add(position);
        }
        temp.setBracePositions(positionList);
        return temp;
    }

    /**
     * 做运算
     *
     * @param number1 操作数
     * @param number2 操作数
     * @param opt     运算符
     * @return 运算结果
     */
    private Number operate(Number number1, Number number2, String opt) {
        if (PLUS.equals(opt)) {
            return (Number) number1.plus(number2);
        } else if (SUBTRACT.equals(opt)) {
            Number result = (Number) number1.subtract(number2);
            if (result.getNumerator() < 0 || result.getDenominator() <= 0) {
                this.setExistNegative(true);
            }
            return result;
        } else if (MULTIPLY.equals(opt)) {
            return (Number) number1.multiply(number2);
        } else if (DIVIDE.equals(opt)) {

            if (number2.getNumerator() == 0) {
                this.setExistDivideZero(true);
                return new Number(0);
            }
            return (Number) number1.divide(number2);
        }
        return null;
    }

    /**
     * 获取当前最高优先级的操作符
     *
     * @param expression 表达式
     * @return 最高优先级的操作符的下标
     */
    private int getPrioritizedOperation(Expression expression) {
        if (expression.getBracePositions().size() > 0) {
            //两个括号
            if (expression.getBracePositions().size() > 1) {
                BracePosition pos1 = expression.getBracePositions().get(0);
                BracePosition pos2 = expression.getBracePositions().get(1);
                if (pos1.range() == pos2.range()) {
                    pos2.setBegin(pos2.getBegin() - 1);
                    pos2.setEnd(pos2.getEnd() - 1);
                    expression.getBracePositions().set(1, pos2);
                    expression.getBracePositions().remove(0);
                    return pos1.getBegin();
                } else if (pos1.range() < pos2.range()) {
                    pos2.setEnd(pos2.getEnd() - 1);
                    expression.getBracePositions().set(1, pos2);
                    expression.getBracePositions().remove(0);
                    return pos1.getBegin();
                } else if (pos1.range() > pos2.range()) {
                    pos1.setEnd(pos1.getEnd() - 1);
                    expression.getBracePositions().set(0, pos1);
                    expression.getBracePositions().remove(1);
                    return pos2.getBegin();
                }

                //一个括号
            } else if (expression.getBracePositions().size() == 1) {
                BracePosition pos = expression.getBracePositions().get(0);
                //括号包括一个操作符
                if (pos.range() == 1) {
                    expression.getBracePositions().remove(0);
                    return pos.getBegin();
                    //括号包括2个操作符
                } else {
                    String opt1 = expression.getOperatorList().get(pos.getBegin());
                    String opt2 = expression.getOperatorList().get(pos.getBegin() + 1);
                    //取第一个操作符
                    if (isPrioritizedOperation(opt1, opt2)) {
                        pos.setEnd(pos.getEnd() - 1);
                        expression.getBracePositions().set(0, pos);
                        return pos.getBegin();
                        //取第二个操作符
                    } else {
                        pos.setEnd(pos.getEnd() - 1);
                        expression.getBracePositions().set(0, pos);
                        return pos.getBegin() + 1;
                    }
                }
            }
        } else {
            if (expression.getOperatorList().size() == 1) {
                return 0;
            }
            int index = 0;
            String prioritizedOpt = expression.getOperatorList().get(0);
            String opt;
            for (int i = 1; i < expression.getOperatorList().size(); i++) {
                opt = expression.getOperatorList().get(i);
                if (!isPrioritizedOperation(prioritizedOpt, opt)) {
                    prioritizedOpt = opt;
                    index = i;
                }
            }
            return index;
        }
        return -1;
    }

    /**
     * 比较两个运算符的优先级
     *
     * @param opt1 左边的运算符
     * @param opt2 右边的运算符
     * @return 左边优先级高返回true
     */
    private boolean isPrioritizedOperation(String opt1, String opt2) {
        if (opt1.equals(MULTIPLY) || opt1.equals(DIVIDE)) {
            return true;
        } else {
            if (opt2.equals(PLUS) || opt2.equals(SUBTRACT)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isRepeat(AbstractExpression expression) {
        if (this.getTotalOperator() == expression.getTotalOperator()) {
            List<Number> numberList = this.getNumberList();
            List<Number> otherNumberList = expression.getNumberList();
            if (numberList.containsAll(otherNumberList)) {
                List<String> operatorList = this.getOperatorList();
                List<String> otherOperatorList = expression.getOperatorList();
                if (operatorList.containsAll(otherOperatorList)) {
                    return compareExpression(this, (Expression) expression);
                }
            }
        }
        return false;
    }

    /**
     * 比较表达式是否重复
     *
     * @param expression      表达式
     * @param otherExpression 另一个表达式
     * @return true为重复
     */
    private boolean compareExpression(Expression expression, Expression otherExpression) {
        Expression temp1 = expression.copy(expression);
        Expression temp2 = expression.copy(otherExpression);
        while (temp1.getOperatorList().size() > 0) {
            int index1 = getPrioritizedOperation(temp1);
            int index2 = getPrioritizedOperation(temp2);

            Number number1 = temp1.getNumberList().get(index1);
            Number number2 = temp1.getNumberList().get(index1 + 1);
            String opt1 = temp1.getOperatorList().get(index1);
            Number result1 = operate(number1, number2, opt1);

            Number number3 = temp2.getNumberList().get(index2);
            Number number4 = temp2.getNumberList().get(index2 + 1);
            String opt2 = temp2.getOperatorList().get(index2);
            Number result2 = operate(number3, number4, opt2);

            if (PLUS.equals(opt1) && PLUS.equals(opt2) || MULTIPLY.equals(opt1) || MULTIPLY.equals(opt2)) {
                if (!((number1.equals(number3) || number1.equals(number4))
                        && (number2.equals(number3) || number2.equals(number4)))) {
                    return false;
                }
            }
            temp1.getNumberList().set(index1, result1);
            temp1.getNumberList().remove(index1 + 1);
            temp1.getOperatorList().remove(index1);

            temp2.getNumberList().set(index2, result2);
            temp2.getNumberList().remove(index2 + 1);
            temp2.getOperatorList().remove(index2);

        }
        return true;
    }

    @Override
    public boolean checkNegative() {
        return this.isExistNegative();
    }

    @Override
    public boolean checkDivideZero() {
        return this.isExistDivideZero();
    }

    @Override
    public boolean checkLegitimacy() {
        this.calculate();
        return checkNegative() || checkDivideZero();
    }

    @Override
    public String printQuestion() {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> list = makeupString();
        for (String s : list) {
            stringBuilder.append(s);
            stringBuilder.append(" ");
        }
        stringBuilder.append("= ");
        return stringBuilder.toString();
    }

    /**
     * 构建输出字符串
     *
     * @return 字符串列表
     */
    private List<String> makeupString() {
        List<String> list = new ArrayList<>(7 + this.getBracePositions().size() * 2);
        List<Number> numberList = this.getNumberList();
        List<String> operatorList = this.getOperatorList();
        list.add(numberList.get(0).toString());
        for (int i = 0; i < operatorList.size(); i++) {
            list.add(operatorList.get(i));
            list.add(numberList.get(i + 1).toString());
        }
        insertBrackets(list);
        return list;

    }

    /**
     * 插入括号
     *
     * @param list 有操作数和运算符的字符串列表
     * @return 完整的运算表达式字符串
     */
    private List<String> insertBrackets(List<String> list) {
        List<BracePosition> bracePositions = this.getBracePositions();
        if (bracePositions == null || bracePositions.size() == 0) {
            return list;
        }
        BracePosition pos = bracePositions.get(0);
        //只有一个括号
        if (bracePositions.size() == 1) {
            //括号括着两个运算符
            if (pos.range() == 2) {
                if (pos.getBegin() == 0) {
                    list.add(0, LEFT_BRACKET);
                    list.add(6, RIGHT_BRACKET);
                } else {
                    list.add(2, LEFT_BRACKET);
                    list.add(8, RIGHT_BRACKET);
                }
            } else {
                int leftBegin = pos.getBegin() * 2;
                list.add(leftBegin, LEFT_BRACKET);
                list.add(leftBegin + 4, RIGHT_BRACKET);
            }
        } else {
            BracePosition pos2 = bracePositions.get(1);
            if (pos.range() == pos2.range()) {
                int begin = 0;
                list.add(begin, LEFT_BRACKET);
                list.add(begin + 4, RIGHT_BRACKET);
                list.add(begin + 6, LEFT_BRACKET);
                list.add(10, RIGHT_BRACKET);
            } else {
                if (pos.range() < pos2.range()) {
                    int leftBegin = pos.getBegin() * 2;
                    list.add(leftBegin, LEFT_BRACKET);
                    list.add(leftBegin + 4, RIGHT_BRACKET);

                    leftBegin = pos2.getBegin() * 2;
                    list.add(leftBegin, LEFT_BRACKET);
                    list.add(leftBegin + 6, RIGHT_BRACKET);
                } else if (pos.range() > pos2.range()) {
                    int leftBegin = pos2.getBegin() * 2;
                    list.add(leftBegin, LEFT_BRACKET);
                    list.add(leftBegin + 4, RIGHT_BRACKET);

                    leftBegin = pos.getBegin() * 2;
                    list.add(leftBegin, LEFT_BRACKET);
                    list.add(leftBegin + 6, RIGHT_BRACKET);
                }
            }
        }
        return list;
    }
}