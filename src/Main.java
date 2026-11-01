import connectivity.SCC;
import connectivity.TopologicalSort;
import graph.Edge;
import graph.Graph;
import mst.Kruskal;
import mst.MSTResult;
import mst.Prim;
import shortestpath.Dijkstra;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Graph g = new Graph(5, false);
        g.addEdge(0, 1, 4);
        g.addEdge(0, 2, 1);
        g.addEdge(1, 2, 2);
        g.addEdge(1, 3, 5);
        g.addEdge(2, 3, 8);
        g.addEdge(3, 4, 3);

        MSTResult r = Kruskal.run(g);
        System.out.println("kruskal connected = " + r.connected);
        System.out.println("kruskal weight = " + r.totalWeight);
        for (Edge e : r.edges) {
            System.out.println(e.from + " - " + e.to + ": " + e.weight);
        }

        MSTResult p = Prim.run(g);
        System.out.println("prim connected = " + p.connected);
        System.out.println("prim weight = " + p.totalWeight);

        Graph d = new Graph(4, true);
        d.addEdge(0, 1, 1);
        d.addEdge(1, 2, 1);
        d.addEdge(2, 0, 1);
        d.addEdge(2, 3, 1);
        System.out.println(TopologicalSort.run(d));
        System.out.println(SCC.run(d));
        System.out.println(Arrays.toString(Dijkstra.run(d, 0).dist));
    }
}