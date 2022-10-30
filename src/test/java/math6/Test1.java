package math6;

import com.dzb.math.data.tuple.Tuple;
import com.dzb.math.data.tuple.Tuple2;
import com.dzb.math.data.tuple.Tuple4;
import org.junit.Test;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-06-03 10:16
 */
public class Test1 {

    @Test
    public void test1() {
        Tuple tuple2 = new Tuple2<>(12, "abc");
        Object[] array = tuple2.getArray();
        String str = tuple2.toString();
        Object o0 = tuple2.get(0);
        Object o1 = tuple2.get(1);
        int size = tuple2.size();
        tuple2.set(0, 23);
        tuple2.set(1, "123");
        Object o01 = tuple2.get(0);
        Object o11 = tuple2.get(1);
        System.out.println(tuple2.toString());
        tuple2.update(1, 3);
        // tuple2.update(1, 2, 3);
        System.out.println(tuple2.toString());
    }

    @Test
    public void test2() {
        Tuple4<Integer, String, Double, Long> tuple4 = new Tuple4<>(12, "abc", 12.0, 10L);
        System.out.println(tuple4._0());
        System.out.println(tuple4._3());
        tuple4._1("xyz");
        System.out.println(tuple4._1());
    }

    @Test
    public void test3() {
        Tuple4<Integer, String, Double, Long> tuple4 = new Tuple4<>(12, "abc", 12.0, 10L);
        System.out.println(tuple4.get(3));
    }
}
