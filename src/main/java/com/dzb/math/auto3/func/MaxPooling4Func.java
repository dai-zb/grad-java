package com.dzb.math.auto3.func;

import com.dzb.math.auto2.Func1Var;
import com.dzb.math.auto2.Variable;
import org.jblas.DoubleMatrix;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-06-03 14:20
 */
// 只计算前4列的
public class MaxPooling4Func extends Func1Var {

    private int[] maxIdx;

    public MaxPooling4Func(Variable param0) {
        super(param0);
    }

    @Override
    public DoubleMatrix doCalc() {
        DoubleMatrix value = getParam0().getValue();

        // 只会选择前4列
        DoubleMatrix columns0to3 = value.getColumns(new int[]{0, 1, 2, 3});
        maxIdx = columns0to3.rowArgmaxs();

        return columns0to3.rowMaxs();
    }

    @Override
    public DoubleMatrix partial0(DoubleMatrix grad, DoubleMatrix value) {
        DoubleMatrix param0 = getParam0().getValue();
        DoubleMatrix matrix = DoubleMatrix.zeros(param0.rows, param0.columns);
        DoubleMatrix grad0 = grad.getColumn(0);
        for (int i = 0; i < maxIdx.length; i++) {
            matrix.put(i, maxIdx[i], grad0.get(i));
        }
        return matrix;
    }
}
