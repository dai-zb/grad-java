package com.dzb.math.auto2.lossFunc;

import com.dzb.math.auto2.Constant;
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
public class CrossEntropyLossFunc extends Func1Var {

    private final Constant xBar;

    public CrossEntropyLossFunc(Variable param0, Constant xBar) {
        super(param0);
        this.xBar = xBar;
    }

    @Override
    public DoubleMatrix doCalc() {
        DoubleMatrix x = getParam0().getValue();
        x = MatrixFunction.log(x);
        double v = -1 * x.dot(xBar.getValue()) / x.rows; // 取均值
        return new DoubleMatrix(new double[]{v});
    }

    @Override
    public DoubleMatrix partial0(DoubleMatrix grad, DoubleMatrix value) {
        DoubleMatrix x = getParam0().getValue();
        DoubleMatrix div = xBar.getValue().div(x);
        return div.div(-1 * x.rows).mmul(grad); // 一般情况下，传入的梯度是1;
    }
}
