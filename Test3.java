package homework6;
// Test3: check that the student GraphColoring constructor produces a
// valid coloring, competitive with the given greedyColoring method.



public class Test3 extends TestBase
{
    public static void main(String[] args)
    {
        System.err.println("Comparing constructor coloring with greedyColoring"
                           + " (large non-bipartite graphs)");
        int V=4000, S=(int)(System.currentTimeMillis()%9000);
        int points = 0;
        String when = "";
        try {
            for (int t=0; t<10; ++t)
            {
                ++S;
                int K = t+3, E = 2*K*V;
                // Two copies, so student cannot mess with one.
                FGraph G = new FGraph(V,E,K,S);
                System.err.print("Trial "+t+", " + G.desc + ": ");
                when = "in your GraphColoring constructor";
                GraphColoring gc = new GraphColoring(G);
                when = "checking your GraphColoring";
                int K1 = check(G, gc);
                int K2 = greedyColoringK(G);
                if (K1<K2)
                    System.err.println("you won  ("+K1+" < "+K2+")");
                else if (K1==K2)
                    System.err.println("you tied ("+K1+" = "+K2+")");
                else
                    System.err.println("you lost ("+K1+" > "+K2+")");
                points += (K2-K1);
            }
            if (points < -3)
                bug("your constructor colors worse than greedyColoring");
            String comment = "barely enough";
            if (points > -1) comment = "enough";
            if (points >  2) comment = "ok";
            if (points >  5) comment = "good";
            if (points >  8) comment = "very good";
            if (points > 11) comment = "great";
            System.err.println("You won by total margin of " +
                               points + " points, " + comment);
            // System.err.println("Test3: OK");
        } catch (Exception e) {
            System.err.println(); // terminate partial line
            //System.err.println("When:  " + when);
            // e.printStackTrace(System.err);
            // System.exit(1);
            throw e;
        }
    }
}

