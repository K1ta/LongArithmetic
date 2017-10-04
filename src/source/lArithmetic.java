package source;

public class lArithmetic {

    /**
     * возвращает сумму чисел a и b
     * @param a первое слагаемое в формате LongNumber
     * @param b второе слагаемое в формате LongNumber
     * @return сумма a и b
     */
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

    /**
     * возвращает сумму чисел a и b с обнулением остатка через каждые zeroing битов.
     * @param a первое слагаемое в формате LongNumber
     * @param b второе слагаемое в формате LongNumber
     * @param zeroing обнуление остатка через каждые zeroing битов
     * @return сумма a и b с обнулением остатка каждые zeroing битов
     */
    public static LongNumber sum(LongNumber a, LongNumber b, int zeroing)
    {
        int maxLength = Math.max(a.getLength(), b.getLength());
        LongNumber res = new LongNumber(maxLength);
        int n = Math.max(a.getBitCount(), b.getBitCount());
        long temp = 0;
        for (int i = 0; i < n; i++) {
            if(i % zeroing == 0)
            {
                temp = 0;
            }
            res.setBit(i, a.getBit(i) ^ b.getBit(i) ^ temp);
            temp = (a.getBit(i) & b.getBit(i)) | (a.getBit(i) & temp) | (b.getBit(i) & temp);
        }
        res.countNumbers();
        return res;
    }

    /**
     * возвращает логическое "И" каждого элемента LongNumber a с long b
     * @param a первый аргумент в формате LongNumber
     * @param b второй аргумент в формате long
     * @return логическое умножение каждого элемента LongNumber a с long b
     */
    public static LongNumber AND(LongNumber a, long b)
    {
        LongNumber res = new LongNumber(a.getLength());
        for(int i = 0; i < res.getLength(); i++)
        {
            res.setNumber(i, a.getNumber(i) & b);
        }
        return res;
    }

    /**
     * возвращает логическую сумму a и b
     * @param a первый аргумент в формате LongNumber
     * @param b второй аргумент в формате LongNumber
     * @return логическая сумма двух аргументов
     */
    public static LongNumber OR(LongNumber a, LongNumber b)
    {
        LongNumber res = new LongNumber(Math.max(a.getLength(), b.getLength()));
        for(int i = 0; i < res.getLength(); i++)
        {
            res.setNumber(i, a.getNumber(i) | b.getNumber(i));
        }
        return res;
    }

    /**
     * возвращает логическое сложение по модулю 2 двух аргументов
     * @param a первый аргумент в формате LongNumber
     * @param b второй аргумент в формате LongNumber
     * @return логическое сложение по модулю два двух аргументов
     */
    public static LongNumber XOR(LongNumber a, long b)
    {
        LongNumber res = new LongNumber(a.getLength());
        for(int i = 0; i < res.getLength(); i++)
        {
            res.setNumber(i, a.getNumber(i) ^ b);
        }
        return res;
    }
}