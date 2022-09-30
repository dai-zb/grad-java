package com.dzb.math.auto1.func;

import com.dzb.math.auto1.Func1;
import com.dzb.math.auto1.Symbol;
import lombok.Getter;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-04-26 13:02
 */
@Getter
public class PowFunc extends Func1 {
    private final double constant;

    private final String name;
    private Double value;

    public PowFunc(Symbol parameter0, double constant) {
        super(parameter0);
        this.constant = constant;
        this.name = "pow_" + constant;
    }

    @Override
    public Double partial0() {
        return constant * Math.pow(getParameter0().getValue(), constant - 1);
    }


    @Override
    public Double calc() {
        getParameter0().calc();
        value = Math.pow(getParameter0().getValue(), constant);
        return value;
    }
}
