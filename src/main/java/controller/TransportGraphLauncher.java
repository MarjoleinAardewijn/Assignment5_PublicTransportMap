package controller;

import graphalgorithms.A_Star;
import graphalgorithms.BreadthFirstPath;
import graphalgorithms.DepthFirstPath;
import graphalgorithms.DijkstraShortestPath;
import model.Station;
import model.TransportGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransportGraphLauncher {

    public static void main(String[] args) {
//        assignmentA();
        assignmentBC();
    }

    private static void assignmentBC() {
        String[] redLine = {"Red", "metro", "Haven", "Marken", "Steigerplein", "Centrum", "Meridiaan", "Dukdalf", "Oostvaarders"};
        double[] redWeight = {4.5, 4.7, 6.1, 3.5, 5.4, 5.6};
//        int[][] redCords = {{14,1}, {12,3}, {10,5}, {8,8},{6,9}, {3,10}, {0,11}};
        int[] redCords = {14, 1, 12, 3, 10, 5, 8, 8, 6, 9, 3, 10, 0, 11};

        String[] blueLine = {"Blue", "metro", "Trojelaan", "Coltrane Cirkel", "Meridiaan", "Robijnpark", "Violetplantsoen"};
        double[] blueWeight = {6, 5.3, 5.1, 3.3};
//        int[][] blueCords = {{9,3}, {7,6}, {6,9},{5,1}, {5,14}};
        int[] blueCords = {9, 3, 7, 6, 6, 9, 3, 3, 5, 14};

        String[] purpleLine = {"Purple", "metro", "Grote Sluis", "Grootzeil", "Coltrane Cirkel", "Centrum", "Swingstraat"};
        double[] purpleWeight = {6.2, 5.2, 3.8, 3.6};
//        int[][] purpleCords = {{2,3}, {4,6}, {7,6}, {8,8}, {10,9}};
        int[] purpleCords = {2, 3, 4, 6, 7, 6, 8, 8, 10, 9};

        String[] greenLine = {"Green", "metro", "Ymeerdijk", "Trojelaan", "Steigerplein", "Swingstraat", "Bachgracht", "Nobelplein"};
        double[] greenWeight = {5, 3.7, 6.9, 3.9, 3.4};
//        int[][] greenCords = {{9,0}, {9,3}, {10,5}, {10,9}, {11,11}, {12,13}};
        int[] greenCords = {9, 0, 9, 3, 10, 5, 10, 9, 11, 11, 12, 13};

        String[] yellowLine = {"Yellow", "bus", "Grote Sluis", "Ymeerdijk", "Haven", "Nobelplein", "Violetplantsoen", "Oostvaarders", "Grote Sluis"};
        double[] yellowWeight = {26, 19, 37, 25, 22, 28};
//        int[][] yellowCords = {{2,3}, {9,0}, {14,1}, {12,3}, {5,14}, {0,11}, {2,3}};
        int[] yellowCords = {9, 0, 14, 1, 12, 13, 5, 14, 0, 11, 2, 3, 9, 0};

        TransportGraph transportGraph = new TransportGraph.Builder()
                .addLine(redLine, redWeight, redCords)
                .addLine(blueLine, blueWeight, blueCords)
                .addLine(purpleLine, purpleWeight, purpleCords)
                .addLine(greenLine, greenWeight, greenCords)
                .addLine(yellowLine, yellowWeight, yellowCords)
                .buildStationSet()
                .addLinesToStations()
                .buildConnections()
                .build();

        System.out.println(transportGraph);

        // Uncomment to test the builder:
        System.out.println(transportGraph.toString());
        
        final String startStation = "Steigerplein";
        final String endSation = "Oostvaarders";

        // DepthFirstPath algorithm
        System.out.println("Result of DepthFirstSearch:");
        DepthFirstPath dfpTest = new DepthFirstPath(transportGraph, startStation, endSation);
        dfpTest.search();
        System.out.println(dfpTest);
        dfpTest.printNodesInVisitedOrder();
        System.out.println();

        // BreadthFirstPath algorithm
        System.out.println("Result of BreadthFirstPath:");
        BreadthFirstPath bfsTest = new BreadthFirstPath(transportGraph, startStation, endSation);
        bfsTest.search();
        System.out.println(bfsTest);
        bfsTest.printNodesInVisitedOrder();
        System.out.println();

        // DijkstraShortestPath algorithm
        System.out.println("Result of DijkstraShortestPath:");
        DijkstraShortestPath dspTest = new DijkstraShortestPath(transportGraph, startStation, endSation);
        dspTest.search();
        System.out.println(dspTest);
        dspTest.printNodesInVisitedOrder();
        System.out.printf("Total weight: %.2f and vertices visited: %d\n", dspTest.getTotalWeight(), dspTest.countVertices());
        System.out.println();

        // A_Star algorithm
        System.out.println("Result of A_Star:");
        A_Star asTest = new A_Star(transportGraph, startStation, endSation);
        asTest.search();
        System.out.println(asTest);
        asTest.printNodesInVisitedOrder();
        System.out.printf("Total weight: %.2f and vertices visited: %d\n", asTest.getTotalWeight(), asTest.countVertices());
        System.out.println();


        // Full overview

        System.out.printf("%-10s%-10s%-10s\n", "Dijkstra", "A_Star", "Path");
        int dTotal = 0, aTotal = 0;
        for (Station from : transportGraph.getStationList()) {
            for (Station to : transportGraph.getStationList()) {
                if (!from.getStationName().equals(to)) {
                    // Dijkstra test
                    dspTest = new DijkstraShortestPath(transportGraph, from.getStationName(), to.getStationName());
                    dspTest.search();
                    dTotal += dspTest.countVertices();

                    // A_Star test
                    asTest = new A_Star(transportGraph, from.getStationName(), to.getStationName());
                    asTest.search();
                    aTotal += asTest.countVertices();

                    System.out.printf("%-10s%-10s%-10s\n", dspTest.countVertices(), asTest.countVertices(), dspTest);
                }
            }
        }

        System.out.println("\n------------------");
        System.out.printf("Dijkstra:\t%d\nA_Star:\t\t%d\n", dTotal, aTotal);
        System.out.println("------------------");
    }

//    private static void assignmentA() {
//        String[] redLine = {"red", "metro", "A", "B", "C", "D"};
//        String[] blueLine = {"blue", "metro", "E", "B", "F", "G"};
//        String[] greenLine = {"green", "metro", "H", "I", "C", "G", "J"};
//        String[] yellowLine = {"yellow", "bus", "A", "E", "H", "D", "G", "A"};
//
//        TransportGraph transportGraph;
//        TransportGraph.Builder builder = new TransportGraph.Builder()
//                .addLine(redLine)
//                .addLine(blueLine)
//                .addLine(greenLine)
//                .addLine(yellowLine)
//                .buildStationSet()
//                .addLinesToStations()
//                .buildConnections();
//
//        transportGraph = builder.build();
//
//        // Uncomment to test the builder:
//        System.out.println(transportGraph.toString());
//
//        // DepthFirstPath algorithm
//        System.out.println("Result of DepthFirstSearch:");
//        DepthFirstPath dfpTest = new DepthFirstPath(transportGraph, "E", "J");
//        dfpTest.search();
//        System.out.println(dfpTest);
//        dfpTest.printNodesInVisitedOrder();
//        System.out.println();
//
//        // BreadthFirstPath algorithm
//        System.out.println("Result of BreadthFirstPath:");
//        BreadthFirstPath bfsTest = new BreadthFirstPath(transportGraph, "E", "J");
//        bfsTest.search();
//        System.out.println(bfsTest);
//        bfsTest.printNodesInVisitedOrder();
//
//        // A - 5
//        System.out.println("\nOverview of all the paths from all stations to all other stations with the least connections:");
//        System.out.println("-----------------------------------------------");
//
//        for (Station s : transportGraph.getStationList()) {
//            for (Station s2 : transportGraph.getStationList()) {
//                if(!s.getStationName().equals(s2.getStationName())) {
//                    DepthFirstPath dfp = new DepthFirstPath(transportGraph, s.getStationName(), s2.getStationName());
//                    dfp.search();
//                    System.out.println("DepthFirstPath: " + dfp);
//
//                    BreadthFirstPath bfp = new BreadthFirstPath(transportGraph, s.getStationName(), s2.getStationName());
//                    bfp.search();
//                    System.out.println("BreadthFirstPath: " + bfp);
//
//                    System.out.println("-----------------------------------------------");
//                }
//            }
//        }
//    }
}
