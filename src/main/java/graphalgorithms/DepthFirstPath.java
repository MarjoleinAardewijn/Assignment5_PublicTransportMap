package graphalgorithms;

import model.TransportGraph;

public class DepthFirstPath extends AbstractPathSearch {
    public DepthFirstPath(TransportGraph graph, String start, String end) {
        super(graph, start, end);
    }

    @Override
    public void search() {
        depthFirstSearch(this.startIndex);
        pathTo(endIndex);
    }

    private void depthFirstSearch(int vertex) {
        if (marked[vertex]) return;

        marked[vertex] = true;

        for (int a : graph.getAdjacentVertices(vertex)) {
            if (!marked[a]) {
                nodesVisited.add(graph.getStation(a));
                edgeTo[a] = vertex;

                // Recursive
                depthFirstSearch(a);
            }
        }
    }
}
