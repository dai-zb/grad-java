package math2;

import com.dzb.math.auto2.Variable;
import com.dzb.math.auto2.func.matrix.AddFunc;
import com.dzb.math.auto2.func.matrix.DotFunc;
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
public class Test1_Variable {

    @Test
    public void test1() {
        double[][] d1 = new double[][]{
                {1, 2, 3},
                {1, 2, 3},
                {1, 2, 3}};
        double[][] d2 = new double[][]{
                {3, 2, 1},
                {1, 2, 3},
                {3, 2, 1}};

        DoubleMatrix matrix1 = new DoubleMatrix(d1);
        DoubleMatrix matrix2 = new DoubleMatrix(d2);

        Variable var1 = new Variable(matrix1);
        Variable var2 = new Variable(matrix2);

        AddFunc addFunc = new AddFunc(var1, var2);
        Variable variable = new Variable(addFunc);

        variable.forward();
        DoubleMatrix value = variable.getValue();
        System.out.println(value.toString("%.3f", "[", "]", ", ", "\n"));

        variable.clear();
        value = variable.getValue();
        System.out.println(value);
    }

    @Test
    public void test2() {
        double[][] d1 = new double[][]{
                {1, 2, 3},
                {1, 2, 3},
                {1, 2, 3}};
        double[][] d2 = new double[][]{
                {3, 2, 1},
                {1, 2, 3},
                {3, 2, 1}};
        double[][] d3 = new double[][]{
                {1, 0, 1},
                {0, 1, 0},
                {1, 0, 1}};


        DoubleMatrix matrix1 = new DoubleMatrix(d1);
        DoubleMatrix matrix2 = new DoubleMatrix(d2);
        DoubleMatrix matrix3 = new DoubleMatrix(d3);

        Variable var1 = new Variable(matrix1);
        Variable var2 = new Variable(matrix2);
        Variable var3 = new Variable(matrix3);

        AddFunc addFunc = new AddFunc(var1, var2);
        Variable variable = new Variable(addFunc);
        DotFunc dotFunc = new DotFunc(variable, var3);
        Variable variable2 = new Variable(dotFunc);

        variable2.forward();
        DoubleMatrix value = variable2.getValue();
        System.out.println(value.toString("%.3f", "[", "]", ", ", "\n"));

        variable2.clear();
        value = variable2.getValue();
        System.out.println(value);
    }
}
