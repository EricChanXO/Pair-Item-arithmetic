package com.cyqwsj.arithmetic.service;

import lombok.Data;

/**
 * @author cyq
 * @date 2020/10/9 22:27
 */
@Data
public abstract class AbstractNumber {

    /**
     * 分子
     */
    private int numerator;

    /**
     * 分母
     */
    private int denominator = 1;

    /**
     * 分母为0
     */
    private boolean isNaN;

    /**
     * 是否是分数
     */
    private boolean isFraction;

    protected AbstractNumber() {
        super();
    }

    public AbstractNumber(int numerator) {
        this.numerator = numerator;
        this.isFraction = false;
    }

    public AbstractNumber(int numerator, int denominator) {
        if (denominator == 0) {
            this.numerator = numerator;
            this.denominator = denominator;
        } else {
            AbstractNumber number = convertToTrueFraction(numerator, denominator);
            this.numerator = number.getNumerator();
            this.denominator = number.getDenominator();
            this.isFraction = number.isFraction;
        }
    }


    /**
     * 转化为真分数
     *
     * @param numerator   分子
     * @param denominator 分母
     * @return 真分数
     */
    public abstract AbstractNumber convertToTrueFraction(int numerator, int denominator);

    /**
     * 加法
     *
     * @param other 另一个数
     * @return 结果
     */
    public abstract AbstractNumber plus(AbstractNumber other);

    /**
     * 减法
     *
     * @param other 另一个数
     * @return 结果
     */
    public abstract AbstractNumber subtract(AbstractNumber other);

    /**
     * 乘法
     *
     * @param other 另一个数
     * @return 结果
     */
    public abstract AbstractNumber multiply(AbstractNumber other);

    /**
     * 除法
     *
     * @param other 另一个数
     * @return 结果
     */
    public abstract AbstractNumber divide(AbstractNumber other);


    @Override
    public String toString() {
        if (isFraction()) {
            return numerator + "/" + denominator;
        } else {
            return numerator + "";
        }
    }
}