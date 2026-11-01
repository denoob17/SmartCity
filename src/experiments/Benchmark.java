package experiments;

import connectivity.SCC;
import connectivity.TopologicalSort;
import graph.Graph;
import graph.GraphGenerator;
import mst.Kruskal;
import mst.MSTResult;
import mst.Prim;
import shortestpath.Dijkstra;

import java.io.File;
import java.io.PrintWriter;

public class Benchmark {
    static final int RUNS = 5;
    static final long SEED = 42;
    static final int[][] SIZES = {{10, 30}, {100, 500}, {1000, 5000}};

    public static void main(String[] args) throws Exception {
        new File("results/tables").mkdirs();
        try (PrintWriter w = new PrintWriter("results/tables/results.csv")) {
            w.println("algorithm,V,E,avg_time_ms,operations");
            for (int[] s : SIZES) {
                int V = s[0];
                int E = s[1];
                Graph und = GraphGenerator.connectedUndirected(V, E, SEED);
                Graph dag = GraphGenerator.dag(V, E, SEED);
                Graph cyc = GraphGenerator.directedWithCycles(V, E, SEED);

                for (int i = 0; i < 20; i++) {
                    Kruskal.run(und);
                    Prim.run(und);
                    TopologicalSort.run(dag);
                    TopologicalSort.run(cyc);
                    SCC.run(cyc);
                    Dijkstra.run(cyc, 0);
                }

                double t = 0;
                long ops = 0;
                for (int i = 0; i < RUNS; i++) {
                    long st = System.nanoTime();
                    MSTResult r = Kruskal.run(und);
                    t += System.nanoTime() - st;
                    ops = r.edgeExaminations + r.comparisons + r.dsuOps;
                }
                w.println("Kruskal," + V + "," + E + "," + t / RUNS / 1e6 + "," + ops);

                t = 0;
                for (int i = 0; i < RUNS; i++) {
                    long st = System.nanoTime();
                    MSTResult r = Prim.run(und);
                    t += System.nanoTime() - st;
                    ops = r.edgeExaminations + r.comparisons;
                }
                w.println("Prim," + V + "," + E + "," + t / RUNS / 1e6 + "," + ops);

                t = 0;
                for (int i = 0; i < RUNS; i++) {
                    long st = System.nanoTime();
                    TopologicalSort.run(dag);
                    t += System.nanoTime() - st;
                    ops = TopologicalSort.edgeExaminations;
                }
                w.println("TopoSort_DAG," + V + "," + E + "," + t / RUNS / 1e6 + "," + ops);

                t = 0;
                for (int i = 0; i < RUNS; i++) {
                    long st = System.nanoTime();
                    TopologicalSort.run(cyc);
                    t += System.nanoTime() - st;
                    ops = TopologicalSort.edgeExaminations;
                }
                w.println("TopoSort_Cyclic," + V + "," + E + "," + t / RUNS / 1e6 + "," + ops);

                t = 0;
                for (int i = 0; i < RUNS; i++) {
                    long st = System.nanoTime();
                    SCC.run(cyc);
                    t += System.nanoTime() - st;
                    ops = SCC.edgeExaminations;
                }
                w.println("SCC," + V + "," + E + "," + t / RUNS / 1e6 + "," + ops);

                t = 0;
                for (int i = 0; i < RUNS; i++) {
                    long st = System.nanoTime();
                    Dijkstra.Result r = Dijkstra.run(cyc, 0);
                    t += System.nanoTime() - st;
                    ops = r.edgeExaminations + r.comparisons;
                }
                w.println("Dijkstra," + V + "," + E + "," + t / RUNS / 1e6 + "," + ops);
            }
        }
        System.out.println("done");
    }
}