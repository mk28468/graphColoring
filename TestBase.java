package homework6;
// Public test file, students do not edit.
// A base class for Test1, Test2, Test3, Test4.

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;





public class TestBase
{
    // Print a warning message to System.err
    static void warn(String msg) { System.err.println("WARNING: "+msg); }

    // Timing routine: measure thread user CPU time, if possible.
    // Otherwise, just measure elapsed time.
    static ThreadMXBean bean = ManagementFactory.getThreadMXBean();
    static boolean beanWorks = bean.isCurrentThreadCpuTimeSupported();
    static { if (!beanWorks) warn("user CPU timing not available"); }
    static long nanoTime() {
        return beanWorks? bean.getCurrentThreadUserTime() : System.nanoTime();
    }

    // Signal a bug (ends test and delivers message to student)
    static void bug(String msg) { throw new RuntimeException(msg); }

    // Check gc is a coloring: return maxColor() or signal a bug.
    static int check(Graph G, GraphColoring gc)
    {
        int V = G.V(), K = gc.maxColor();
        // copy color(v) values into an array (to keep colors consistent)
        int[] colors = new int[V];
        for (int v=0; v<V; ++v) {
            int c = colors[v] = gc.color(v);
            if (c < 1) bug("color("+v+") < 1");
            if (c > K) bug("color("+v+") > maxColor()");
        }
        // Check each non-loop edge is colored correctly.
        for (int u=0; u<V; ++u)
            for (int v: G.adj(u))
                if (u!=v && colors[u]==colors[v])
                    bug("both ends of edge ("+u+", "+v+") are colored "
                        + colors[u]);
        // good!
        return K;
    }

    // The greedy coloring method given to students (de-commented).
    static int[] greedyColoring(Graph G) {
        int V = G.V(), maxColor = 0;
        int[] color = new int[V];
        for (int v = 0; v < V; ++v) {
            boolean[] taken = new boolean[maxColor+1];
            for (int u: G.adj(v))
                taken[color[u]] = true;
            int c = 1;
            while (c<=maxColor && taken[c]) ++c;
            color[v] = c;
            if (c > maxColor) maxColor = c;
        }
        return color;
    }
    // Same thing, except this version returns the maxColor value K.
    static int greedyColoringK(Graph G) {
        int V = G.V(), maxColor = 0;
        int[] color = new int[V];
        for (int v = 0; v < V; ++v) {
            boolean[] taken = new boolean[maxColor+1];
            for (int u: G.adj(v))
                taken[color[u]] = true;
            int c = 1;
            while (c<=maxColor && taken[c]) ++c;
            color[v] = c;
            if (c > maxColor) maxColor = c;
        }
        return maxColor;
    }
}
