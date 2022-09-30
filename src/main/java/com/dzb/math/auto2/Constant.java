package com.dzb.math.auto2;

import org.jblas.DoubleMatrix;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-05-21 15:42
 */
public class Constant extends Variable {
    public Constant(DoubleMatrix value) {
        super(value);
    }

    public boolean isNode() {
        return false;
    }

    // 计算并更新value
    public void forward() {
        // do nothing
    }

    // 清除value
    public void clear() {
        // do nothing
    }

    public void backward() {
        throw new RuntimeException("常量无法 backward()");
    }

    public void backward(DoubleMatrix grad) {
        // do nothing
    }

    // 清除梯度
    public void zeroGrad() {
        // do nothing
    }
}
