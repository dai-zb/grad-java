package math2;

import com.dzb.math.auto2.Variable;
import com.dzb.math.auto2.func.matrix.AddFunc;
import com.dzb.math.auto2.func.matrix.DotFunc;
import com.dzb.math.auto2.func.toScalar.MeanSquareFunc;
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
public class Test2_Variable {

    @Test
    public void test1() {
        double[][] d1 = new double[][]{
                {4, 2, 3},
                {1, 4, 3},
                {1, 2, 5}};
        double[][] d2 = new double[][]{
                {3, 2, 5},
                {1, 9, 3},
                {7, 2, 1}};
        double[][] d3 = new double[][]{
                {1, 0, 1},
                {0, 1, 0},
                {1, 0, 1}};

        Variable X = new Variable(new DoubleMatrix(d1));
        Variable W = new Variable(new DoubleMatrix(d2));
        Variable B = new Variable(new DoubleMatrix(d3));

        DotFunc dotFunc = new DotFunc(X, W);
        Variable Y = new Variable(dotFunc);

        AddFunc addFunc = new AddFunc(Y, B);
        Variable Z = new Variable(addFunc);

        MeanSquareFunc meanSquareFunc = new MeanSquareFunc(Z);
        Variable l = new Variable(meanSquareFunc);

        l.forward();
        DoubleMatrix value = l.getValue();
        System.out.println(value.toString("%.3f", "[", "]", ", ", "\n"));
        // 1033.222

        System.out.println();
    }
}
