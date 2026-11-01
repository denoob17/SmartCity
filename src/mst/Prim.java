package mst;

import graph.Edge;
import graph.Graph;
import java.util.*;

public class Prim {
    public static MSTResult run(Graph g) {
        MSTResult r = new MSTResult();
        int n = g.getV();
        if (n == 0) {
            r.connected = true;
            return r;
        }
        boolean[] in = new boolean[n];
        long[] cmp = {0};
        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> {
            cmp[0]++;
            return Integer.compare(a.weight, b.weight);
        });
        in[0] = true;
        int count = 1;
        for (Edge e : g.neighbors(0)) {
            pq.add(e);
            r.edgeExaminations++;
        }
        while (!pq.isEmpty() && count < n) {
            Edge e = pq.poll();
            if (in[e.to]) continue;
            in[e.to] = true;
            count++;
            r.edges.add(e);
            r.totalWeight += e.weight;
            for (Edge f : g.neighbors(e.to)) {
                r.edgeExaminations++;
                if (!in[f.to]) pq.add(f);
            }
        }
        r.comparisons = cmp[0];
        r.connected = count == n;
        return r;
    }
}