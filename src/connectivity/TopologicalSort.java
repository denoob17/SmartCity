package connectivity;

import graph.Edge;
import graph.Graph;
import java.util.*;

public class TopologicalSort {
    public static long edgeExaminations = 0;

    public static List<Integer> run(Graph g) {
        edgeExaminations = 0;
        int n = g.getV();
        int[] indeg = new int[n];
        for (Edge e : g.getEdges()) indeg[e.to]++;
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) if (indeg[i] == 0) q.add(i);
        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            for (Edge e : g.neighbors(u)) {
                edgeExaminations++;
                if (--indeg[e.to] == 0) q.add(e.to);
            }
        }
        if (order.size() != n) return null;
        return order;
    }
}