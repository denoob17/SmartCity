import graph.Graph;

class Main {
    public static void main(String[] args) {
        Graph g = new Graph(5, false);

        g.addEdge(0, 1, 4);
        g.addEdge(0, 2, 1);
        g.addEdge(1, 2, 2);
        g.addEdge(1, 3, 5);
        g.addEdge(2, 3, 8);
        g.addEdge(3, 4, 3);

        g.print();
    }
}