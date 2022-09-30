package com.dzb.math.auto2.func;

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
 * @create 2021-05-26 10:41
 */
public class MaskFunc extends Func1Var {

    @Setter
    @Getter
    private DoubleMatrix mask;

    public MaskFunc(Variable param0) {
        super(param0);
    }

    public MaskFunc(Variable param0, DoubleMatrix mask) {
        super(param0);
        this.mask = mask;
    }

    @Override
    public DoubleMatrix doCalc() {
        DoubleMatrix x = getParam0().getValue();
        return x.mul(mask);
    }

    @Override
    public DoubleMatrix partial0(DoubleMatrix grad, DoubleMatrix value) {
        return grad.mul(mask);
    }
}
