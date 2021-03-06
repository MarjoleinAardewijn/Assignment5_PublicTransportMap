package graphalgorithms;

import model.Connection;
import model.IndexMinPQ;
import model.TransportGraph;

public class DijkstraShortestPath extends AbstractPathSearch {

    private IndexMinPQ<Double> queue;
    private double[] distTo;

    public DijkstraShortestPath(TransportGraph graph, String start, String end) {
        super(graph, start, end);

        queue = new IndexMinPQ<>(graph.getNumberOfStations());
        distTo = new double[graph.getNumberOfStations()];

        for (int v = 0; v < graph.getNumberOfStations(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }

        distTo[startIndex] = 0;
    }

    @Override
    public void search() {
        queue.insert(startIndex, (double) 0);
        edgeTo[startIndex] = -1;
        int current = startIndex;

        while (!queue.isEmpty() && current != endIndex){
            current = queue.delMin();

            nodesVisited.add(graph.getStation(current));

            for (Integer i : graph.getAdjacentVertices(current)) {

                Connection connection = graph.getConnection(current, i);
                if (distTo[i] > (distTo[current] + connection.getWeight())) {

                    // Add the connection and weight
                    distTo[i] = distTo[current] + connection.getWeight();

                    // Change the edge to the vertex
                    edgeTo[i] = current;

                    if (queue.contains(i)) {
                        queue.decreaseKey(i, distTo[i]);
                    } else {
                        queue.insert(i, distTo[i]);
                    }
                }
            }
        }
        pathTo(endIndex);
    }

    /**
     * @param vertex Determines whether a path exists to the station as an index from the start station
     * @return
     */
    @Override
    public boolean hasPathTo(int vertex) {
        return distTo[vertex] < Double.POSITIVE_INFINITY;
    }
}
