package com.cyqwsj.arithmetic.po;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 括号的位置
 *
 * @author cyq
 * @date 2020/10/9 21:51
 */
@Data
@AllArgsConstructor
public class BracePosition {

    /**
     * 括号开始位置
     */
    private int begin;

    /**
     * 括号结束位置
     */
    private int end;

    /**
     * 获取括号包括的运算符数量
     *
     * @return 包括的运算符数量
     */
    public int range() {
        return this.end - this.begin;
    }
}