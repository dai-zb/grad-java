package com.dzb.math.auto2;

import lombok.Getter;
import lombok.Setter;
import org.jblas.DoubleMatrix;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-05-21 15:42
 */
public class Variable {
    // value 值
    @Setter
    @Getter
    private DoubleMatrix value;

    // grad 梯度
    @Getter
    private DoubleMatrix grad;

    // function 计算的函数
    private Func func;

    public Variable(DoubleMatrix value) {
        this.value = value;
    }

    public Variable(Func func) {
        this.func = func;
    }

    public boolean isNode() {
        return func != null;
    }

    // 计算并更新value
    public void forward() {
        if (isNode()) {
            if (value == null) { // 这样只有第一次进行计算
                value = func.calc();
            }
        }
    }

    // 清除value
    public void clear() {
        if (isNode()) {
            value = null;
        }
    }

    public void backward() {
        if (value.isScalar()) {
            // 更新grad(标量对自己求导，就是1)
            backward(DoubleMatrix.eye(1));
        } else {
            throw new RuntimeException("只有标量才能 backward()");
        }
    }

    public void backward(DoubleMatrix grad) {
        if (this.grad == null) {
            zeroGrad();
        }

        this.grad.addi(grad); // 使用addi方法，就会用结果替换

        if (isNode()) {
            // 反向传播
            func.backward(grad, value);
        }
    }

    // 清除梯度
    public void zeroGrad() {
        if (grad != null) {
            grad.fill(0);
        } else {
            grad = DoubleMatrix.zeros(value.rows, value.columns);
        }
    }
}
