package math3;

import com.dzb.math.auto2.func.func.SoftmaxFunc;
import com.dzb.math.utils.MatrixFunction;
import org.jblas.DoubleMatrix;
import org.junit.Test;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-05-24 14:34
 */
public class Test1_softmax {
    @Test
    public void test1() {
        double[][] d = {{-0.7467, -0.8823, 0.4202},
                {0.5861, 0.7123, -0.4636},
                {0.2844, 0.0121, -0.4476},
                {-2.1594, 0.8123, 0.0543},
                {2.1404, 0.8639, 1.6508},
                {1.4372, 0.6566, -0.6754}};
        DoubleMatrix x = new DoubleMatrix(d);
        System.out.println(x.toString("%.4f", "[", "]", ", ", "\n"));

        DoubleMatrix y = MatrixFunction.softmax1(x);
        System.out.println(y.toString("%.4f", "[", "]", ", ", "\n"));
        /*
        [0.1966, 0.1717, 0.6316
         0.4025, 0.4566, 0.1409
         0.4459, 0.3396, 0.2145
         0.0337, 0.6580, 0.3083
         0.5286, 0.1475, 0.3239
         0.6333, 0.2901, 0.0766]
         */
    }

    @Test
    public void test2() {
        double[][] d = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12},
                {13, 14, 15},
                {16, 17, 18}};
        DoubleMatrix x = new DoubleMatrix(d);

            System.out.println(
                    SoftmaxFunc.partial(x, x)
                            .toString("%.4f", "[", "]", ", ", "\n"));
            /*
            [-13.0000, -24.0000, -33.0000
            -292.0000, -360.0000, -426.0000
            -1309.0000, -1488.0000, -1665.0000
            -3550.0000, -3894.0000, -4236.0000
            -7501.0000, -8064.0000, -8625.0000
            -13648.0000, -14484.0000, -15318.0000]
             */
    }
}
