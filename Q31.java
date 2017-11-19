/**
 * Created by Nabillionaire on 8/28/17.
 */
public class Q31 {

    static int getXor(int upperBound) {
        if (upperBound < 0) return 0;
        int mod = upperBound % 4;
        switch(mod){
            case 0:
                return upperBound;
            case 1:
                return 1;
            case 2:
                return upperBound + 1;
            default:
                return 0;
        }
    }

    public static int answer(int start, int length) {
        int res = 0;
        for (int i = 0; i < length; i++) {
            res ^= getXor(start + length - 1 - i) ^ getXor(start - 1);
            start = start + length;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(answer(17, 10000));
    }

}
