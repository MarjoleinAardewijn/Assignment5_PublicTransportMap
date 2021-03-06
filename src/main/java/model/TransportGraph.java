package model;

import java.util.*;

public class TransportGraph {

    private int numberOfStations;
    private int numberOfConnections;
    private List<Station> stationList;
    private Map<String, Integer> stationIndices;
    private List<Integer>[] adjacencyLists;
    private Connection[][] connections;

    public TransportGraph(int size) {
        this.numberOfStations = size;
        stationList = new ArrayList<>(size);
        stationIndices = new HashMap<>();
        connections = new Connection[size][size];
        adjacencyLists = (List<Integer>[]) new List[size];
        for (int vertex = 0; vertex < size; vertex++) {
            adjacencyLists[vertex] = new LinkedList<>();
        }
    }

    /**
     * @param vertex Station to be added to the stationList
     *               The method also adds the station with it's index to the map stationIndices
     */
    public void addVertex(Station vertex) {
        stationList.add(vertex);
        stationIndices.put(vertex.getStationName(), stationList.indexOf(vertex));
    }


    /**
     * Method to add an edge to a adjancencyList. The indexes of the vertices are used to define the edge.
     * Method also increments the number of edges, that is number of Connections.
     * The grap is bidirected, so edge(to, from) should also be added.
     *
     * @param from
     * @param to
     */
    private void addEdge(int from, int to) {
        adjacencyLists[from].add(to);
        adjacencyLists[to].add(from);
        numberOfConnections++;
    }


    /**
     * Method to add an edge in the form of a connection between stations.
     * The method also adds the edge as an edge of indices by calling addEdge(int from, int to).
     * The method adds the connecion to the connections 2D-array.
     * The method also builds the reverse connection, Connection(To, From) and adds this to the connections 2D-array.
     *
     * @param connection The edge as a connection between stations
     */
    public void addEdge(Connection connection) {
        int from = this.stationIndices.get(connection.getFrom().getStationName());
        int to = this.stationIndices.get(connection.getTo().getStationName());

        connections[from][to] = connection;
        connections[to][from] = connection;
        addEdge(from, to);
    }

    public List<Integer> getAdjacentVertices(int index) {
        return adjacencyLists[index];
    }

    public Connection getConnection(int from, int to) {
        return connections[from][to];
    }

    public int getIndexOfStationByName(String stationName) {
        return stationIndices.get(stationName);
    }

    public Station getStation(int index) {
        return stationList.get(index);
    }

    public int getNumberOfStations() {
        return numberOfStations;
    }

    public List<Station> getStationList() {
        return stationList;
    }

    public int getNumberEdges() {
        return numberOfConnections;
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder();
        resultString.append(String.format("Graph with %d vertices and %d edges: \n", numberOfStations, numberOfConnections));
        for (int indexVertex = 0; indexVertex < numberOfStations; indexVertex++) {
            resultString.append(stationList.get(indexVertex) + ": ");
            int loopsize = adjacencyLists[indexVertex].size() - 1;
            for (int indexAdjacent = 0; indexAdjacent < loopsize; indexAdjacent++) {
                resultString.append(stationList.get(adjacencyLists[indexVertex].get(indexAdjacent)).getStationName() + "-");
            }
            resultString.append(stationList.get(adjacencyLists[indexVertex].get(loopsize)).getStationName() + "\n");
        }
        return resultString.toString();
    }


    /**
     * A Builder helper class to build a TransportGraph by adding lines and building sets of stations and connections from these lines.
     * Then build the graph from these sets.
     */
    public static class Builder {

        private Set<Station> stationSet;
        private List<Line> lineList;
        private Set<Connection> connectionSet;
        private Map<Line, double[]> weightSet;
        private Map<String, Location> locationSet;

        public Builder() {
            lineList = new ArrayList<>();
            stationSet = new HashSet<>();
            connectionSet = new HashSet<>();
            weightSet = new HashMap<>();
            locationSet = new HashMap<>();
        }

        /**
         * Method to add a line to the list of lines and add stations to the line.
         *
         * @param lineDefinition String array that defines the line. The array should start with the name of the line,
         *                       followed by the type of the line and the stations on the line in order.
         * @return
         */
        public Builder addLine(String[] lineDefinition) {
            addLine(lineDefinition, null);
            return this;
        }

        /**
         * Method to add a line to the list of lines and add stations to the line.
         * This method also adds the weight of the line to the scope's weightSet's variable
         *
         * @param lineDefinition String array that defines the line. The array should start with the name of the line,
         *                       followed by the type of the line and the stations on the line in order.
         * @return
         */
        public Builder addLine(String[] lineDefinition, double[] weight) {
            addLine(lineDefinition, weight, null);
            return this;
        }

        /**
         * Method to add a line to the list of lines and add stations to the line.
         * This method also adds the weight of the line to the scope's weightSet's variable
         *
         * @param lineDefinition String array that defines the line. The array should start with the name of the line,
         *                       followed by the type of the line and the stations on the line in order.
         * @return
         */
        public Builder addLine(String[] lineDefinition, double[] weight, int[] locations) {
            Line line = new Line(lineDefinition[1], lineDefinition[0]);
            int locationIndex = 0;
            for (int i = 2; i < lineDefinition.length; i++) {
                // Replaces spaces in the string to fix the transfer calculation.
                Station station = new Station(lineDefinition[i].replace(" ", "_"));
                line.addStation(station);

                // If the locations have been set, then add it to the locations.
                if (locations != null && locations.length > 0) {
                    station.setLocation(new Location(locations[locationIndex++], locations[locationIndex++]));
                    System.out.println("Adding: " + locations.length + " locations to line: " + line.toString() + ", with location: " + station.getLocation());
                }
            }
            lineList.add(line);

            // If a weight has been set, then add it to the weightSet list.
            if (weight != null && weight.length > 0) {
                weightSet.put(line, weight);
            }
            return this;
        }

        /**
         * Method that reads all the lines and their stations to build a set of stations.
         * Stations that are on more than one line will only appear once in the set.
         * If the station list contains the station name, it adds the location to the station.
         *
         * @return
         */
        public Builder buildStationSet() {
            for (Line line : lineList) {
                for (Station station : line.getStationsOnLine()) {
                    if (locationSet.containsKey(station.getStationName())) {
                        station.setLocation(locationSet.get(station.getStationName()));
                    }
                    this.stationSet.add(station);
                }
            }
            return this;
        }

        /**
         * For every station on the set of station add the lines of that station to the lineList in the station
         *
         * @return
         */
        public Builder addLinesToStations() {
            for (Station station : stationSet) {
                for (Line line : lineList) {
                    for (Station stationOnLine : line.getStationsOnLine()) {
                        if (stationOnLine.getStationName().equals(station.getStationName())) {
                            station.addLine(line);
                        }
                    }
                }
            }
            return this;
        }

        /**
         * Method that uses the list of Lines to build connections from the consecutive stations in the stationList of a line.
         *
         * @return
         */
        public Builder buildConnections() {
            for (Line line : lineList) {
                for (int i = 0; i < line.getStationsOnLine().size() - 1; i++) {
                    final Connection connection = new Connection(line.getStationsOnLine().get(i), line.getStationsOnLine().get(i + 1));
                    if (weightSet.containsKey(line)) {
                        connection.setWeight(weightSet.containsKey(line) ? weightSet.get(line)[i] : 0);
                        connection.setLine(line);
                    }
                    connectionSet.add(connection);
                }
            }
            return this;
        }

        /**
         * Method that builds the graph.
         * All stations of the stationSet are addes as vertices to the graph.
         * All connections of the connectionSet are addes as edges to the graph.
         *
         * @return
         */
        public TransportGraph build() {
            TransportGraph graph = new TransportGraph(stationSet.size());
            for (Station station : stationSet) {
                graph.addVertex(station);
            }
            for (Connection connection : connectionSet) {
                graph.addEdge(connection);
            }
            return graph;
        }

    }
}
