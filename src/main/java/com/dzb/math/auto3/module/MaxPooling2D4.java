package com.dzb.math.auto3.module;

import com.dzb.math.auto2.Constant;
import com.dzb.math.auto2.Variable;
import com.dzb.math.auto2.func.GetColumnsFunc;
import com.dzb.math.auto3.func.MaxPooling4Func;
import com.dzb.math.auto2.func.PutColumnsFunc;
import com.dzb.math.utils.MatrixFunction;
import org.jblas.DoubleMatrix;

import java.util.ArrayList;
import java.util.List;

// 计算效率很低，不要使用
// 可以用MaxPoolingFunc代替
public class MaxPooling2D4 implements Module {
    private final int inB;
    private final int inC;
    private final int inH;
    private final int inW;

    private final List<Variable> vars = new ArrayList<>();

    public MaxPooling2D4(int inB, int inC, int inH, int inW) {
        this.inB = inB;
        this.inC = inC;
        this.inH = inH;
        this.inW = inW;
    }

    @Override
    public Variable build(Variable x) {

        int inHHalf = inH / 2;
        int inWHalf = inW / 2;

        int cc = inC * inHHalf * inWHalf;

        Variable y = new Constant(DoubleMatrix.zeros(inB, cc));

        for (int c = 0; c < inC; c++) {
            for (int i = 0; i < inHHalf; i++) {
                for (int j = 0; j < inWHalf; j++) {

                    int idx00 = c * (inH * inW) + (2 * i) * inW + (2 * j);
                    int idx01 = c * (inH * inW) + (2 * i) * inW + ((2 * j) + 1);
                    int idx10 = c * (inH * inW) + ((2 * i) + 1) * inW + (2 * j);
                    int idx11 = c * (inH * inW) + ((2 * i) + 1) * inW + ((2 * j) + 1);

                    int[] ints = new int[]{idx00, idx01, idx10, idx11};
                    Variable get = new GetColumnsFunc(x, ints).toVar();
                    vars.add(get);

                    Variable maxPool = new MaxPooling4Func(get).toVar();
                    vars.add(maxPool);

                    int idx = c * inWHalf * inWHalf + i * inWHalf + j;

                    y = new PutColumnsFunc(y, maxPool, new int[]{idx}).toVar();
                    vars.add(y);
                }
            }
        }
        return y;
    }

    public DoubleMatrix calc(DoubleMatrix x) {
        int rr = x.rows;

        int inHHalf = inH / 2;
        int inWHalf = inW / 2;

        int cc = inC * inHHalf * inWHalf;

        DoubleMatrix y = DoubleMatrix.zeros(rr, cc);

        for (int c = 0; c < inC; c++) {
            for (int i = 0; i < inHHalf; i++) {
                for (int j = 0; j < inWHalf; j++) {

                    int idx00 = c * (inH * inW) + (2 * i) * inW + (2 * j);
                    int idx01 = c * (inH * inW) + (2 * i) * inW + ((2 * j) + 1);
                    int idx10 = c * (inH * inW) + ((2 * i) + 1) * inW + (2 * j);
                    int idx11 = c * (inH * inW) + ((2 * i) + 1) * inW + ((2 * j) + 1);

                    int[] ints = new int[]{idx00, idx01, idx10, idx11};
                    DoubleMatrix get = MatrixFunction.getColumns(x, ints);

                    DoubleMatrix maxPool = get.getColumns(new int[]{0, 1, 2, 3}).rowMaxs();

                    int idx = c * inWHalf * inWHalf + i * inWHalf + j;

                    y.putColumn(idx, maxPool.getColumn(0));
                }
            }
        }
        return y;
    }

    @Override
    public Variable[] getParams() {
        return new Variable[0];
    }

    @Override
    public Variable[] getVariables() {
        return vars.toArray(new Variable[0]);
    }
}
