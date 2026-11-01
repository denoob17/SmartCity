package connectivity;

import graph.Edge;
import graph.Graph;
import java.util.*;

public class SCC {
    public static long edgeExaminations = 0;

    public static List<List<Integer>> run(Graph g) {
        edgeExaminations = 0;
        int n = g.getV();
        int[] idx = new int[n];
        int[] low = new int[n];
        boolean[] onStack = new boolean[n];
        int[] it = new int[n];
        Arrays.fill(idx, -1);
        Deque<Integer> stack = new ArrayDeque<>();
        Deque<Integer> call = new ArrayDeque<>();
        List<List<Integer>> result = new ArrayList<>();
        int counter = 0;

        for (int s = 0; s < n; s++) {
            if (idx[s] != -1) continue;
            call.push(s);
            idx[s] = low[s] = counter++;
            stack.push(s);
            onStack[s] = true;
            while (!call.isEmpty()) {
                int u = call.peek();
                List<Edge> nb = g.neighbors(u);
                if (it[u] < nb.size()) {
                    Edge e = nb.get(it[u]++);
                    edgeExaminations++;
                    int v = e.to;
                    if (idx[v] == -1) {
                        idx[v] = low[v] = counter++;
                        stack.push(v);
                        onStack[v] = true;
                        call.push(v);
                    } else if (onStack[v]) {
                        low[u] = Math.min(low[u], idx[v]);
                    }
                } else {
                    call.pop();
                    if (!call.isEmpty()) {
                        int p = call.peek();
                        low[p] = Math.min(low[p], low[u]);
                    }
                    if (low[u] == idx[u]) {
                        List<Integer> comp = new ArrayList<>();
                        int w;
                        do {
                            w = stack.pop();
                            onStack[w] = false;
                            comp.add(w);
                        } while (w != u);
                        result.add(comp);
                    }
                }
            }
        }
        return result;
    }
}