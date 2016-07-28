
/**
 * Write a description of class TestDeque here.
 *
 * @author (Arjun Jauhari)
 * @version (1.0)
 */
public class TestDeque
{
    public void Test1(int x)
    {
        System.out.println("Test1: Testing addFirst and removeLast from 1 to " + x);
        Deque<Integer> d1 = new Deque<Integer>();
        for(int i = 1; i <= x; i++)
            d1.addFirst(i);
        for(int j = 1; j <= x; j++)
            System.out.println("" + d1.removeLast());
    }

    public void Test2(int x)
    {
        System.out.println("Test2: Testing addFirst and removeFirst from 1 to " + x);
        Deque<Integer> d1 = new Deque<Integer>();
        for(int i = 1; i <= x; i++)
            d1.addFirst(i);
        for(int j = 1; j <= x; j++)
            System.out.println("" + d1.removeFirst());
    }

    public void Test3(int x)
    {
        System.out.println("Test3: Testing foreach iterable with addFirst from 1 to " + x);
        Deque<Integer> d1 = new Deque<Integer>();
        for(int i = 1; i <= x; i++)
            d1.addFirst(i);
        for (Integer a : d1)
            System.out.println("" + a);
    }

    public void Test4(int x)
    {
        System.out.println("Test4: Testing foreach iterable with addLast from 1 to " + x);
        Deque<Integer> d1 = new Deque<Integer>();
        for(int i = 1; i <= x; i++)
            d1.addLast(i);
        for (Integer a : d1)
            System.out.println("" + a);
    }

    public void Test5(int x)
    {
        System.out.println("Test5: RandomizedQueue, Testing enqueue/dequeue " + x);
        RandomizedQueue<Integer> rq1 = new RandomizedQueue<Integer>();
        for(int i = 1; i <= x; i++)
            rq1.enqueue(i);
        for(int j = 1; j <= x; j++)
            System.out.println("" + rq1.dequeue());
        System.out.println("Size = " + rq1.size());
    }

    public void Test6(int x)
    {
        System.out.println("Test6: RandomizedQueue, Testing enqueue/sample " + x);
        RandomizedQueue<Integer> rq1 = new RandomizedQueue<Integer>();
        for(int i = 1; i <= x; i++)
            rq1.enqueue(i);
        for(int j = 1; j <= x; j++)
            System.out.println("" + rq1.sample());
        System.out.println("Size = " + rq1.size());
    }

    public void Test7(int x)
    {
        System.out.println("Test7: RandomizedQueue, Testing foreach " + x);
        RandomizedQueue<Integer> rq1 = new RandomizedQueue<Integer>();
        for(int i = 1; i <= x; i++)
            rq1.enqueue(i);
        for (Integer a : rq1) {
            for (Integer b : rq1) {
                System.out.println("" + a + " " + b);
            }
        }
        System.out.println("Size = " + rq1.size());
    }

    public static void main(String[] args)
    {
        // run all tests
        int x = 3;
        TestDeque td1 = new TestDeque();
        td1.Test1(x);
        td1.Test2(x);
        td1.Test3(x);
        td1.Test4(x);
        td1.Test5(x);
        td1.Test6(x);
        td1.Test7(x);
    }
}
