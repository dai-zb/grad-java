package math2;

import com.dzb.math.auto2.Variable;
import com.dzb.math.auto2.func.matrix.AddFunc;
import com.dzb.math.auto2.func.matrix.DotFunc;
import com.dzb.math.auto2.func.toScalar.MeanSquareFunc;
import com.dzb.math.auto2.func.func.SigmoidFunc;
import org.jblas.DoubleMatrix;
import org.junit.Test;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-05-21 16:01
 */
public class Test5_backward_3 {

    @Test
    public void test3() {
        double[][] d1 = new double[][]{
                {0.4, 0.2, 0.3},
                {0.1, 0.4, 0.3},
                {0.1, 0.2, 0.5},
                {0.3, 0.6, 0.3},
                {0.8, 0.6, 0.5}};
        double[][] d2 = new double[][]{
                {3, 2, 5, 1},
                {1, 5, 9, 3},
                {4, 7, 2, 1}};
        double[][] d3 = new double[][]{
                {-1, 0, 1, -2},
                {-3, 0, -1, 0},
                {1, 2, 0, -1},
                {3, -2, 1, 2},
                {-3, 2, -1, 1}};

        Variable X = new Variable(new DoubleMatrix(d1));
        Variable W = new Variable(new DoubleMatrix(d2));
        Variable B = new Variable(new DoubleMatrix(d3));

        DotFunc dotFunc = new DotFunc(X, W);
        Variable Y = new Variable(dotFunc);

        AddFunc addFunc = new AddFunc(Y, B);
        Variable Z = new Variable(addFunc);

        SigmoidFunc sigmoidFunc = new SigmoidFunc(Z);
        Variable U = new Variable(sigmoidFunc);

        AddFunc addFunc1 = new AddFunc(U,B);
        Variable V = new Variable(addFunc1);

        MeanSquareFunc meanSquareFunc = new MeanSquareFunc(V);
        Variable l = new Variable(meanSquareFunc);

        l.forward();

        DoubleMatrix zValue = Z.getValue();
        System.out.println("Z value");
        System.out.println(zValue.toString("%.4f", "[", "]", ", ", "\n"));

        DoubleMatrix uValue = U.getValue();
        System.out.println("U value");
        System.out.println(uValue.toString("%.4f", "[", "]", ", ", "\n"));

        DoubleMatrix vValue = V.getValue();
        System.out.println("V value");
        System.out.println(vValue.toString("%.4f", "[", "]", ", ", "\n"));

        DoubleMatrix value = l.getValue();
        System.out.println("l");
        System.out.println(value);
        // [15.673512]

        l.backward();

        DoubleMatrix xGrad = X.getGrad();
        System.out.println("X Grad");
        System.out.println(xGrad.toString("%.4f", "[", "]", ", ", "\n"));
        /*
        [-0.1430, -0.3829, -0.1250
        -0.5626, -0.0426, -0.7420
        0.0918, 0.0152, 0.0819
        0.0118, 0.0015, -0.0316
        -0.2542, -0.0506, -0.3429]
         */

        DoubleMatrix wGrad = W.getGrad();
        System.out.println("W Grad");
        System.out.println(wGrad.toString("%.4f", "[", "]", ", ", "\n"));
        /*
        [-0.0917, 0.0009, 0.0028, -0.0445
        -0.1301, -0.0019, 0.0034, -0.0037
        -0.0963, 0.0017, 0.0077, -0.0421]
         */

        DoubleMatrix bGrad = B.getGrad();
        System.out.println("B Grad");
        System.out.println(bGrad.toString("%.4f", "[", "]", ", ", "\n"));
        /*
        [-0.0766, 0.3997, 0.8018, -0.8152
        -1.3062, 0.3999, -0.0099, 0.3793
        0.8107, 1.2010, 0.3990, -0.2246
        1.6040, -0.4193, 0.8001, 1.2095
        -0.9367, 1.2000, -0.0000, 0.8062]
         */

        DoubleMatrix yGrad = Y.getGrad();
        System.out.println("Y Grad");
        System.out.println(yGrad.toString("%.4f", "[", "]", ", ", "\n"));

        DoubleMatrix zGrad = Z.getGrad();
        System.out.println("Z Grad");
        System.out.println(zGrad.toString("%.4f", "[", "]", ", ", "\n"));

        DoubleMatrix uGrad = U.getGrad();
        System.out.println("U Grad");
        System.out.println(uGrad.toString("%.4f", "[", "]", ", ", "\n"));

        DoubleMatrix vGrad = V.getGrad();
        System.out.println("V Grad");
        System.out.println(vGrad.toString("%.4f", "[", "]", ", ", "\n"));

        DoubleMatrix lGrad = l.getGrad();
        System.out.println("l Grad");
        System.out.println(lGrad.toString("%.4f", "[", "]", ", ", "\n"));
    }
}