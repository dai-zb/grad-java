package com.dzb.math.auto1;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-04-26 11:10
 */
@AllArgsConstructor
@Getter
public abstract class Func2 implements Symbol {
    // 默认有两个参数
    private final Symbol parameter0;
    private final Symbol parameter1;

    @Override
    public boolean contain(String name) {
        if (getName().equals(name)) {
            return true;
        }
        return parameter0.contain(name) || parameter1.contain(name);
    }

    @Override
    public Double partial(String name) {
        if (getName().equals(name)) {
            return 1.;
        }

        double ret = 0.0;
        if (parameter0.contain(name)) {
            // 链式法则
            ret += partial0() * parameter0.partial(name);
        }
        if (parameter1.contain(name)) {
            // 链式法则
            ret += partial1() * parameter1.partial(name);
        }
        return ret;
    }

    // 对参数0 求偏导
    public abstract Double partial0();

    // 对参数1 求偏导
    public abstract Double partial1();

    @Override
    public String toString() {
        return "[" + parameter0 + " " + getName() + " " + parameter1 + "]";
    }
}
