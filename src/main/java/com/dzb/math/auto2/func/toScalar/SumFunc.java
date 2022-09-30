package com.dzb.math.auto2.func.toScalar;

import com.dzb.math.auto2.Func1Var;
import com.dzb.math.auto2.Variable;
import lombok.Getter;
import lombok.Setter;
import org.jblas.DoubleMatrix;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-05-25 13:53
 */
public class SumFunc extends Func1Var {

    @Setter
    @Getter
    private double a = 1;

    public SumFunc(Variable param0) {
        super(param0);
    }

    public SumFunc(Variable param0, double a) {
        super(param0);
        this.a = a;
    }

    @Override
    public DoubleMatrix doCalc() {
        DoubleMatrix x = getParam0().getValue();
        double ret = 0;
        if (a != 0) {
            ret = x.sum() * a;
        }
        return new DoubleMatrix(new double[]{ret});
    }

    @Override
    public DoubleMatrix partial0(DoubleMatrix grad, DoubleMatrix value) {
        DoubleMatrix x = getParam0().getValue();
        if (a == 0) {
            return DoubleMatrix.zeros(x.rows, x.columns);
        } else if (a == 1) {
            return DoubleMatrix.ones(x.rows, x.columns);
        } else {
            return DoubleMatrix.ones(x.rows, x.columns).fill(a);
        }
    }
}
