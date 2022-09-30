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
public abstract class Func1 implements Symbol {
    // 默认有一个参数
    private final Symbol parameter0;

    @Override
    public boolean contain(String name) {
        if (getName().equals(name)) {
            return true;
        }
        return parameter0.contain(name);
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

        return ret;
    }

    // 对参数0 求偏导
    public abstract Double partial0();

    @Override
    public String toString() {
        return getName() + "[" + parameter0 + "]";
    }
}
