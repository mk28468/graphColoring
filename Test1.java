package homework6;
// Public test file, students do not edit.



// Test1: check that the GraphColoring constructor produces valid
// 2-colorings on bipartite graphs, and valid colorings on 3-colorable
// graphs of the same size.

public class Test1 extends TestBase
{
    public static void main(String[] args)
    {
        String when = "";
        int V = 100, N=10, S=(int)(System.currentTimeMillis()%9000);
        System.err.println
            ("Testing your constructor on test graphs with V=100, K=2 or 3");
        try {
            for (int t=0; t<N; ++t)
            {
                ++S;
                int E = 950-t*100; // from dense to disconnected
                int K = 2+t%2;  // 2 or 3
                FGraph G = new FGraph(V, E, K, S);
                System.err.print("Trial "+t+", G="+G.desc+": ");
                when = "constructing GraphColoring(G)";
                GraphColoring gc = new GraphColoring(G);
                when = "checking GraphColoring(G)";
                int gotK = check(G, gc);
                if (K==2 && gotK > 2)
                    bug("failed to 2-color a bipartite graph");
                System.err.println("used " + gotK + " colors");
            }
            // System.err.println("Test1: OK");
        } catch (Exception e) {
            System.err.println();
            System.err.println("When:  " + when);
            // e.printStackTrace(System.err);
            //System.exit(1);
            throw e;
        }
    }
}
