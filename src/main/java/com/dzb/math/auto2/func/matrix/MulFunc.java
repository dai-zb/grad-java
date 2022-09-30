package com.dzb.math.auto2.func.matrix;

import com.dzb.math.auto2.Func2Var;
import com.dzb.math.auto2.Variable;
import org.jblas.DoubleMatrix;

/**
 * <p>
 * Description:  Elementwise multiply
 * </p>
 *
 * @author DZB
 * @create 2021-05-26 9:56
 */
public class MulFunc extends Func2Var {
    public MulFunc(Variable param0, Variable param1) {
        super(param0, param1);
    }

    @Override
    public DoubleMatrix doCalc() {
        DoubleMatrix a = getParam0().getValue();
        DoubleMatrix b = getParam1().getValue();
        return a.mul(b);
    }

    @Override
    public DoubleMatrix partial0(DoubleMatrix grad, DoubleMatrix value) {
        DoubleMatrix b = getParam1().getValue();
        return grad.mul(b);
    }

    @Override
    public DoubleMatrix partial1(DoubleMatrix grad, DoubleMatrix value) {
        DoubleMatrix a = getParam0().getValue();
        return grad.mul(a);
    }
}
