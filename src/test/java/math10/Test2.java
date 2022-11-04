package math10;

import com.dzb.math.auto2.Constant;
import com.dzb.math.auto2.Variable;
import com.dzb.math.auto2.func.toScalar.SumFunc;
import com.dzb.math.auto3.module.MaxPooling2D4;
import org.jblas.DoubleMatrix;
import org.junit.Test;

public class Test2 {
    @Test
    public void test1() {
        int inC = 2;
        int inH = 4;
        int inW = 4;

        MaxPooling2D4 maxPooling2D4 = new MaxPooling2D4(2, inC, inH, inW);

        // 2 表示batch size = 2
        DoubleMatrix x = DoubleMatrix.linspace(1, 2 * inC * inH * inW, 2 * inC * inH * inW);
        x.reshape(inC, inC * inH * inW);

        System.out.println(x.toString("%.4f", "[", "]", ", ", "\n"));
        /*
        [1.0000, 3.0000, 5.0000, 7.0000, 9.0000, 11.0000,
        2.0000, 4.0000, 6.0000, 8.0000, 10.0000, 12.0000,
         */
        Constant X = new Constant(x);
        Variable Y = maxPooling2D4.build(X);

        Variable l = new SumFunc(Y).toVar();
        l.forward();

        System.out.println(Y.getValue().toString("%.4f", "[", "]", ", ", "\n"));
        /*
        [11.0000, 15.0000, 27.0000, 31.0000, 43.0000, 47.0000, 59.0000, 63.0000
        12.0000, 16.0000, 28.0000, 32.0000, 44.0000, 48.0000, 60.0000, 64.0000]
         */
        System.out.println();
    }

    @Test
    public void test2() {
        int inC = 2;
        int inH = 4;
        int inW = 4;

        MaxPooling2D4 maxPooling2D4 = new MaxPooling2D4(2, inC, inH, inW);

        // 2 表示batch size = 2
        DoubleMatrix x = DoubleMatrix.linspace(1, 2 * inC * inH * inW, 2 * inC * inH * inW);
        x.reshape(inC, inC * inH * inW);

        System.out.println(x.toString("%.4f", "[", "]", ", ", "\n"));
        /*
        [1.0000, 3.0000, 5.0000, 7.0000, 9.0000, 11.0000,
        2.0000, 4.0000, 6.0000, 8.0000, 10.0000, 12.0000,
         */
        DoubleMatrix y = maxPooling2D4.calc(x);

        System.out.println(y.toString("%.4f", "[", "]", ", ", "\n"));
        /*
        [11.0000, 15.0000, 27.0000, 31.0000, 43.0000, 47.0000, 59.0000, 63.0000
        12.0000, 16.0000, 28.0000, 32.0000, 44.0000, 48.0000, 60.0000, 64.0000]
         */
    }
}
