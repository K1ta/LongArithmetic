package source;

public class lArithmetic {

    public static LongNumber sum(LongNumber a, LongNumber b) {
        int maxLength = Math.max(a.getLength(), b.getLength());
        if (a.getBitCount() - maxLength * 64 == 0 || b.getBitCount() - maxLength * 64 == 0) {
            maxLength++;
        }
        LongNumber res = new LongNumber(maxLength);
        int n = Math.max(a.getBitCount(), b.getBitCount());
        long temp = 0;
        for (int i = 0; i < n + 1; i++) {
            res.setBit(i, a.getBit(i) ^ b.getBit(i) ^ temp);
            temp = (a.getBit(i) & b.getBit(i)) | (a.getBit(i) & temp) | (b.getBit(i) & temp);
        }
        res.countNumbers();
        return res;
    }

    public static LongNumber AND(LongNumber a, long b)
    {
        LongNumber res = new LongNumber(a.getLength());
        for(int i = 0; i < res.getLength(); i++)
        {
            res.setNumber(i, a.getNumber(i) & b);
        }
        return res;
    }

    public static LongNumber OR(LongNumber a, LongNumber b)
    {
        LongNumber res = new LongNumber(Math.max(a.getLength(), b.getLength()));
        for(int i = 0; i < res.getLength(); i++)
        {
            res.setNumber(i, a.getNumber(i) | b.getNumber(i));
        }
        return res;
    }
}