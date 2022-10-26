package math3;

import com.dzb.math.auto2.Constant;
import com.dzb.math.auto2.Variable;
import com.dzb.math.auto2.func.matrix.DotFunc;
import com.dzb.math.auto2.func.func.ReLUFunc;
import com.dzb.math.auto2.func.toScalar.SumFunc;
import org.jblas.DoubleMatrix;
import org.junit.Test;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-05-25 13:38
 */
public class Test4_ReLU_sum {

    @Test
    public void test1() {
        double[][] x = {
                {-6.0000, -5.5000, -5.0000, -4.5000, -4.0000},
                {-3.5000, -3.0000, -2.5000, -2.0000, -1.5000},
                {-1.0000, -0.5000, 0.0000, 0.5000, 1.0000},
                {1.5000, 2.0000, 2.5000, 3.0000, 3.5000},
                {4.0000, 4.5000, 5.0000, 5.5000, 6.0000}
        };
        Variable X = new Variable(new DoubleMatrix(x));

        ReLUFunc reLUFunc = new ReLUFunc(X);
        Variable U = new Variable(reLUFunc);

        Constant A = new Constant(DoubleMatrix.ones(1,5));
        Constant B = new Constant(DoubleMatrix.ones(5,1));

        DotFunc dotFunc1 = new DotFunc(A, U);
        Variable Y = new Variable(dotFunc1);

        DotFunc dotFunc2 = new DotFunc(Y, B);
        Variable Z = new Variable(dotFunc2);

        Z.forward();

        System.out.println("U Value");
        System.out.println(U.getValue().toString("%.4f", "[", "]", ", ", "\n"));
        /*
        [-0.0000, -0.0000, -0.0000, -0.0000, -0.0000
        -0.0000, -0.0000, -0.0000, -0.0000, -0.0000
        -0.0000, -0.0000, 0.0000, 0.5000, 1.0000
        1.5000, 2.0000, 2.5000, 3.0000, 3.5000
        4.0000, 4.5000, 5.0000, 5.5000, 6.0000]
         */

        System.out.println("Z Value");
        System.out.println(Z.getValue().toString("%.4f", "[", "]", ", ", "\n"));
        // [39.0000]

        Z.backward();

        System.out.println("X Grad");
        System.out.println(X.getGrad().toString("%.4f", "[", "]", ", ", "\n"));
        /*
        [0.0000, 0.0000, 0.0000, 0.0000, 0.0000
        0.0000, 0.0000, 0.0000, 0.0000, 0.0000
        0.0000, 0.0000, 1.0000, 1.0000, 1.0000
        1.0000, 1.0000, 1.0000, 1.0000, 1.0000
        1.0000, 1.0000, 1.0000, 1.0000, 1.0000]
         */

    }

    @Test
    public void test2() {
        double[][] x = {
                {-6.0000, -5.5000, -5.0000, -4.5000, -4.0000},
                {-3.5000, -3.0000, -2.5000, -2.0000, -1.5000},
                {-1.0000, -0.5000, 0.0000, 0.5000, 1.0000},
                {1.5000, 2.0000, 2.5000, 3.0000, 3.5000},
                {4.0000, 4.5000, 5.0000, 5.5000, 6.0000}
        };
        Variable X = new Variable(new DoubleMatrix(x));

        ReLUFunc reLUFunc = new ReLUFunc(X);
        Variable U = new Variable(reLUFunc);

        SumFunc sumFunc = new SumFunc(U);

        Variable Z = new Variable(sumFunc);

        Z.forward();

        System.out.println("U Value");
        System.out.println(U.getValue().toString("%.4f", "[", "]", ", ", "\n"));
        /*
        [-0.0000, -0.0000, -0.0000, -0.0000, -0.0000
        -0.0000, -0.0000, -0.0000, -0.0000, -0.0000
        -0.0000, -0.0000, 0.0000, 0.5000, 1.0000
        1.5000, 2.0000, 2.5000, 3.0000, 3.5000
        4.0000, 4.5000, 5.0000, 5.5000, 6.0000]
         */

        System.out.println("Z Value");
        System.out.println(Z.getValue().toString("%.4f", "[", "]", ", ", "\n"));
        // [39.0000]

        Z.backward();

        System.out.println("X Grad");
        System.out.println(X.getGrad().toString("%.4f", "[", "]", ", ", "\n"));
        /*
        [0.0000, 0.0000, 0.0000, 0.0000, 0.0000
        0.0000, 0.0000, 0.0000, 0.0000, 0.0000
        0.0000, 0.0000, 1.0000, 1.0000, 1.0000
        1.0000, 1.0000, 1.0000, 1.0000, 1.0000
        1.0000, 1.0000, 1.0000, 1.0000, 1.0000]
         */
    }
}
