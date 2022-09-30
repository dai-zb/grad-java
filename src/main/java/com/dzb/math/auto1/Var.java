package com.dzb.math.auto1;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-04-26 10:45
 */
@Getter
@Setter
public class Var implements Symbol {
    private final String name;
    private final Symbol symbol;
    private Double value;

    // y = f(x)  或 y = x
    public Var(String name, Symbol symbol) {
        if (name.equals(symbol.getName())) {
            throw new RuntimeException("name == symbol.name");
        }
        this.name = name;
        this.symbol = symbol;
    }

    // y = C
    public Var(String name, Double value) {
        this.name = name;
        this.symbol = null;
        this.value = value;
    }

    // 判断是那种类型的
    public boolean isConstant() {
        return symbol == null;
    }

    @Override
    public Double calc() {
        if (!isConstant()) {
            value = symbol.calc();
        }
        return value;
    }

    @Override
    public boolean contain(String name) {
        if (this.name.equals(name)) {
            return true;
        } else if (isConstant()) {
            return false;
        } else {
            return symbol.contain(name);
        }
    }

    @Override
    public Double partial(String name) {
        if (this.name.equals(name)) {
            return 1.0;
        } else if (isConstant()) {
            return 0.0;
        } else {
            return symbol.partial(name);
        }
    }

    @Override
    public String toString() {
        if (isConstant()) {
            return "(" + name + " = " + value + ")";
        } else {
            return name + " = \n    " + symbol;
        }
    }
}
