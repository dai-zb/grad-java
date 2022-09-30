package com.dzb.math.auto2.func.matrix;

import com.dzb.math.auto2.Func1Var;
import com.dzb.math.auto2.Variable;
import lombok.Getter;
import lombok.Setter;
import org.jblas.DoubleMatrix;

/**
 * <p>
 * Description: // 未进行测试
 * </p>
 *
 * @author DZB
 * @create 2021-05-25 14:24
 */
public class ScaleFunc extends Func1Var {

    @Getter
    @Setter
    private double scalar;

    public ScaleFunc(Variable param0, double scalar) {
        super(param0);
        this.scalar = scalar;
    }

    @Override
    public DoubleMatrix doCalc() {
        DoubleMatrix x = getParam0().getValue();
        return x.mul(scalar);
    }

    @Override
    public DoubleMatrix partial0(DoubleMatrix grad, DoubleMatrix value) {
        return grad.mul(scalar);
    }
}
