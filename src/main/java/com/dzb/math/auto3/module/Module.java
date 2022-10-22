package com.dzb.math.auto3.module;

import com.dzb.math.auto2.Variable;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-06-03 14:54
 */
public interface Module {
    Variable build(Variable x);

    Variable[] getParams();

    Variable[] getVariables();
}
