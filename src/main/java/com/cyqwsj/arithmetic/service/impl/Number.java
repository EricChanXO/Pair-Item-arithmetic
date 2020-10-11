package com.cyqwsj.arithmetic.service.impl;

import com.cyqwsj.arithmetic.service.AbstractNumber;

/**
 * 数字
 *
 * @author cyq
 * @date 2020/10/9 22:27
 */
public class Number extends AbstractNumber {

    protected Number() {
        super();
    }

    public Number(int numerator) {
        super(numerator);
    }

    public Number(int numerator, int denominator) {
        super(numerator, denominator);
    }

    @Override
    public AbstractNumber convertToTrueFraction(int numerator, int denominator) {
        Number number = new Number();
        if (denominator == 0 || numerator == 0) {
            number.setNumerator(numerator);
            number.setDenominator(denominator);
            number.setNaN(true);
            return number;
        }
        //获取最大公因数
        int maxCommonFactor = getMaxCommonFactor(Math.abs(numerator), Math.abs(denominator));
        numerator = numerator / maxCommonFactor;
        denominator = denominator / maxCommonFactor;
        if (numerator < 0 && denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }
        number.setNumerator(numerator);
        number.setDenominator(denominator);
        if (denominator == 1) {
            number.setFraction(false);
        } else {
            number.setFraction(true);
        }
        return number;
    }

    @Override
    public AbstractNumber plus(AbstractNumber other) {
        int thisMolecule = this.getNumerator();
        int thisDenominator = this.getDenominator();

        int otherMolecule = other.getNumerator();

        //通分
        thisMolecule = thisMolecule * other.getDenominator();
        thisDenominator = thisDenominator * other.getDenominator();
        otherMolecule = otherMolecule * this.getDenominator();


        int numerator = thisMolecule + otherMolecule;
        return new Number(numerator, thisDenominator);
    }

    @Override
    public AbstractNumber subtract(AbstractNumber other) {
        int thisMolecule = this.getNumerator();
        int thisDenominator = this.getDenominator();

        int otherMolecule = other.getNumerator();

        //通分
        thisMolecule = thisMolecule * other.getDenominator();
        thisDenominator = thisDenominator * other.getDenominator();
        otherMolecule = otherMolecule * this.getDenominator();


        int numerator = thisMolecule - otherMolecule;
        return new Number(numerator, thisDenominator);
    }

    @Override
    public AbstractNumber multiply(AbstractNumber other) {
        int numerator = this.getNumerator() * other.getNumerator();
        int denominator = this.getDenominator() * other.getDenominator();
        return new Number(numerator, denominator);
    }

    @Override
    public AbstractNumber divide(AbstractNumber other) {
        int numerator = this.getNumerator() * other.getDenominator();
        int denominator = this.getDenominator() * other.getNumerator();
        return new Number(numerator, denominator);
    }

    /**
     * 获取最大公因数
     *
     * @param a 一个数
     * @param b 另一个数
     * @return 最大公因数
     */
    private int getMaxCommonFactor(int a, int b) {
        if (a < b) {
            int c = a;
            a = b;
            b = c;
        }
        int r = a % b;
        while (r != 0) {
            a = b;
            b = r;
            r = a % b;
        }
        return b;
    }
}