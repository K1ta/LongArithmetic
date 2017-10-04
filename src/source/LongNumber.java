package source;

public class LongNumber {
    private long[] numbers;
    private int length;
    private int bitCount;

    public LongNumber(int n) {
        numbers = new long[n];
        length = n;
        countNumbers();
    }

    public LongNumber(int n, long value) {
        numbers = new long[n];
        length = n;
        numbers[length - 1] = value;
        countNumbers();
    }

    public LongNumber(LongNumber l)
    {
        numbers = new long[l.length];
        length = l.length;
        for(int i = 0; i < length; i++)
        {
            numbers[i] = l.numbers[i];
        }
        bitCount = l.bitCount;
    }

    public int getLength() {
        return length;
    }

    public int getBitCount() {
        return bitCount;
    }

    public long getBit(int n) {
        return (n > bitCount - 1) ? 0 : ((numbers[length - (n / 64) - 1] >> (n % 64)) & 1L);
    }

    public void setBit(int n, long bit) {
        numbers[length - (n / 64) - 1] &= ~(1L << (n % 64));
        numbers[length - (n / 64) - 1] |= (bit << (n % 64));
    }

    public long getNumber(int n)
    {
        return (n >= length) ? 0 : numbers[n];
    }

    public void setNumber(int n, long number)
    {
        if(n < length) {
            numbers[n] = number;
        }
    }

    //сдвиг влево на 1 разряд,справа заносится bit
    public void leftOffset(long bit)
    {
        if(length == 0)
        {
            return;
        }
        for(int i = 0; i < length - 1; i++)
        {
            numbers[i] = numbers[i] << 1;
            setBit(64*(length - 1 - i), getBit(64 * (length - 1 - i) - 1));
        }
        numbers[length - 1] = numbers[length - 1] << 1;
        setBit(0,bit);
    }

    //сдвиг вправо на 1 разряд, слева заносится bit
    public void rightOffset(long bit)
    {
        if(length == 0)
        {
            return;
        }
        for(int i = length - 1; i > 0; i--)
        {
            numbers[i] = numbers[i] >> 1;
            setBit(64*(length - i) - 1, getBit(64 * (length - i)));
        }
        numbers[0] = numbers[0] >> 1;
        setBit((64 * length) - 1,bit);
    }

    //сдвиг вправо на n разрядов, слева заносится bit
    public void rightOffset(int n, long bit)
    {
        for(int i = 0; i < n; i++)
        {
            rightOffset(bit);
        }
    }

    //подсчет значащих битов в числе
    public void countNumbers() {
        int i = length * 64 - 1;
        while (i >= 0 && ((numbers[length - (i / 64) - 1] >> (i % 64)) & 1L) == 0) {
            i--;
        }
        bitCount = (i == -1) ? 0 : (i + 1);
    }

    //добавление к каждому лонгу числа l
    public void add(long l)
    {
        for(int i = 0; i < length; i++)
        {
            numbers[i] += l;
        }
    }

    //перевод в двоично-десятичную форму, затем в десятичную и печать на экран
    public void print() {
        //Определяем количество битов для двоично-десятичного кода
        int tetradsBitCount = (int)Math.ceil((bitCount * 0.3));
        //Тетрады храним в LongNumber
        LongNumber tetrads = new LongNumber(tetradsBitCount / 16 + Math.min(1, tetradsBitCount % 16));
        //Прибавляем к каждой тетраде тройку
        for(int i = 0; i < tetrads.length; i++)
        {
            tetrads.setNumber(i, 3689348814741910323L);
        }
        //Главный цикл
        for(int i = bitCount - 1; i >= 0; i--)
        {
            //создаем LongNumber для коррекции
            LongNumber correction = lArithmetic.AND(tetrads,-8608480567731124088L);
            correction = lArithmetic.XOR(correction, -8608480567731124088L);
            //сдвигаем тетрады и толкаем в них бит из числа
            tetrads.leftOffset(getBit(i));
            LongNumber _correction = new LongNumber(correction);
            _correction.rightOffset(2, 0);
            //определяем, нужна ли коррекция
            correction = lArithmetic.OR(correction, _correction);
            correction.add(3689348814741910323L);
            correction.countNumbers();
            tetrads.countNumbers();
            //корректируем
            tetrads = lArithmetic.sum(tetrads, correction, 4);
        }
        //отнимаем от каждой тетрады тройку
        for(int i = 0; i < tetrads.length; i++)
        {
            tetrads.numbers[i] -= 3689348814741910323L;
        }
        //вывод
        //пересчитываем количество бит
        tetrads.countNumbers();
        long res = 0;
        for(int i = tetrads.bitCount - 1; i >= 0; i--)
        {
            res += (tetrads.getBit(i) * Math.pow(2, i % 4));
            if(i % 4 == 0)
            {
                System.out.print(res);
                res = 0;
            }
        }
        //обрабатываем отдельно случай, если значащих битов нет
        if(tetrads.bitCount == 0)
        {
            System.out.print(0);
        }
        System.out.println();
    }
}
