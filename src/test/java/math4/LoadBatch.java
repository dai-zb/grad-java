package math4;

import com.dzb.math.utils.LoadData;
import com.dzb.math.data.tupleSimple.SmpTuple2;
import com.dzb.math.data.tupleSimple.SmpTuple3;
import org.jblas.DoubleMatrix;


public class LoadBatch {
    private int[] y;
    private double[][] x;

    private int cnt = 0;

    public void clear() {
        cnt = 0;
    }

    public LoadBatch(String path) {
        SmpTuple2<double[][], int[]> data = LoadData.load(path);
        y = data._1;
        x = data._0;
    }

    public SmpTuple3<DoubleMatrix, DoubleMatrix, int[]> load() {
        return load(250);
    }

    public SmpTuple3<DoubleMatrix, DoubleMatrix, int[]> load(int batchSize) {
        if (cnt >= y.length) {
            return null;
        }
        int[] y1 = LoadData.split(y, cnt, batchSize);
        double[][] y11 = LoadData.expand(y1, 10);

        double[][] x1 = LoadData.split(x, cnt, batchSize);

        cnt += batchSize;

        return new SmpTuple3<>(new DoubleMatrix(x1), new DoubleMatrix(y11), y1);
    }
}
