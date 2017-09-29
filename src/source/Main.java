package source;

public class Main {

    public static void main(String[] args) {
        LongNumber a = new LongNumber(100);
        LongNumber b = new LongNumber(100);
        lArithmetic.sum(a, b).print();
    }

    public static LongNumber F(int n) {
        if (n < 3) {
            return new LongNumber(1);
        }
        LongNumber prev = new LongNumber(1);
        LongNumber cur = new LongNumber(1);
        LongNumber res = null;
        for (int i = 0; i < n; i++) {
            res = lArithmetic.sum(prev, cur);
            prev = cur;
            cur = res;
            //res.print();
        }
        return res;
    }
}
