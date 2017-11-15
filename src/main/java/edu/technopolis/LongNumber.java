package edu.technopolis;

public class LongNumber {

    private long[] numbers;
    private static final long MASK_EIGHT = 0b10001000_10001000_10001000_10001000_10001000_10001000_10001000_10001000L;
    private static final long MASK_THREE = 0b00110011_00110011_00110011_00110011_00110011_00110011_00110011_00110011L;
    private static final int MIN_SIZE = 1;

    LongNumber()
    {
        numbers = new long[MIN_SIZE];
    }

    LongNumber(long value) {
        numbers = new long[1];
        numbers[0] = value;
    }

    LongNumber add(LongNumber a) {
        LongNumber res = new LongNumber();
        if (numbers.length < a.numbers.length) {
            res.numbers = addNumber(numbers);
        } else {
            res.numbers = addNumber(a.numbers);
        }
        return res;
    }

    private long[] addNumber(long[] a) {
        int l = numbers.length;
        int newLength = l;
        if (numbers[l - 1] > 0) {
            newLength++;
            newLength &= ~Integer.MIN_VALUE;
        }
        /*long lastNumber = numbers[l-1];
        newLength += lastNumber & (~(-lastNumber));
        newLength &= Long.MAX_VALUE;*/
        long[] res = new long[newLength];
        long temp = 0;
        System.arraycopy(a, 0, res, 0, a.length);
        for (int i = 0; i < l; i++) {
            res[i] += temp + numbers[i];
            //определение необходимости переноса
            temp = res[i] & Long.MIN_VALUE;
            res[i] ^= temp;
            temp >>>= 63;
        }
        return res;
    }

    public String toString() {
        //тетрады храним в лонгах
        long[] tetrads = new long[(int) (Math.ceil(numbers.length * 1.25f))];
        //добавляем тройку в каждую тетраду
        for (int i = 0; i < tetrads.length; i++) {
            tetrads[i] = MASK_THREE;
        }
        //массив для хранения коррекции
        long[] correction = new long[tetrads.length];
        //буферная переменная
        long temp;
        //основной цикл
        for (int i = numbers.length * 63 - 1; i >= 0; i--) {
            //определяем необходимость коррекции для каждой тетрады
            for (int j = 0; j < tetrads.length; j++) {
                temp = tetrads[j] & MASK_EIGHT;
                temp = temp >>> 2;
                temp |= temp >>> 1;
                correction[j] = temp;
            }
            //сдвигаем тетрады и вталкиваем старший бит числа
            for (int j = 0; j < tetrads.length - 1; j++) {
                tetrads[j] <<= 1;
                tetrads[j] |= ((tetrads[j + 1] & Long.MIN_VALUE) >>> 63);
            }
            tetrads[tetrads.length - 1] <<= 1;
            tetrads[tetrads.length - 1] |= (numbers[i / 63] >>> (i % 63)) & 1L;
            //коррекция
            for (int j = 0; j < tetrads.length; j++) {
                temp = (correction[j] ^ MASK_THREE);
                tetrads[j] += correction[j] - temp;
            }
        }
        //отнимаем избыточную тройку
        for (int i = 0; i < tetrads.length; i++) {
            tetrads[i] -= MASK_THREE;
        }
        //перевод тетрад в строку
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < tetrads.length; i++) {
            for (int j = 15; j >= 0; j--) {
                temp = (tetrads[i] >> (j * 4)) & 15L;
                s.append(temp);
                s.delete(0, Math.max(0, 2 - (int)temp - s.length()));
            }
        }
        return s.toString();
    }

}
