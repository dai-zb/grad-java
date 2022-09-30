package com.dzb.math.auto2.func.func;

import com.dzb.math.auto2.Func1Var;
import com.dzb.math.auto2.Variable;
import org.jblas.DoubleMatrix;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-05-25 13:04
 */
public class ReLUFunc extends Func1Var {

    private DoubleMatrix mask;

    public ReLUFunc(Variable param0) {
        super(param0);
    }

    @Override
    public DoubleMatrix doCalc() {
        mask = getParam0().getValue().ge(0);
        return getParam0().getValue().mul(mask);
    }

    // 为0处，对应的导数为1
    @Override
    public DoubleMatrix partial0(DoubleMatrix grad, DoubleMatrix value) {
        return grad.mul(mask);
    }
}
