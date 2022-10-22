package com.dzb.math.auto3.optimizer;

import com.dzb.math.auto2.Variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-06-03 16:07
 */
public class Sgd implements Optimizer {
    private final List<Variable> params = new ArrayList<>();
    private final List<Variable> vars = new ArrayList<>();
    private final double lr;

    public Sgd(double lr) {
        this.lr = lr;
    }

    @Override
    public void add(Variable[] params, Variable[] vars) {
        if (params != null) {
            List<Variable> list = Arrays.asList(params);
            if (list.contains(null)) {
                throw new IllegalArgumentException("不能包含null，检查是否未build()");
            }
            this.params.addAll(list);
        }
        if (vars != null) {
            List<Variable> list = Arrays.asList(vars);
            if (list.contains(null)) {
                throw new IllegalArgumentException("不能包含null，检查是否未build()");
            }
            this.vars.addAll(list);
        }
    }

    @Override
    public void add(Variable param, Variable var) {
        if (param != null) {
            this.params.add(param);
        }
        if (var != null) {
            this.vars.add(var);
        }
    }

    @Override
    public void step() {
        for (Variable param : params) {
            param.getValue().addi(param.getGrad().muli(-1 * lr));
        }
    }

    @Override
    public void clear() {
        for (Variable var : vars) {
            var.clear();
            var.zeroGrad();
        }
        for (Variable param : params) {
            // 参数的值需要保留
            // param.clear();
            param.zeroGrad();
        }
    }
}
