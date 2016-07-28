
/**
 * Write a description of class RandomizedQueue here.
 *
 * @author (Arjun Jauhari)
 * @version (1.0)
 */
import java.util.Arrays;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
public class RandomizedQueue<Item> implements Iterable<Item>
{
    // instance variables - replace the example below with your own
    public Item[] x;   // reference 8 bytes
    private int N;      // 4 bytes, tells the number of in queue

    /**
     * Constructor for objects of class RandomizedQueue
     */
    public RandomizedQueue() {
        // initialise instance variables
        x = (Item[]) new Object[2];    // 8N+24 bytes Note:Java does not
                                       // all generic array
        N = 0;
    }

    public boolean isEmpty() {
        return (N == 0);
    }

    public int size() {
        return N;
    }

    public void enqueue(Item item) {
        if (item == null) throw new
            java.lang.NullPointerException("Cant insert null");
        if (N == x.length) resize(2 * x.length);
        x[N++] = item;
    }

    private void resize(int capacity) {
        assert capacity >= N;
        x = Arrays.copyOf(x, capacity);
    }

    public Item dequeue() {
        if (isEmpty()) throw new
            java.util.NoSuchElementException("Queue underflow");
        int rand = StdRandom.uniform(N);   // generated random number between 0 and N-1
        Item item = x[rand];
        x[rand] = x[--N];
        x[N] = null;
        if (N <= x.length/4) resize(x.length/2);
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new
            java.util.NoSuchElementException("Queue underflow");
        return x[StdRandom.uniform(N)];
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        int current = 0;
        Item[] suf_x;

        public ArrayIterator() {
            suf_x = Arrays.copyOf(x, N);
            StdRandom.shuffle(suf_x);
        }

        public boolean hasNext() {
            return (current < N);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            return suf_x[current++];
        }
    }

}
