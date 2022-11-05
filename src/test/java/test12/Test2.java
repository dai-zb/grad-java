package test12;

import com.dzb.math.auto2.Constant;
import com.dzb.math.auto2.Variable;
import com.dzb.math.auto2.func.Conv3DFunc;
import com.dzb.math.auto2.func.toScalar.SumFunc;
import com.dzb.math.auto2.lossFunc.MeanSquareLossFunc;
import com.dzb.math.auto3.optimizer.Sgd;
import org.jblas.DoubleMatrix;
import org.junit.Test;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-06-04 12:33
 */
public class Test2 {

    @Test
    public void test1() {
        int inC = 2;
        int kernelC = 4;

        DoubleMatrix w = DoubleMatrix.ones(inC * 3 * 3, kernelC);
        w.put(4, 0);
        Variable W = new Variable(w);

        DoubleMatrix x = DoubleMatrix.linspace(1, 2 * 2 * 4 * 4, 2 * 2 * 4 * 4);
        x.reshape(inC, 2 * 4 * 4);

        System.out.println(x.toString("%.4f", "[", "]", ", ", "\n"));
        /*
        [1.0000, 3.0000, 5.0000, 7.0000, 9.0000, 11.0000,
        2.0000, 4.0000, 6.0000, 8.0000, 10.0000, 12.0000,
         */
        Constant X = new Constant(x);

        Variable Y = new Conv3DFunc(X, W, 2, 2, 4, 4).toVar();

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
        double[][] x = new double[][]{
                {1., 2., 3., 4., 5., 6., 7., 8., 9., 10.,
                        11., 12., 13., 14., 15., 16., 17., 18., 19., 20.,
                        21., 22., 23., 24., 25., 26., 27., 28., 29., 30.,
                        31., 32., 33., 34., 35., 36., 37., 38., 39., 40.,
                        41., 42., 43., 44., 45., 46., 47., 48., 49., 50.}};
        // B C H W = 1 2 5 5

        double[][] y = new double[][]{{
                1., 2., 3., 4., 5., 6., 7., 8., 9.,
                10., 11., 12., 13., 14., 15., 16., 17., 18}};
        // B C H W = 1 2 3 3

        Variable X = new Constant(new DoubleMatrix(x));
        Constant Y_BAR = new Constant(new DoubleMatrix(y));

        DoubleMatrix w = DoubleMatrix.randn(2 * 3 * 3, 2);
        w.muli(0.00004);
        Variable W = new Variable(w);

        Variable Y = new Conv3DFunc(X, W, 1, 2, 5, 5).toVar();

        Variable l = new MeanSquareLossFunc(Y, Y_BAR).toVar();

        Sgd sgd = new Sgd(0.000005);

        sgd.add(W, X);
        sgd.add(null, Y);
        sgd.add(null, l);

        for (int i = 0; i < 1000; i++) {
            // 正向传播
            l.forward();
            double loss = l.getValue().scalar();
            System.out.println(i + "  loss: " + loss);
            // 反向传播
            l.backward();
            sgd.step();
            sgd.clear();
        }

        l.forward();
        System.out.println(Y.getValue().toString("%.4f", "[", "]", ", ", "\n"));
        System.out.println(W.getValue().toString("%.4f", "[", "]", ", ", "\n"));
    }
}
