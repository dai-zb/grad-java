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
 * @create 2021-04-26 12:47
 */
@Getter
public class ExpFunc extends Func1 {
    private final String name;
    private Double value;

    public ExpFunc(Symbol parameter0) {
        super(parameter0);
        this.name = "exp";
    }

    @Override
    public Double partial0() {
        return value;
    }

    @Override
    public Double calc() {
        getParameter0().calc();
        value = Math.exp(getParameter0().getValue());
        return value;
    }
}
