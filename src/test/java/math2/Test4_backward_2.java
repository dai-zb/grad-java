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
public class Test4_backward_2 {

    @Test
    public void test2() {
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

        MeanSquareFunc meanSquareFunc = new MeanSquareFunc(U);
        Variable l = new Variable(meanSquareFunc);

        l.forward();

        l.forward();
        DoubleMatrix zValue = Z.getValue();
        System.out.println("Z value");
        System.out.println(zValue.toString("%.4f", "[", "]", ", ", "\n"));

        DoubleMatrix uValue = U.getValue();
        System.out.println("U value");
        System.out.println(uValue.toString("%.4f", "[", "]", ", ", "\n"));

        DoubleMatrix value = l.getValue();
        System.out.println("l");
        System.out.println(value);

        l.backward();
        // [3.254208]

        DoubleMatrix xGrad = X.getGrad();
        System.out.println("X Grad");
        System.out.println(xGrad.toString("%.4f", "[", "]", ", ", "\n"));

        DoubleMatrix wGrad = W.getGrad();
        System.out.println("W Grad");
        System.out.println(wGrad.toString("%.4f", "[", "]", ", ", "\n"));

        DoubleMatrix bGrad = B.getGrad();
        System.out.println("B Grad");
        System.out.println(bGrad.toString("%.4f", "[", "]", ", ", "\n"));

        DoubleMatrix yGrad = Y.getGrad();
        System.out.println("Y Grad");
        System.out.println(yGrad.toString("%.4f", "[", "]", ", ", "\n"));

        DoubleMatrix zGrad = Z.getGrad();
        System.out.println("Z Grad");
        System.out.println(zGrad.toString("%.4f", "[", "]", ", ", "\n"));

        DoubleMatrix uGrad = U.getGrad();
        System.out.println("U Grad");
        System.out.println(uGrad.toString("%.4f", "[", "]", ", ", "\n"));

        DoubleMatrix lGrad = l.getGrad();
        System.out.println("l Grad");
        System.out.println(lGrad.toString("%.4f", "[", "]", ", ", "\n"));

        System.out.println();
    }
}
