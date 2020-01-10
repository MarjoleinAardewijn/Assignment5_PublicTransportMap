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

        while (!queue.isEmpty()) {
            int vertex = queue.delMin();
            if (vertex == endIndex) {
                pathTo(vertex);
                return;
            }

            nodesVisited.add(graph.getStation(vertex));

            for (Integer adjacentVertex : graph.getAdjacentVertices(vertex)) {
                nodesVisited.add(graph.getStation(adjacentVertex));

                Connection connection = graph.getConnection(vertex, adjacentVertex);
                if (this.distTo[adjacentVertex] > (distTo[vertex] + connection.getWeight())) {

                    // Add the connection and weight
                    this.distTo[adjacentVertex] = distTo[vertex] + connection.getWeight();

                    // Change the edge to the vertex
                    this.edgeTo[adjacentVertex] = vertex;

                    if (queue.contains(adjacentVertex)) {
                        queue.decreaseKey(adjacentVertex, distTo[adjacentVertex]);
                    } else {
                        queue.insert(adjacentVertex, distTo[adjacentVertex]);
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

    public double getTotalWeight() {
        double weight = 0;
        for (int i = 0; i < this.verticesInPath.size() - 1; i++) {
            weight += graph.getConnection(verticesInPath.get(i), verticesInPath.get(i + 1)).getWeight();
        }
        return weight;
    }
}
