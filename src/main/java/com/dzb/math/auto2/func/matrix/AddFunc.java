package com.dzb.math.auto2.func.matrix;

import com.dzb.math.auto2.Func2Var;
import com.dzb.math.auto2.Variable;
import org.jblas.DoubleMatrix;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-05-21 15:59
 */
public class AddFunc extends Func2Var {

    public AddFunc(Variable parameter0, Variable parameter1) {
        super(parameter0, parameter1);
    }

    @Override
    public DoubleMatrix doCalc() {
        return getParam0().getValue().add(getParam1().getValue());
    }

    // 对参数0 求偏导
    @Override
    public DoubleMatrix partial0(DoubleMatrix grad, DoubleMatrix value) {
        return grad.dup();
    }

    // 对参数1 求偏导
    @Override
    public DoubleMatrix partial1(DoubleMatrix grad, DoubleMatrix value) {
        return grad.dup();
    }
}
