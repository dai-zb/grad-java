package com.dzb.math.auto3.module;

import com.dzb.math.auto2.Constant;
import com.dzb.math.auto2.Variable;
import com.dzb.math.auto2.func.matrix.AddFunc;
import com.dzb.math.auto2.func.matrix.DotFunc;
import org.jblas.DoubleMatrix;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-06-03 15:50
 */
public class Linear implements Module {

    private final Constant I;

    private final Variable W;

    private Variable U;

    private final Variable B;

    private Variable V;

    public Linear(int batchSize, int inNum, int outNum, double wScaler) {
        DoubleMatrix i = DoubleMatrix.ones(batchSize, 1);
        I = new Constant(i);

        DoubleMatrix w = DoubleMatrix.randn(inNum, outNum);
        w.muli(wScaler);
        W = new Variable(w);

        DoubleMatrix b = DoubleMatrix.zeros(1, outNum);
        B = new Variable(b);
    }

    @Override
    public Variable build(Variable X) {
        U = new DotFunc(X, W).toVar();
        V = new DotFunc(I, B).toVar();
        return new AddFunc(U, V).toVar();
    }

    @Override
    public Variable[] getParams() {
        return new Variable[]{W, B};
    }

    @Override
    public Variable[] getVariables() {
        return new Variable[]{U, V};
    }
}
