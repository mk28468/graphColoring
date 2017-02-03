package homework6;
// An FGraph is a frozen copy of a Graph (it cannot be modified).
// For convenience, it also carries an optional descrption string (desc),
// and we provide a constructor that wraps up Gen.graph(V,E,K,S).

public class FGraph extends Graph
{
    public String desc = null;  // optional
    public FGraph(Graph G) { super(G); }
    public FGraph(int V, int E, int K, int S) {
        super(Gen.graph(V, E, K, S));
        desc = "Gen.graph("+V+","+E+","+K+","+S+")";
    }
    // An FGraph cannot be modified.
    public void addEdge(int u, int v) {
        throw new RuntimeException("this Graph is frozen, do not modify");
    }
}
