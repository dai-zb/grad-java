package math8;

import com.dzb.math.data.tupleSimple.SmpTuple3;
import math4.LoadBatch;
import org.jblas.DoubleMatrix;
import org.junit.Test;

public class Test1 {
    @Test
    public void test1() {
        LoadBatch trainBatch = new LoadBatch("..\\DataSet\\mnist\\csv\\train.csv");
        LoadBatch testBatch = new LoadBatch("..\\DataSet\\mnist\\csv\\test.csv");

        Net model = new Net(250, 784, 256,10);
        // 训练
        for (int j = 0; j < 5; j++) { // epoch
            for (int i = 0; i < 60000 / 250; i++) {
                SmpTuple3<DoubleMatrix, DoubleMatrix, int[]> tuple3 = trainBatch.load();
                if (tuple3 != null) {
                    DoubleMatrix x = tuple3._0;
                    DoubleMatrix y = tuple3._1;
                    int[] yInt = tuple3._2;
                    model.train(x, y, yInt, 0.001);
                } else {
                    System.out.println("new epoch " + j + " =============================================");
                    trainBatch.clear();
                    break;
                }

                SmpTuple3<DoubleMatrix, DoubleMatrix, int[]> tuple32 = testBatch.load();
                if (tuple32 != null) {
                    DoubleMatrix x = tuple32._0;
                    int[] yInt = tuple32._2;
                    model.test(x, yInt);
                } else {
                    testBatch.clear();
                }
            }
        }
        // 进行测试
        testBatch.clear();
        for (int i = 0; i < 10000 / 250; i++) {
            SmpTuple3<DoubleMatrix, DoubleMatrix, int[]> tuple32 = testBatch.load();
            if (tuple32 != null) {
                DoubleMatrix x = tuple32._0;
                int[] yInt = tuple32._2;
                model.test(x, yInt);
            } else {
                break;
            }
        }
    }
}
