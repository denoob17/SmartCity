package graph;

import java.util.*;

public class Graph {
    private final int V;
    private final List<List<Edge>> adj;
    private final List<Edge> edges;
    private final boolean directed;

    public Graph(int V, boolean directed) {
        this.V = V;
        this.directed = directed;
        adj = new ArrayList<>();
        edges = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int w) {
        Edge e = new Edge(u, v, w);
        adj.get(u).add(e);
        edges.add(e);
        if (!directed) {
            adj.get(v).add(new Edge(v, u, w));
        }
    }

    public List<Edge> neighbors(int u) {
        return adj.get(u);
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public int getV() {
        return V;
    }

    public int getE() {
        return edges.size();
    }

    public boolean isDirected() {
        return directed;
    }

    public void print() {
        for (int u = 0; u < V; u++) {
            System.out.print(u + ": ");
            for (Edge e : adj.get(u)) {
                System.out.print("(" + e.to + ", w=" + e.weight + ") ");
            }
            System.out.println();
        }
    }
}