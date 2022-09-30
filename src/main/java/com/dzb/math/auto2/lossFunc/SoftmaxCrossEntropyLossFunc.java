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
public class SoftmaxCrossEntropyLossFunc extends Func1Var {

    private final Constant zBar;
    private DoubleMatrix z;

    public SoftmaxCrossEntropyLossFunc(Variable param0, Constant zBar) {
        super(param0);
        this.zBar = zBar;
    }

    @Override
    public DoubleMatrix doCalc() {
        DoubleMatrix x = getParam0().getValue();

        z = MatrixFunction.softmax1(x); // 计算softmax

        // DoubleMatrix z_bar = constant.getValue();
        DoubleMatrix zz = MatrixFunction.log(z);
        double v = -1 * zz.dot(zBar.getValue()) / x.rows; // 取均值

        return new DoubleMatrix(new double[]{v});
    }

    @Override
    public DoubleMatrix partial0(DoubleMatrix grad, DoubleMatrix value) {
        DoubleMatrix sub = z.sub(zBar.getValue());
        return sub.muli(1.0 / sub.rows).mmul(grad); // 一般情况下，传入的梯度是1
    }
}
