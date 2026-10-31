import graph.Graph;
import graph.GraphGenerator;

public class Main {
    public static void main(String[] args) {
        Graph a = GraphGenerator.connectedUndirected(10, 30, 42);
        Graph b = GraphGenerator.connectedUndirected(10, 30, 42);
        a.print();
        System.out.println("---");
        b.print();
        System.out.println("E = " + a.getE());
    }
}