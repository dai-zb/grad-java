package com.dzb.math.auto3.optimizer;

import com.dzb.math.auto2.Variable;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-06-03 15:00
 */
public interface Optimizer {
    void add(Variable[] params, Variable[] vars);
    void add(Variable param, Variable var);
    void step();
    void clear();
}
