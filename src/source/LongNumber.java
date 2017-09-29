package source;

public class LongNumber {
    public long[] numbers;

    public LongNumber() {
        numbers = new long[4];
    }

    public LongNumber(long l) {
        numbers = new long[4];
        numbers[3] = l;
    }

    public LongNumber(long l1, long l2, long l3, long l4) {
        numbers = new long[]{l1, l2, l3, l4};
    }

    public long getBit(int n) {
        int index = 3 - n / 64;
        long number = n % 64;
        return (numbers[index] >> number) & 1L;
    }

    public void setBit(int n) {
        int index = 3 - n / 64;
        int number = n % 64;
        numbers[index] = numbers[index] | (1L << number);
    }

    public void leftOffset(int index, int n) {
        numbers[index] = numbers[index] << n;
    }

    public void printNumbers() {
        for (int j = 127; j >= 0; j--) {
            if (getBit(j) != 0) {
                System.out.print(1);
            } else {
                System.out.print(0);
            }
            if (j % 4 == 0) {
                System.out.print(" ");
            }
            if(j % 64 == 0)
            {
                System.out.print("  ");
            }
        }
        System.out.println();
    }

    public void print() {
        //всего 80 тетрад
        long[] tetras = new long[5];
        boolean[] correction = new boolean[80];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 64; j += 4) {
                tetras[i] = tetras[i] | (3L << j);
            }
        }
        //алгоритм
        for (int i = 255; i >= 0; i--) {
            int corrInd = 0;
            //делаем сдвиг тетрад и проверяем коррекцию
            for (int j = 0; j < 5; j++) {
                //проверка на необходимость коррекции
                for (int k = 0; k < 64; k += 4) {
                    if ((tetras[j] & (1L << (63 - k))) != 0) {
                        correction[corrInd] = true;
                    } else {
                        correction[corrInd] = false;
                    }
                    corrInd++;
                }
                tetras[j] = tetras[j] << 1;
                if (j < 4) {
                    if ((tetras[j + 1] & (1L << 63)) != 0) {
                        tetras[j] += 1;
                    }
                } else {
                    tetras[j] = tetras[j] & ~1L;
                    tetras[j] += getBit(i);
                }
            }
            //проводим коррекцию
            corrInd = 0;
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 64; k += 4) {
                    long corr = (3L << (60 - k));
                    if (correction[corrInd] == true) {
                        tetras[j] += corr;
                    } else {
                        tetras[j] -= corr;
                    }
                    corrInd++;
                }
            }
        }
        //обратно
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 64; j += 4) {
                long corr = (3L << (64 - j));
                tetras[i] -= corr;
            }
        }
        //битовый вывод
        /*for (int _i = 0; _i < 5; _i++) {
            for (int j = 63; j >= 0; j--) {
                if ((tetras[_i] & (1L << j)) != 0) {
                    System.out.print(1);
                } else {
                    System.out.print(0);
                }
                if (j % 4 == 0) {
                    System.out.print(" ");
                }
            }
            System.out.print("  ");
        }
        System.out.println();*/
        //десятичный вывод
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 64; j += 4) {
                long res = (((tetras[i] >> (63 - j)) & 1) * 8) +
                        (((tetras[i] >> (63 - j - 1)) & 1) * 4) +
                        (((tetras[i] >> (63 - j - 2)) & 1) * 2) +
                        ((tetras[i] >> (63 - j - 3)) & 1);
                System.out.print(res);
            }
            System.out.print(" ");
        }
        System.out.println();
    }
}
