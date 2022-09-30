package com.dzb.math.auto2.func;

import com.dzb.math.auto2.Func2Var;
import com.dzb.math.auto2.Variable;
import org.jblas.DoubleMatrix;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-06-03 13:51
 */
public class PutColumnsFunc extends Func2Var {

    private final int[] idx;

    public PutColumnsFunc(Variable param0, Variable param1, int[] idx) {
        super(param0, param1);
        this.idx = idx;
    }

    @Override
    public DoubleMatrix doCalc() {
        DoubleMatrix param0 = getParam0().getValue();
        DoubleMatrix param1 = getParam1().getValue();

        DoubleMatrix matrix = param0.dup();
        for (int i = 0; i < idx.length; i++) {
            matrix.putColumn(idx[i], param1.getColumn(i));
        }
        return matrix;
    }

    @Override
    public DoubleMatrix partial0(DoubleMatrix grad, DoubleMatrix value) {
        DoubleMatrix matrix = grad.dup();
        DoubleMatrix zeros = DoubleMatrix.zeros(matrix.rows, 1);
        for (int j : idx) {
            matrix.putColumn(j, zeros);
        }
        return matrix;
    }

    @Override
    public DoubleMatrix partial1(DoubleMatrix grad, DoubleMatrix value) {
        DoubleMatrix param1 = getParam1().getValue();
        // 只更新第0列的内容
        DoubleMatrix matrix = DoubleMatrix.zeros(param1.rows, param1.columns);
        for (int i = 0; i < idx.length; i++) {
            matrix.putColumn(i, grad.getColumn(idx[i]));
        }
        return matrix;
    }
}
