package mst;

import dsu.DSU;
import graph.Edge;
import graph.Graph;
import java.util.*;

public class Kruskal {
    public static MSTResult run(Graph g) {
        MSTResult r = new MSTResult();
        List<Edge> sorted = new ArrayList<>(g.getEdges());
        long[] cmp = {0};
        sorted.sort((a, b) -> {
            cmp[0]++;
            return Integer.compare(a.weight, b.weight);
        });
        r.comparisons = cmp[0];
        DSU dsu = new DSU(g.getV());
        for (Edge e : sorted) {
            r.edgeExaminations++;
            if (dsu.union(e.from, e.to)) {
                r.edges.add(e);
                r.totalWeight += e.weight;
                if (r.edges.size() == g.getV() - 1) break;
            }
        }
        r.dsuOps = dsu.findCalls + dsu.unionCalls;
        r.connected = r.edges.size() == g.getV() - 1;
        return r;
    }
}