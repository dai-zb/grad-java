package com.dzb.math.auto1.func;

import com.dzb.math.auto1.Func2;
import com.dzb.math.auto1.Symbol;
import lombok.Getter;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-04-26 11:31
 */
@Getter
public class AddFunc extends Func2 {
    private final String name;
    private Double value;

    public AddFunc(Symbol parameter0, Symbol parameter1) {
        super(parameter0, parameter1);
        this.name = "+";
    }

    @Override
    public Double partial0() {
        return 1.;
    }

    @Override
    public Double partial1() {
        return 1.;
    }

    @Override
    public Double calc() {
        getParameter0().calc();
        getParameter1().calc();
        value = getParameter0().getValue() + getParameter1().getValue();
        return value;
    }
}
