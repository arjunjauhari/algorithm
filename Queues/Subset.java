
/**
 * Write a description of class Subset here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.princeton.cs.algs4.StdIn;
public class Subset
{
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            //System.out.println(StdIn.readString());
            rq.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k; i++) {
            System.out.println(rq.dequeue());
        }
    }

}