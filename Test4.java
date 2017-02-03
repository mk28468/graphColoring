package homework6;
// private: just permutedGreedyColoring

// Test4: here we test the tryImprove method on some test graphs,
// and check that it performs at least as well as repeated random
// greedy coloring (with half the time).

import java.util.Random;





public class Test4 extends TestBase
{
    public static void main(String[] args)
    {
        double stSecs = 0.4;      // seconds per student trial
        double rgSecs = 0.2;      // seconds per random-greedy trial
        System.err.println("Comparing your tryImprove (" +
                           stSecs + " secs) with randomGreedy ("
                           + rgSecs + " secs)");
        int V=240, E=V*V/4, K=40, S=(int)(System.currentTimeMillis()%9000);
        int points = 0;
        try {
            for (int t=0; t<10; ++t)
            {
                ++S;
                K-=2;
                // Two copies, so student cannot mess with one copy.
                FGraph G = new FGraph(V,E,K,S);
                System.err.print("Trial "+t+", G="+G.desc+": ");
                // Give student twice as much time, for an advantage.
                int K1 = studentColoring(G, stSecs);
                int K2 = randGreedyColoring(G, rgSecs);
                if (K1<K2)
                    System.err.println("you won  ("+K1+" < "+K2+")");
                else if (K1==K2)
                    System.err.println("you tied ("+K1+" = "+K2+")");
                else
                    System.err.println("you lost ("+K1+" > "+K2+")");
                points += (K2-K1);
            }
            if (points < 0)
                bug("your heuristic does not color as well as randomGreedy");
            System.err.println
                ("You won by a total margin of "+points+" points");
            // System.err.println("Test4: OK");
        } catch (Exception e) {
            System.err.println(); // terminate partial line
            // e.printStackTrace(System.err);
            // System.exit(1);
            throw e;
        }
    }

    // Similar to GraphColoring.main, but no printing.
    static int studentColoring(Graph G, double secs)
    {
        long start = nanoTime();
        GraphColoring coloring = new GraphColoring(G);
        int bestK = check(G, coloring);
        String msg = "GraphColoring (" + secs + " sec limit): K="+bestK;
        while (true)
        {
            long innerBeg = nanoTime();
            double remaining = secs - (innerBeg-start)/1e9;
            if (remaining <= 0) break; // out of time
            boolean improved = coloring.tryImprove(remaining);
            double used = (nanoTime()-innerBeg)/1e9;
            if (used > remaining+0.1)
                bug("tryImprove("+remaining+") used "+used+" seconds");
            if (!improved) break;
            // tryImprove succeeded, check the new coloring
            int K = check(G, coloring);
            if (K >= bestK)
                bug("tryImprove() returned true, but maxColor() not improved");
            bestK = K;
            msg += ", " + bestK;
        }
        msg += ", done.";
        //System.err.println(msg);
        return bestK;
    }

    static int randGreedyColoring(Graph G, double secs) {
        long start = nanoTime();
        long limit = start + (long)(secs*1e9);
        long S = System.currentTimeMillis(); // different every time
        int bestK = permutedGreedyColorK(G, S++);
        String msg = "randGreedyColoring (" + secs + " sec limit): K="+bestK;
        while (nanoTime() < limit) {
            int K = permutedGreedyColorK(G, S++);
            if (K < bestK) {
                bestK = K;
                msg += ", " + bestK;
            }
        }
        msg += ", done.";
        //System.err.println(msg);
        return bestK;
    }

    // Do pseudo-randomly permuted greedy coloring, return maxColor.
    static int permutedGreedyColorK(Graph G, long S) {
        int V = G.V(), maxColor = 0;
        int[] color = new int[V], order = new int[V];
        // Create random permutation from seed S.
        Random rand = new Random(S);
        for (int v=0; v<V; ++v) {
            int u = rand.nextInt(v+1);
            order[v] = order[u];
            order[u] = v;
        }
        // Do greedy coloring, visiting vertices in permuted order.
        for (int v: order) {
            boolean[] taken = new boolean[maxColor+1];
            for (int u: G.adj(v))
                taken[color[u]] = true;
            int c = 1;
            while (c <= maxColor && taken[c]) ++c;
            color[v] = c;
            if (c > maxColor) maxColor = c;
        }
        return maxColor;
    }
}

