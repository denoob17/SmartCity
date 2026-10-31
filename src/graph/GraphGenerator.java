package graph;

import java.util.*;

public class GraphGenerator {

    public static Graph connectedUndirected(int V, int E, long seed) {
        if (E < V - 1 || E > (long) V * (V - 1) / 2) {
            throw new IllegalArgumentException("Bad E");
        }
        Random rand = new Random(seed);
        Graph g = new Graph(V, false);
        Set<Long> used = new HashSet<>();

        for (int i = 1; i < V; i++) {
            int j = rand.nextInt(i);
            g.addEdge(i, j, rand.nextInt(100) + 1);
            used.add((long) Math.min(i, j) * V + Math.max(i, j));
        }

        while (g.getE() < E) {
            int u = rand.nextInt(V);
            int v = rand.nextInt(V);
            if (u == v) continue;
            long key = (long) Math.min(u, v) * V + Math.max(u, v);
            if (used.contains(key)) continue;
            used.add(key);
            g.addEdge(u, v, rand.nextInt(100) + 1);
        }

        return g;
    }
}