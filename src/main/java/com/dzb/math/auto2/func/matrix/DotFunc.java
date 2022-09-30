package com.dzb.math.auto2.func.matrix;

import com.dzb.math.auto2.Func2Var;
import com.dzb.math.auto2.Variable;
import org.jblas.DoubleMatrix;

/**
 * <p>
 * Description: 矩阵点乘(底层调用 mmul)
 * </p>
 *
 * @author DZB
 * @create 2021-05-21 15:59
 */
public class DotFunc extends Func2Var {

    public DotFunc(Variable param0, Variable param1) {
        super(param0, param1);
    }

    @Override
    public DoubleMatrix doCalc() {
        forward();
        return getParam0().getValue().mmul(getParam1().getValue());
    }

    // 对参数0 求偏导
    @Override
    public DoubleMatrix partial0(DoubleMatrix grad, DoubleMatrix value) {
        return grad.mmul(getParam1().getValue().transpose());
    }

    // 对参数1 求偏导
    @Override
    public DoubleMatrix partial1(DoubleMatrix grad, DoubleMatrix value) {
        return getParam0().getValue().transpose().mmul(grad);
    }
}
