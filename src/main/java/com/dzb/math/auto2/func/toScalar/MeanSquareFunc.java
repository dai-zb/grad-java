package com.dzb.math.auto2.func.toScalar;

import com.dzb.math.auto2.Func1Var;
import com.dzb.math.auto2.Variable;
import org.jblas.DoubleMatrix;

/**
 * <p>
 * Description: 按行计数求平均
 * </p>
 *
 * @author DZB
 * @create 2021-05-23 12:51
 */
public class MeanSquareFunc extends Func1Var {

    public MeanSquareFunc(Variable param0) {
        super(param0);
    }

    @Override
    public DoubleMatrix doCalc() {
        double ret = getParam0().getValue().dot(getParam0().getValue());
        ret = ret / getParam0().getValue().rows;
        return new DoubleMatrix(new double[]{ret});
    }

    @Override
    public DoubleMatrix partial0(DoubleMatrix grad, DoubleMatrix value) {
        DoubleMatrix matrix = getParam0().getValue();
        return matrix.mul(2.0 / matrix.rows).mmul(grad); // 一般情况下，传入的梯度是1
    }
}
