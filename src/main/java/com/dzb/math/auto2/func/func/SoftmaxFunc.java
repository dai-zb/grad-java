package com.dzb.math.auto2.func.func;

import com.dzb.math.auto2.Func1Var;
import com.dzb.math.auto2.Variable;
import com.dzb.math.utils.MatrixFunction;
import org.jblas.DoubleMatrix;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-05-24 9:58
 */
public class SoftmaxFunc extends Func1Var {
    public SoftmaxFunc(Variable param0) {
        super(param0);
    }

    @Override
    public DoubleMatrix doCalc() {
        DoubleMatrix matrix = getParam0().getValue();
        return MatrixFunction.softmax1(matrix);
    }

    @Override
    public DoubleMatrix partial0(DoubleMatrix grad, DoubleMatrix value) {
        return partial(grad, value);
    }

    private static DoubleMatrix partial0withIdxRow(DoubleMatrix grad, DoubleMatrix value, int idx) {
        DoubleMatrix g = grad.getRow(idx);
        DoubleMatrix y = value.getRow(idx);
        DoubleMatrix y2 = y.transpose().mmul(y);
        return g.mmul(DoubleMatrix.diag(y).subi(y2));
    }

    public static DoubleMatrix partial(DoubleMatrix grad, DoubleMatrix value) {
        DoubleMatrix ret = DoubleMatrix.zeros(grad.rows, grad.columns);
        for (int i = 0; i < grad.rows; i++) {
            DoubleMatrix row = partial0withIdxRow(grad, value, i);
            ret.putRow(i, row);
        }
        return ret;
    }
}
