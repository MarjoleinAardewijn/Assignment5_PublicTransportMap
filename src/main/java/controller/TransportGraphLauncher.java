package controller;

import graphalgorithms.BreadthFirstPath;
import graphalgorithms.DepthFirstPath;
import model.Station;
import model.TransportGraph;

public class TransportGraphLauncher {

    public static void main(String[] args) {
        String[] redLine = {"red", "metro", "A", "B", "C", "D"};
        String[] blueLine = {"blue", "metro", "E", "B", "F", "G"};
        String[] greenLine = {"green", "metro", "H", "I", "C", "G", "J"};
        String[] yellowLine = {"yellow", "bus", "A", "E", "H", "D", "G", "A"};

        // Assignment A

        TransportGraph transportGraph;
        TransportGraph.Builder builder = new TransportGraph.Builder()
                .addLine(redLine)
                .addLine(blueLine)
                .addLine(greenLine)
                .addLine(yellowLine)
                .buildStationSet()
                .addLinesToStations()
                .buildConnections();

        transportGraph = builder.build();

        // Uncomment to test the builder:
        System.out.println(transportGraph.toString());

        // DepthFirstPath algorithm
        System.out.println("Result of DepthFirstSearch:");
        DepthFirstPath dfpTest = new DepthFirstPath(transportGraph, "E", "J");
        dfpTest.search();
        System.out.println(dfpTest);
        dfpTest.printNodesInVisitedOrder();
        System.out.println();

        // BreadthFirstPath algorithm
        System.out.println("Result of BreadthFirstPath:");
        BreadthFirstPath bfsTest = new BreadthFirstPath(transportGraph, "E", "J");
        bfsTest.search();
        System.out.println(bfsTest);
        bfsTest.printNodesInVisitedOrder();

        // A - 5
        System.out.println("\nOerview of all the path from all stations to all other stations with the least connections:");
        System.out.println("-----------------------------------------------");

        for (Station s : transportGraph.getStationList()) {
            for (Station s2 : transportGraph.getStationList()) {
                if(!s.getStationName().equals(s2.getStationName())) {
                    DepthFirstPath dfp = new DepthFirstPath(transportGraph, s.getStationName(), s2.getStationName());
                    dfp.search();
                    System.out.println("DepthFirstPath: " + dfp);

                    BreadthFirstPath bfp = new BreadthFirstPath(transportGraph, s.getStationName(), s2.getStationName());
                    bfp.search();
                    System.out.println("BreadthFirstPath: " + bfp);

                    System.out.println("-----------------------------------------------");
                }
            }
        }

    }
}
