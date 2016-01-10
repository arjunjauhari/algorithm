
/**
 * Write a description of class Deque here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Deque<Item> implements Iterable<Item>
{
    // instance variables - replace the example below with your own
    private Node front, back;
    private Node sentinel = new Node();
    private int N;

    // object overhead of 16 bytes
    private class Node  // inner class extra overhead of 8 bytes
    {
        private Item item;  // 8 bytes
        private Node next;  // 8 bytes
        private Node prev;  // 8 bytes
    }

    /**
     * Constructor for objects of class Deque
     */
    public Deque()
    {
        // initialise instance variables
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        front = sentinel;
        back = sentinel;
        N = 0;
    }

    public boolean isEmpty()
    {
        return (N == 0);
    }

    public int size()
    {
        return N;
    }

    public void addFirst(Item item)
    {
        if (item == null) throw new 
            java.lang.NullPointerException("Cant insert null");
        Node oldFront = front;
        front = new Node();     // new node
        oldFront.prev = front;  // IMP: prev of old front points to current front
        front.item = item;      // add item
        front.next = oldFront;  // point to old front
        front.prev = sentinel;  // prev points to sentinel
        if (isEmpty()) back = front;    // First add: so back and front same
        N++;
    }

    public void addLast(Item item)
    {
        if (item == null) throw new 
            java.lang.NullPointerException("Cant insert null");
        Node oldBack = back;
        back = new Node();      // new node
        oldBack.next = back;    // IMP: next of old back points to current back
        back.item = item;       // item inserted
        back.next = sentinel;   // last node pointed to sentinel
        back.prev = oldBack;    // points to old back
        if (isEmpty()) front = back;    // First add: so back and front same
        N++;
    }

    public Item removeFirst()
    {
        if (isEmpty()) throw new 
            java.util.NoSuchElementException("Deque underflow");
        Item item = front.item;
        front = front.next;
        front.prev = sentinel;
        N--;
        if (isEmpty()) back = front;    // Empty Deque with this remove
        return item;
    }

    public Item removeLast()
    {
        if (isEmpty()) throw new 
            java.util.NoSuchElementException("Deque underflow");
        Item item = back.item;
        back = back.prev;
        back.next = sentinel;
        N--;
        if (isEmpty()) front = back;    // Empty Deque with this remove
        return item;
    }


}
