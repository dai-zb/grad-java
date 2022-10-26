package math3;

import com.dzb.math.utils.Csv;
import com.dzb.math.utils.LoadData;
import com.dzb.math.data.tupleSimple.SmpTuple2;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class Test2_csv {

    @Test
    public void test1() {
        Csv<Integer[]> csv = new Csv<>("..\\DataSet\\mnist\\csv\\test.csv",
                str -> {
                    String[] split = str.split(",");
                    Integer[] ints = new Integer[split.length];
                    for (int i = 0; i < split.length; i++) {
                        ints[i] = Integer.parseInt(split[i]);
                    }
                    return ints;
                });
        List<Integer[]> list = csv.asList();
        int rows = list.size();
        int columns = list.get(0).length;
        int[] y = new int[rows];
        double[][] x = new double[rows][columns - 1];

        for (int i = 0; i < rows; i++) {
            y[i] = list.get(i)[0];
            for (int j = 1; j < columns - 1; j++) {
                x[i][j] = list.get(i)[j];
            }
        }
        System.out.println(y.length);
        System.out.println(x.length);
    }

    @Test
    public void test2() {
        SmpTuple2<double[][], int[]> data = LoadData.load("..\\DataSet\\mnist\\csv\\test.csv");
        int[] y = data._1;
        int[] y1 = LoadData.split(y, 0, 5);
        int[] y2 = LoadData.split(y, 5, 5);
        System.out.println(Arrays.toString(y1));
        System.out.println(Arrays.toString(y2));

        double[][] y11 = LoadData.expand(y1, 10);
        System.out.println(Arrays.deepToString(y11));

        double[][] x = data._0;
        double[][] x1 = LoadData.split(x, 0, 10);
        for (double[] doubles : x1) {
            System.out.println(sum(doubles));
        }
    }

    private double sum(double[] x) {
        double y = 0;
        for (double v : x) {
            y += v;
        }
        return y;
    }
}