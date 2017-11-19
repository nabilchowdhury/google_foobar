import java.math.BigInteger;

/**
 * Created by Nabillionaire on 8/29/17.
 */
public class Q33 {

    static BigInteger zero = new BigInteger("0");
    static BigInteger one = new BigInteger("1");

    public static boolean gcd(BigInteger x, BigInteger y) {
        while (!y.equals(zero)) {
            BigInteger t = new BigInteger(y.toString());
            y = x.mod(y);
            x = t;
        }
        return x.equals(one);
    }

    public static BigInteger getCount(BigInteger smaller, BigInteger bigger, BigInteger count) {
        if (smaller.equals(zero)) return count;
        if (smaller.equals(one)) return count.add(bigger.subtract(smaller));

        count = count.add(bigger.divide(smaller));
        bigger = bigger.mod(smaller);
        return getCount(bigger, smaller, count);
    }


    public static String answer(String M, String F) {
        boolean possible = gcd(new BigInteger(M), new BigInteger(F));

        if (!possible) return "impossible";

        BigInteger bigger = new BigInteger(M.compareTo(F) > 0 ? M : F);
        BigInteger smaller = new BigInteger(M.compareTo(F) > 0 ? F : M);

        BigInteger count = getCount(smaller, bigger, new BigInteger("0"));
        return count.toString();
    }

    public static void main(String[] args) {

        System.out.println(answer("24574285724894913843243542534", "442597834275982373243253"));
    }
}
