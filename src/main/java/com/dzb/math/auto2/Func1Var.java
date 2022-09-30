package com.dzb.math.auto2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jblas.DoubleMatrix;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-05-21 15:54
 */
@Getter
@AllArgsConstructor
public abstract class Func1Var extends Func {
    // 默认有1个参数
    private final Variable param0;

    @Override
    public void forward() {
        param0.forward();
    }

    @Override
    public void backward(DoubleMatrix grad, DoubleMatrix value) {
        DoubleMatrix partial0 = partial0(grad, value);
        param0.backward(partial0);
    }

    // 对参数0 求偏导
    public abstract DoubleMatrix partial0(DoubleMatrix grad, DoubleMatrix value);
}
