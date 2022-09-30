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
 * @create 2021-05-23 14:14
 */
public class SigmoidFunc extends Func1Var {
    public SigmoidFunc(Variable param0) {
        super(param0);
    }

    @Override
    public DoubleMatrix doCalc() {
        DoubleMatrix matrix = getParam0().getValue();
//        int rows = matrix.rows;
//        int columns = matrix.columns;
//
//        DoubleMatrix ret = DoubleMatrix.zeros(rows, columns);
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < columns; j++) {
//                double v = matrix.get(i, j);
//                v = 1 / (1 + Math.exp(-1 * v));
//                ret.put(i, j, v);
//            }
//        }
//
//        return ret;
        return MatrixFunction.sigmoid(matrix);
    }

    @Override
    public DoubleMatrix partial0(DoubleMatrix grad, DoubleMatrix value) {
        // delta = y * (1 - y)
        DoubleMatrix ones = DoubleMatrix.ones(value.rows, value.columns);
        DoubleMatrix delta = ones.subi(value).muli(value);
        // grad * delta
        return delta.muli(grad);
    }
}
