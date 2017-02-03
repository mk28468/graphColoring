package homework6;
// Test2: Check that the GraphColoring constructor is fast enough,
// compared to greedyColoring, on some large graphs (same as Test3).
// We allow it to be up to 10 times slower, that should allow some
// fancier heuristics like DSATUR.



public class Test2 extends TestBase
{
    public static void main(String[] args)
    {
        double factor = 10.0;   // allowed slow-down factor

        System.err.println
            ("constructor timing (compared to greedyColoring on large graphs)");
        // Build array of big FGraph objects (same as in Test3)
        int N=10, V=4000, S=(int)(System.currentTimeMillis()%9000);
        FGraph[] G = new FGraph[N];
        for (int t=0; t<N; ++t) {
            ++S;
            int K = t+3, E = 2*K*V;
            G[t] = new FGraph(Gen.graph(V, E, K, S));
            G[t].desc = "Gen.graph("+V+","+E+","+K+","+S+")";
        }
        try {
            // Warm up the JVM
            greedy(G);
            greedy(G);
            double goalTime = 0.15;
            int Reps=0;
            long diff=(long)(goalTime*1e9), beg=nanoTime(), target=beg+diff;
            do {
                greedy(G);
                ++Reps;
            } while (nanoTime() < target);
            double time1 = (nanoTime()-beg)/1e9;
            // Now time same number of colorings, using student method
            student(G);
            student(G);
            beg=nanoTime();
            target = beg+(long)(factor*time1*1e9);

            for (int r=0; r<Reps && nanoTime() <= target; ++r)
                student(G);
            double time2 = (nanoTime()-beg)/1e9;
            double ratio = time2/time1;
            System.err.printf("constructed %d colorings in %.3f secs " +
                              "(greedyColoring used %.3f)\n",
                              Reps*N, time2, time1);
            if (ratio > factor)
                bug("too slow! (over " + factor +
                    " times slower than greedyColoring)");
            if (ratio < 0.9)
                System.err.println("Faster than greedyColoring, nice!");
            // System.err.println("Test2: OK");
        } catch (Exception e) {
            // e.printStackTrace(System.err);
            // System.exit(1);
            // throw new RuntimeException("Test2 failed");
            throw e;
        }
    }

    // Color each using the default greedyColoring method
    static void greedy(FGraph[] G) {
        for (FGraph g: G) greedyColoring(g);
    }

    // Color each using the student GraphColoring constructor
    static void student(FGraph[] G) {
        for (FGraph g: G) new GraphColoring(g);
    }
}

