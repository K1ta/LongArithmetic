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
        numbers[n] = number;
    }

    public void leftOffset(long bit)
    {
        if(length == 0)
        {
            return;
        }
        for(int i = 0; i < length - 1; i++)
        {
            numbers[i] = numbers[i] << 1;
            setBit(64*(length - 1 - i), 64 * (length - 1 - i) - 1);
        }
        numbers[length - 1] = numbers[length - 1] << 1;
        setBit(0,bit);
    }

    public void rightOffset(long bit)
    {
        if(length == 0)
        {
            return;
        }
        for(int i = length - 1; i > 0; i--)
        {
            numbers[i] = numbers[i] >> 1;
            setBit(64*(i - 1) - 1, 64 * (i - 1));
        }
        numbers[0] = numbers[0] >> 1;
        setBit(64 * (length - 1) - 1,bit);
    }

    public void rightOffset(int n, long bit)
    {
        for(int i = 0; i < n; i++)
        {
            rightOffset(bit);
        }
    }

    public void countNumbers() {
        int i = length * 64 - 1;
        while (i >= 0 && ((numbers[length - (i / 64) - 1] >> (i % 64)) & 1L) == 0) {
            i--;
        }
        bitCount = (i == -1) ? 0 : (i + 1);
    }

    public void add(long l)
    {
        for(int i = 0; i < length; i++)
        {
            numbers[i] += l;
        }
    }

    public void print() {
        //Определяем количество тетрад для двоично-десятичного кода
        int tetradsCount = (int)Math.ceil((bitCount * 0.3));
        //Тетрады храним в лонгах
        LongNumber tetrads = new LongNumber(tetradsCount / 16 + tetradsCount % 16);
        //Прибавляем к каждой тетраде тройку
        for(int i = 0; i < tetrads.length; i++)
        {
            tetrads.setNumber(i, 3689348814741910323L);
        }
        //Главный цикл
        for(int i = bitCount - 1; i >= 0; i--)
        {
            System.out.println("i: " + i);
            tetrads.countNumbers();
            for(int j = tetrads.bitCount - 1; j >= 0; j--)
            {
                System.out.print(tetrads.getBit(j));
                if(j % 4 == 0)
                {
                    System.out.print(" ");
                }
            }
            System.out.println();
            //создаем LongNumber для коррекции
            LongNumber correction = lArithmetic.AND(tetrads,-8608480567731124088L);
            //сдвигаем тетрады и толкаем в них бит из числа
            tetrads.leftOffset(getBit(i));
            LongNumber _correction = correction;
            _correction.rightOffset(2, 0);
            //определяем, нужна ли коррекция
            correction = lArithmetic.OR(correction, _correction);
            correction.add(3689348814741910323L);
            //корректируем
            tetrads = lArithmetic.sum(tetrads, correction);
        }
        for(int i = 0; i < tetrads.length; i++)
        {
            tetrads.numbers[i] -= 2459565876494606883L;
        }
        System.out.println("result:");
        tetrads.countNumbers();
        for(int i = tetrads.bitCount - 1; i >= 0; i--)
        {
            System.out.print(tetrads.getBit(i));
            if(i % 4 == 0)
            {
                System.out.print(" ");
            }
        }
    }
}
