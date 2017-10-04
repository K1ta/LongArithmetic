package source;

public class Main {

    public static void main(String[] args) {
        /*LongNumber a = new LongNumber();
        LongNumber b = new LongNumber(1, 5);
        lArithmetic.sum(a, b).print();*/
        fibonacci(333);
    }

    public static void fibonacci(int n)
    {
        LongNumber prev = new LongNumber(1,0);
        LongNumber cur = new LongNumber(1, 1);
        for(int i = 0; i < n; i++)
        {
            System.out.print((i + 1) + ":");
            prev.print();
            LongNumber temp = new LongNumber(cur);
            cur = lArithmetic.sum(prev, cur);
            prev = temp;
        }
    }
}
