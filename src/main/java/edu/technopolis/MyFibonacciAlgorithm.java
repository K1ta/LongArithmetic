package edu.technopolis;

public class MyFibonacciAlgorithm implements FibonacciAlgorithm {
    @Override
    public String evaluate(int index) {
        LongNumber prev = new LongNumber(0L);
        LongNumber cur = new LongNumber(1L);
        LongNumber temp;
        for (int i = 1; i < index; i++) {
            temp = cur;
            cur = cur.add(prev);
            prev = temp;
        }
        return cur.toString();
    }
}
