package math2;

import com.dzb.math.auto2.Constant;
import com.dzb.math.auto2.Variable;
import com.dzb.math.auto2.lossFunc.SoftmaxCrossEntropyLossFunc;
import org.jblas.DoubleMatrix;
import org.junit.Test;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-05-24 15:37
 */
public class Test9_softmax_crossEntropy_6 {
    @Test
    public void test6() {
        double[][] d = {
                {0.4469, 0.7072, 1.2886},
                {0.0334, -1.6490, -0.8464},
                {1.7588, 0.2174, 1.6029},
                {-0.6278, -1.3048, -0.6514},
                {-0.8362, -0.5904, 0.2429}
        };
        double[][] d2 = {
                {0., 0., 1.},
                {0., 1., 0.},
                {1., 0., 0.},
                {0., 0., 1.},
                {0., 1., 0.}
        };

        DoubleMatrix x = new DoubleMatrix(d);
        DoubleMatrix y_bar = new DoubleMatrix(d2);

        Variable X = new Variable(x);
        Constant Y_bar = new Constant(y_bar);

        SoftmaxCrossEntropyLossFunc SoftmaxCrossEntropyLossFunc = new SoftmaxCrossEntropyLossFunc(X, Y_bar);
        Variable l = new Variable(SoftmaxCrossEntropyLossFunc);

        l.forward();

        System.out.println("l value");
        System.out.println(l.getValue().toString("%.4f", "[", "]", ", ", "\n"));
        // [1.1818]

        l.backward();

        System.out.println("X Grad");
        System.out.println(X.getGrad().toString("%.4f", "[", "]", ", ", "\n"));
        /*
        X Grad
        [0.0433, 0.0562, -0.0995
        0.1249, -0.1768, 0.0518
        -0.1034, 0.0207, 0.0827
        0.0805, 0.0409, -0.1214
        0.0383, -0.1510, 0.1127]
         */
    }
}
