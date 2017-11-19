import java.math.BigInteger;
import java.util.HashMap;

/**
 * Created by Nabillionaire on 8/28/17.
 */
public class Q32 {


    public static int answer(String n) {
        BigInteger bi = new BigInteger(n);
        int count = 0;
        BigInteger one = new BigInteger("1");

        while (!bi.toString().equals("1")) {
            if (!bi.testBit(0)) {
                bi = bi.shiftRight(1);
            } else if ((bi.testBit(0) && !bi.testBit(1)) || bi.intValue() == 3) {
                bi = bi.subtract(one);
            } else {
                bi = bi.add(one);
            }

            count++;
        }

        return count;
    }



    public static void main(String[] args) {
        System.out.println(answer("4257838498954567894567890567890508490584495948594545274832747"));
    }

}
