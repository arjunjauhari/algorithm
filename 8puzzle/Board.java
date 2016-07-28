
/**
 * Write a description of class Board here.
 *
 * @author (Arjun Jauhari)
 * @version (1.0)
 */
import java.util.Arrays;
import java.util.Stack;
import edu.princeton.cs.algs4.In;
public class Board
{
    // instance variables - replace the example below with your own
    private final int[] blocks;
    private final int N;
    private int hamming;
    private int manhattan;
    private int freeSquare;

    /**
     * Constructor for objects of class Board
     */
    public Board(int[][] blocks)
    {
        // initialise instance variables
        hamming = -1;
        manhattan = -1;
        this.N = blocks.length;
        this.blocks = new int[N*N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.blocks[i*N + j] = blocks[i][j];
                if (blocks[i][j] == 0)
                    freeSquare = i*N + j;
            }
        }

    }

    public int dimenision() {
        return this.N;
    }

    public int hamming() {
        if (hamming != -1)
            return hamming;

        hamming = 0;
        for (int i = 0; i < N*N; i++) {
            if ((blocks[i] != 0) && (blocks[i] != i+1))
                hamming++;
        }

        return hamming;
    }

    public int manhattan() {
        if (manhattan != -1)
            return manhattan;

        manhattan = 0;
        int t_i, t_j, c_i, c_j;
        for (int i = 0; i < N*N; i++) {
            if (blocks[i] != 0) {
                t_i = (blocks[i] - 1)/N;
                t_j = (blocks[i] - 1)%N;
                c_i = i/N;
                c_j = i%N;
                manhattan += Math.abs(t_i - c_i) + Math.abs(t_j - c_j);
            }
        }

        return manhattan;
    }

    public boolean isGoal() {
        return (hamming() == 0);
    }

    public Board twin() {
        int[] twinbrd = blocks.clone();

        if ((twinbrd[0] != 0) && (twinbrd[1] != 0)) {
            exch(twinbrd, 0 , 1);
        }
        else {
            exch(twinbrd, N , N+1);
        }

        return new Board(convert2d(twinbrd));
    }

    private int[][] convert2d(int[] tb) {
        int[][] blk = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blk[i][j] = tb[i*N + j];
            }
        }

        return blk;
    }

    private void exch(int[] a, int x, int y) {
        int tmp;
        tmp = a[x];
        a[x] = a[y];
        a[y] = tmp;
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass())  return false;
        Board that = (Board)y;

        for (int i = 0; i < N*N; i++) {
            if (this.blocks[i] != that.blocks[i])
                return false;
        }

        return true;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> stack = new Stack<Board>();
        int[] clonebrd;

        if ((freeSquare % N) != 0) {    //left
            clonebrd = blocks.clone();
            exch(clonebrd, freeSquare, freeSquare-1);
            stack.push(new Board(convert2d(clonebrd)));
        }

        if ((freeSquare % N) != N-1) {    //right
            clonebrd = blocks.clone();
            exch(clonebrd, freeSquare, freeSquare+1);
            stack.push(new Board(convert2d(clonebrd)));
        }

        if ((freeSquare / N) != 0) {    //top
            clonebrd = blocks.clone();
            exch(clonebrd, freeSquare, freeSquare-N);
            stack.push(new Board(convert2d(clonebrd)));
        }

        if ((freeSquare / N) != N-1) {    //bottom
            clonebrd = blocks.clone();
            exch(clonebrd, freeSquare, freeSquare+N);
            stack.push(new Board(convert2d(clonebrd)));
        }

        return stack;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i*N+j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        System.out.println(initial);

        System.out.println(initial.hamming());

        System.out.println(initial.manhattan());

        System.out.println(initial.twin());

        System.out.println(initial.equals(initial.twin()));

        System.out.println(initial.equals(initial));

        Stack<Board> st = (Stack<Board>)initial.neighbors();
        for (Board b : st)
            System.out.println(b);
    }
}
