package com.dzb.math.auto2;

import org.jblas.DoubleMatrix;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-05-21 15:54
 */
public abstract class Func {

    public DoubleMatrix calc() {
        forward();
        return doCalc();
    }

    // (使用value进行)计算返回一个值
    public abstract DoubleMatrix doCalc();

    // 对参数进行forward，确保计算前数值都存在
    public abstract void forward();

    public abstract void backward(DoubleMatrix grad, DoubleMatrix value);

    public Variable toVar() {
        return new Variable(this);
    }
}
