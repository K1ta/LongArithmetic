package source;

public class lArithmetic {

    public static LongNumber AND(LongNumber a, LongNumber b) {
        return new LongNumber(a.numbers[0] & b.numbers[0],
                a.numbers[1] & b.numbers[1],
                a.numbers[2] & b.numbers[2],
                a.numbers[3] & b.numbers[3]);
    }

    public static LongNumber OR(LongNumber a, LongNumber b) {
        return new LongNumber(a.numbers[0] | b.numbers[0],
                a.numbers[1] | b.numbers[1],
                a.numbers[2] | b.numbers[2],
                a.numbers[3] | b.numbers[3]);
    }

    public static LongNumber XOR(LongNumber a, LongNumber b) {
        return new LongNumber(a.numbers[0] ^ b.numbers[0],
                a.numbers[1] ^ b.numbers[1],
                a.numbers[2] ^ b.numbers[2],
                a.numbers[3] ^ b.numbers[3]);
    }

    public static boolean isZero(LongNumber l) {
        return ((l.numbers[3] == 0) && (l.numbers[2] == 0) && (l.numbers[1] == 0) && (l.numbers[0] == 0));
    }

    public static LongNumber leftOffset(LongNumber l) {
        l.leftOffset(0, 1);
        if (l.getBit(191) == 1) {
            l.setBit(192);
        }
        l.leftOffset(1, 1);
        if (l.getBit(127) == 1) {
            l.setBit(128);
        }
        l.leftOffset(2, 1);
        if (l.getBit(63) == 1) {
            l.setBit(64);
        }
        l.leftOffset(3, 1);
        return l;
    }

    public static LongNumber sum(LongNumber a, LongNumber b) {
        LongNumber o1;
        LongNumber o2;
        while ((!isZero(AND(a,b)))) {
            o1 = XOR(a, b);
            o2 = leftOffset(lArithmetic.AND(a,b));
            a = o1;
            b = o2;
        }
        return OR(a,b);
    }
}