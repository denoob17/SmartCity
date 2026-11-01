import connectivity.SCC;
import connectivity.TopologicalSort;
import graph.Edge;
import graph.Graph;
import graph.GraphGenerator;
import mst.Kruskal;
import mst.MSTResult;
import mst.Prim;
import shortestpath.Dijkstra;

import java.util.*;

public class TestRunner {
    static int passed = 0;
    static int failed = 0;

    static void check(String name, boolean ok) {
        if (ok) passed++; else failed++;
        System.out.println((ok ? "PASS " : "FAIL ") + name);
    }

    public static void main(String[] args) {
        Graph single = new Graph(1, false);
        MSTResult s1 = Kruskal.run(single);
        MSTResult s2 = Prim.run(single);
        check("single vertex kruskal", s1.connected && s1.totalWeight == 0);
        check("single vertex prim", s2.connected && s2.totalWeight == 0);

        Graph disc = new Graph(4, false);
        disc.addEdge(0, 1, 1);
        disc.addEdge(2, 3, 2);
        check("disconnected kruskal", !Kruskal.run(disc).connected);
        check("disconnected prim", !Prim.run(disc).connected);

        Graph tie = new Graph(4, false);
        tie.addEdge(0, 1, 1);
        tie.addEdge(1, 2, 1);
        tie.addEdge(2, 3, 1);
        tie.addEdge(3, 0, 1);
        tie.addEdge(0, 2, 1);
        check("duplicate weights kruskal=prim",
                Kruskal.run(tie).totalWeight == 3 && Prim.run(tie).totalWeight == 3);

        Graph hand = new Graph(5, false);
        hand.addEdge(0, 1, 4);
        hand.addEdge(0, 2, 1);
        hand.addEdge(1, 2, 2);
        hand.addEdge(1, 3, 5);
        hand.addEdge(2, 3, 8);
        hand.addEdge(3, 4, 3);
        check("hand graph weight 11",
                Kruskal.run(hand).totalWeight == 11 && Prim.run(hand).totalWeight == 11);

        boolean same = true;
        for (long seed = 1; seed <= 20; seed++) {
            Graph g = GraphGenerator.connectedUndirected(100, 500, seed);
            MSTResult a = Kruskal.run(g);
            MSTResult b = Prim.run(g);
            if (a.totalWeight != b.totalWeight || !a.connected || !b.connected) same = false;
        }
        check("random 20 graphs kruskal=prim", same);

        boolean validTopo = true;
        for (long seed = 1; seed <= 20; seed++) {
            Graph g = GraphGenerator.dag(100, 500, seed);
            List<Integer> order = TopologicalSort.run(g);
            if (order == null || order.size() != 100) {
                validTopo = false;
                continue;
            }
            int[] pos = new int[100];
            for (int i = 0; i < order.size(); i++) pos[order.get(i)] = i;
            for (Edge e : g.getEdges()) if (pos[e.from] >= pos[e.to]) validTopo = false;
        }
        check("topological order valid on DAGs", validTopo);

        Graph cyc = new Graph(3, true);
        cyc.addEdge(0, 1, 1);
        cyc.addEdge(1, 2, 1);
        cyc.addEdge(2, 0, 1);
        check("cycle detected", TopologicalSort.run(cyc) == null);

        boolean sccOk = true;
        for (long seed = 1; seed <= 20; seed++) {
            Graph g = GraphGenerator.directedWithCycles(100, 300, seed);
            List<List<Integer>> comps = SCC.run(g);
            int[] count = new int[100];
            for (List<Integer> c : comps) for (int v : c) count[v]++;
            for (int c : count) if (c != 1) sccOk = false;
        }
        check("every vertex in exactly one SCC", sccOk);

        Graph two = new Graph(4, true);
        two.addEdge(0, 1, 1);
        two.addEdge(1, 2, 1);
        two.addEdge(2, 0, 1);
        two.addEdge(2, 3, 1);
        List<List<Integer>> comps = SCC.run(two);
        check("scc count 2", comps.size() == 2);

        Graph dj = new Graph(5, true);
        dj.addEdge(0, 1, 4);
        dj.addEdge(0, 2, 1);
        dj.addEdge(2, 1, 2);
        dj.addEdge(1, 3, 1);
        Dijkstra.Result r = Dijkstra.run(dj, 0);
        check("dijkstra distances",
                r.dist[0] == 0 && r.dist[1] == 3 && r.dist[2] == 1 && r.dist[3] == 4);
        check("dijkstra unreachable", r.dist[4] == Dijkstra.INF);
        check("dijkstra path", Dijkstra.path(r, 3).equals(Arrays.asList(0, 2, 1, 3)));
        check("dijkstra path unreachable", Dijkstra.path(r, 4) == null);

        System.out.println("passed " + passed + ", failed " + failed);
    }
}