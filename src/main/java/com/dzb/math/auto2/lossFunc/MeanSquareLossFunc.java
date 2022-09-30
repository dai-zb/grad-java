package com.dzb.math.auto2.lossFunc;

import com.dzb.math.auto2.Constant;
import com.dzb.math.auto2.Func1Var;
import com.dzb.math.auto2.Variable;
import org.jblas.DoubleMatrix;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-5-24 09:56
 */
public class MeanSquareLossFunc extends Func1Var {

    private Constant yBar;

    public MeanSquareLossFunc(Variable param0, Constant yBar) {
        super(param0);
        this.yBar = yBar;
    }

    @Override
    public DoubleMatrix doCalc() {
        DoubleMatrix sub = getParam0().getValue().sub(yBar.getValue());

        double ret = sub.dot(sub);
        ret = ret / getParam0().getValue().rows;
        return new DoubleMatrix(new double[]{ret});
    }

    @Override
    public DoubleMatrix partial0(DoubleMatrix grad, DoubleMatrix value) {
        DoubleMatrix sub = getParam0().getValue().sub(yBar.getValue());
        return sub.muli(2.0 / sub.rows).mmul(grad); // 一般情况下，传入的梯度是1
    }
}
