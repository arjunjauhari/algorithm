import edu.princeton.cs.algs4.StdDraw;
public class sim {
    double sum = 0;

    public void plot() {
        StdDraw.show();
        for (int i = 1; i <= 1000; i++) {
            sum += 1/Math.pow((8*i-1),2);
            StdDraw.point(i/1000.0,sum);
        }
        System.out.println(sum/Math.PI);
    }
}
