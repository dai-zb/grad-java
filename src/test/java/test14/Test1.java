package test14;

import com.dzb.math.data.tupleSimple.SmpTuple3;
import math4.LoadBatch;
import org.jblas.DoubleMatrix;

public class Test1 {
    public static void main(String[] args) {

        // LoadBatch trainBatch = new LoadBatch("..\\DataSet\\mnist\\csv\\train.csv");
        LoadBatch trainBatch = new LoadBatch("..\\DataSet\\fashion-mnist\\csv\\train.csv");
        // LoadBatch testBatch = new LoadBatch("..\\DataSet\\mnist\\csv\\test.csv");
        LoadBatch testBatch = new LoadBatch("..\\DataSet\\fashion-mnist\\csv\\test.csv");

        int batchSize = 1000;

        Net model = new Net(batchSize, 784, 50, 10, 0.9);
        // 训练
        for (int j = 0; j < 15; j++) { // epoch
            trainBatch.clear();
            System.out.println("epoch " + j + "   进行训练==========");
            for (int i = 0; i < 60000 / batchSize; i++) {
                SmpTuple3<DoubleMatrix, DoubleMatrix, int[]> tuple3 = trainBatch.load(batchSize);
                if (tuple3 != null) {
                    DoubleMatrix x = tuple3._0;
                    DoubleMatrix y = tuple3._1;
                    int[] yInt = tuple3._2;
                    model.train(x, y, yInt);
                } else {
                    System.out.println("new epoch " + j + " =============================================");
                    trainBatch.clear();
                    break;
                }
            }
            // 进行测试(训练集)
            trainBatch.clear();
            System.out.println("epoch " + j + "   进行测试(训练集)==========");
            for (int i = 0; i < 60000 / (batchSize); i++) {
                SmpTuple3<DoubleMatrix, DoubleMatrix, int[]> tuple32 = trainBatch.load(batchSize);
                if (tuple32 != null) {
                    DoubleMatrix x = tuple32._0;
                    DoubleMatrix y = tuple32._1;
                    int[] yInt = tuple32._2;
                    model.test(x, y, yInt);
                } else {
                    break;
                }
            }

            // 进行测试(测试集)
            testBatch.clear();
            System.out.println("epoch " + j + "   进行测试(测试集)==========");
            for (int i = 0; i < 10000 / (batchSize); i++) {
                SmpTuple3<DoubleMatrix, DoubleMatrix, int[]> tuple32 = testBatch.load(batchSize);
                if (tuple32 != null) {
                    DoubleMatrix x = tuple32._0;
                    DoubleMatrix y = tuple32._1;
                    int[] yInt = tuple32._2;
                    model.test(x, y, yInt);
                } else {
                    break;
                }
            }
        }
    }
}
