
/**
 * Write a description of class Solver here.
 *
 * @author (Arjun Jauhari)
 * @version (a version number or a date)
 */
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.Stack;
public class Solver
{
    // instance variables - replace the example below with your own
    private searchNode origDequed;
    private searchNode twinDequed;

    private class searchNode implements Comparable<searchNode> {
        final Board brd;
        final int numMoves;
        final searchNode prev;
        final int priority;

        searchNode(Board in, int moves, searchNode prev) {
            this.brd = in;
            this.numMoves = moves;
            this.prev = prev;
            this.priority = this.brd.manhattan() + this.numMoves;
        }

        public int compareTo(searchNode that) {
            if (this.priority < that.priority)
                return -1;
            else if (this.priority > that.priority)
                return 1;
            else
                return 0;
        }
    }

    /**
     * Constructor for objects of class Solver
     */
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new java.lang.NullPointerException("Not a valid input");

        // make a priority queue of searchNode type
        MinPQ<searchNode> pq = new MinPQ<searchNode>();
        MinPQ<searchNode> tq = new MinPQ<searchNode>();

        // initialize
        pq.insert(new searchNode(initial, 0, null));
        tq.insert(new searchNode(initial.twin(), 0, null));

        // initialize origDequed node
        origDequed = pq.delMin();
        twinDequed = tq.delMin();

        // till goal is not reached
        while((!origDequed.brd.isGoal()) && (!twinDequed.brd.isGoal())) {
            origDequed = aStar(origDequed, pq);
            twinDequed = aStar(twinDequed, tq);
        }
    }

    private searchNode aStar(searchNode in, MinPQ<searchNode> pq) {
        //debug
        //System.out.println(in.numMoves);
        // increment movesTaken
        int movesTaken = in.numMoves + 1;

        // tmp searchNode
        searchNode tmp;

        // insert neighbors
        for (Board b : in.brd.neighbors()) {
            // make a searchNode from Board
            tmp = new searchNode(b, movesTaken, in);

            //debug
            //System.out.println((in.prev == null) || ((in.prev != null) && (!tmp.brd.equals(in.prev.brd))));
            // insert only if not already inserted
            if ((in.prev == null) || ((in.prev != null) && (!b.equals(in.prev.brd))))
                pq.insert(tmp);
        }

        // update in
        return pq.delMin();
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return origDequed.brd.isGoal();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable())
            return origDequed.numMoves;
        else
            return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        Stack<Board> st = new Stack<Board>();
        Stack<Board> rt = new Stack<Board>();

        searchNode sn = origDequed.prev;

        // final node
        st.push(origDequed.brd);

        while (sn != null) {
            st.push(sn.brd);

            // update sn
            sn = sn.prev;
        }

        while(!st.empty())
            rt.push(st.pop());

        return rt;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
