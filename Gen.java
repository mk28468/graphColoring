package homework6;
// Gen.java
//
// This class provides static methods generating random Graph objects.
// In particular, graph(V,E,K) returns a random graph with V vertices,
// E edges, and a (secret) K-coloring.  Note it may have a
// (K-1)-coloring, especially if E is small.
//
// It starts with a random K-coloring of V vertices (each color class
// has V/K vertices, rounded up or down), and then adds E random edges
// between pairs with distinct colors.  Note that if E is too large,
// this may go into an infinite loop.
//
// Extreme cases:
//    K=2: construct a bipartite graph with E edges
//    K=V: construct a random graph with E edges
//
// Command line usage (defined by main):
//
//     java Gen V=# E=# K=# S=#
//
// Each arguments is optional, and each "#" stands for an integer.
// If given, S is a seed controlling the random number generator.
// See main for default values.  The resulting Graph is printed in a
// format that can be read back by the Graph(In) constructor.

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class Gen
{
    // Undirected Edge objects.
    public static class Edge implements Comparable<Edge>
    {
        int u, v;               // we will have u <= v
        Edge(int u, int v) {
            this.u = Math.min(u,v);
            this.v = Math.max(u,v);
        }
        public int compareTo(Edge o) { return u!=o.u ? u-o.u : v-o.v; }
        public String toString() { return u+" "+v; }
    }

    static void rte(String msg) { throw new RuntimeException(msg); }

    // main: parse command line arguments, construct graph, and print it.
    public static void main(String[] args)
    {
        int V=10, E=-1, K=3;
        Random rand = new Random();
        for (String arg: args) {
            String key = arg.substring(0,2).intern();
            int  value = Integer.parseInt(arg.substring(2));
            switch (key) {
            case "V=": V = value; break;
            case "E=": E = value; break;
            case "K=": K = value; break;
            case "S=": rand = new Random(value); break;
            default: rte("bad argument: " + arg);
            }
        }
        // Default E: about half of all the possible edges.
        if (E==-1) E=V*(V-V/K)/4;
        Graph G = graph(V, E, K, rand);
        StdOut.print(readable(G));
    }

    // Convert Graph G to a String that can be read by the Graph(In)
    // constructor. This is not the same as G.toString().
    static String readable(Graph G)
    {
        StringBuilder sb = new StringBuilder();
        int V = G.V(), E=0;
        for (int u=0; u<V; ++u)
            for (int v: G.adj(u))
                if (u <= v)
                {
                    sb.append(u + " " + v + "\n");
                    ++E;
                }
        return V + "\n" + E + "\n" + sb;
    }

    // edgeSet(...) methods return Set<Edge>:
    public static Set<Edge> edgeSet(int V, int E, int K) {
        return edgeSet(V, E, K, new Random()); // time dependent seed
    }
    public static Set<Edge> edgeSet(int V, int E, int K, long S) {
        return edgeSet(V, E, K, new Random(S)); // controlled by S
    }
    public static Set<Edge> edgeSet(int V, int E, int K, Random rand)
    {
        if (K < 2) rte("K < 2");
        if (K > V) rte("K > V");
        if (V < 1) rte("V < 1");

        // Pick random K-coloring (round-robin shuffle)
        int[] col = new int[V];
        for (int v=0; v<V; ++v) {
            int u = rand.nextInt(v+1);
            col[v] = col[u];
            col[u] = (v%K)+1;
        }
        // Pick random edges until we have E good ones, or
        // until good+bad is all possible edges.
        TreeSet<Edge> edges = new TreeSet<Edge>();
        while (edges.size() < E)
        {
            int u = rand.nextInt(V), v = rand.nextInt(V);
            if (col[u] != col[v])
                edges.add(new Edge(u, v));
        }
        return edges;
    }

    // graph(...) methods return a Graph.
    public static Graph graph(int V, Set<Edge> edges) {
        Graph G = new Graph(V);
        for (Edge e: edges)
            G.addEdge(e.u, e.v);
        return G;
    }
    public static Graph graph(int V, int E, int K) {
        return graph(V, edgeSet(V, E, K));
    }
    public static Graph graph(int V, int E, int K, long S) {
        return graph(V, edgeSet(V, E, K, S));
    }
    public static Graph graph(int V, int E, int K, Random rand) {
        return graph(V, edgeSet(V, E, K, rand));
    }
}
