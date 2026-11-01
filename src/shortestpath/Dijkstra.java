package shortestpath;

import graph.Edge;
import graph.Graph;
import java.util.*;

public class Dijkstra {
    public static final long INF = Long.MAX_VALUE;

    public static class Result {
        public long[] dist;
        public int[] parent;
        public long edgeExaminations;
        public long comparisons;
    }

    public static Result run(Graph g, int src) {
        int n = g.getV();
        Result r = new Result();
        r.dist = new long[n];
        r.parent = new int[n];
        Arrays.fill(r.dist, INF);
        Arrays.fill(r.parent, -1);
        r.dist[src] = 0;
        long[] cmp = {0};
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> {
            cmp[0]++;
            return Long.compare(a[0], b[0]);
        });
        pq.add(new long[]{0, src});
        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            int u = (int) cur[1];
            if (cur[0] > r.dist[u]) continue;
            for (Edge e : g.neighbors(u)) {
                r.edgeExaminations++;
                long nd = r.dist[u] + e.weight;
                if (nd < r.dist[e.to]) {
                    r.dist[e.to] = nd;
                    r.parent[e.to] = u;
                    pq.add(new long[]{nd, e.to});
                }
            }
        }
        r.comparisons = cmp[0];
        return r;
    }

    public static List<Integer> path(Result r, int target) {
        if (r.dist[target] == INF) return null;
        LinkedList<Integer> p = new LinkedList<>();
        for (int v = target; v != -1; v = r.parent[v]) p.addFirst(v);
        return p;
    }
}