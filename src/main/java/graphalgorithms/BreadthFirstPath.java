package graphalgorithms;

import model.TransportGraph;

import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstPath extends AbstractPathSearch {

    public BreadthFirstPath(TransportGraph graph, String start, String end) {
        super(graph, start, end);
    }

    @Override
    public void search() {

        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(startIndex);
        marked[startIndex] = true;
        edgeTo[startIndex] = -1;
        nodesVisited.add(graph.getStation(startIndex));

        while (queue.size() != 0) {
            int vertex = queue.remove();

            for (int a : graph.getAdjacentVertices(vertex)) {
                if (!nodesVisited.contains(graph.getStation(a))) {
                    nodesVisited.add(graph.getStation(a));
                    marked[a] = true;
                    edgeTo[a] = vertex;
                    queue.add(a);
                }
            }
        }
        pathTo(endIndex);
    }
}
