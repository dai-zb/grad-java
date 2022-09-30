package com.dzb.math.auto2.func;

import com.dzb.math.auto2.Func1Var;
import com.dzb.math.auto2.Variable;
import lombok.Getter;
import org.jblas.DoubleMatrix;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-06-03 13:13
 */
public class GetColumnsFunc extends Func1Var {

    @Getter
    private final int[] idx;

    public GetColumnsFunc(Variable param0, int[] idx) {
        super(param0);
        this.idx = idx;
    }

    @Override
    public DoubleMatrix doCalc() {
        DoubleMatrix value = getParam0().getValue();
        int rows = value.rows;
        int columns = idx.length;
        DoubleMatrix matrix = DoubleMatrix.zeros(rows, columns);
        for (int i = 0; i < idx.length; i++) {
            DoubleMatrix column = value.getColumn(idx[i]);
            matrix.putColumn(i, column);
        }
        return matrix;
    }

    @Override
    public DoubleMatrix partial0(DoubleMatrix grad, DoubleMatrix value) {
        DoubleMatrix param = getParam0().getValue();
        int rows = param.rows;
        int columns = param.columns;
        DoubleMatrix matrix = DoubleMatrix.zeros(rows, columns);
        for (int i = 0; i < idx.length; i++) {
            DoubleMatrix column = grad.getColumn(i);
            matrix.putColumn(idx[i], column);
        }
        return matrix;
    }
}
