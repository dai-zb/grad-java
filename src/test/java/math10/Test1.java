package math10;

import com.dzb.math.auto2.Constant;
import com.dzb.math.auto2.Variable;
import com.dzb.math.auto2.func.toScalar.SumFunc;
import com.dzb.math.auto3.module.Conv3D;
import org.jblas.DoubleMatrix;
import org.junit.Test;

public class Test1 {
    @Test
    public void test1() {
        int inC = 2;
        int kernelC = 4;
        Conv3D conv3D = new Conv3D(2, inC, 4, 4, kernelC, 1);
        DoubleMatrix w = DoubleMatrix.ones(inC * 3 * 3, kernelC);
        w.put(4, 0);
        conv3D.getParams()[0].setValue(w);


        DoubleMatrix x = DoubleMatrix.linspace(1, 2 * 2 * 4 * 4, 2 * 2 * 4 * 4);
        x.reshape(inC, 2 * 4 * 4);

        System.out.println(x.toString("%.4f", "[", "]", ", ", "\n"));
        /*
        [1.0000, 3.0000, 5.0000, 7.0000, 9.0000, 11.0000,
        2.0000, 4.0000, 6.0000, 8.0000, 10.0000, 12.0000,
         */
        Constant X = new Constant(x);
        Variable Y = conv3D.build(X);

        Variable l = new SumFunc(Y).toVar();
        l.forward();

        System.out.println(Y.getValue().toString("%.4f", "[", "]", ", ", "\n"));
        /*
        [475.0000, 509.0000, 611.0000, 645.0000, 486.0000, 522.0000, 630.0000, 666.0000, 486.0000, 522.0000, 630.0000, 666.0000, 486.0000, 522.0000, 630.0000, 666.0000
        492.0000, 526.0000, 628.0000, 662.0000, 504.0000, 540.0000, 648.0000, 684.0000, 504.0000, 540.0000, 648.0000, 684.0000, 504.0000, 540.0000, 648.0000, 684.0000]
         */
        System.out.println();
    }

    @Test
    public void test2() {
        int inC = 2;
        int kernelC = 4;
        Conv3D conv3D = new Conv3D(2, inC, 4, 4, kernelC, 1);
        DoubleMatrix w = DoubleMatrix.ones(inC * 3 * 3, kernelC);
        w.put(4, 0);
        conv3D.getParams()[0].setValue(w);

        DoubleMatrix x = DoubleMatrix.linspace(1, 2 * 2 * 4 * 4, 2 * 2 * 4 * 4);
        x.reshape(inC, 2 * 4 * 4);

        System.out.println(x.toString("%.4f", "[", "]", ", ", "\n"));
        /*
        [1.0000, 3.0000, 5.0000, 7.0000, 9.0000, 11.0000,
        2.0000, 4.0000, 6.0000, 8.0000, 10.0000, 12.0000,
         */

        DoubleMatrix y = conv3D.calc(x);

        System.out.println(y.toString("%.4f", "[", "]", ", ", "\n"));
        /*
        [475.0000, 509.0000, 611.0000, 645.0000, 486.0000, 522.0000, 630.0000, 666.0000, 486.0000, 522.0000, 630.0000, 666.0000, 486.0000, 522.0000, 630.0000, 666.0000
        492.0000, 526.0000, 628.0000, 662.0000, 504.0000, 540.0000, 648.0000, 684.0000, 504.0000, 540.0000, 648.0000, 684.0000, 504.0000, 540.0000, 648.0000, 684.0000]
         */
        System.out.println();
    }
}
