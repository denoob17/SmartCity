package mst;

import graph.Edge;
import java.util.*;

public class MSTResult {
    public long totalWeight;
    public List<Edge> edges = new ArrayList<>();
    public boolean connected;
    public long edgeExaminations;
    public long comparisons;
    public long dsuOps;
}