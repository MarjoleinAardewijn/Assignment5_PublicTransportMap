package controller;

import graphalgorithms.BreadthFirstPath;
import graphalgorithms.DepthFirstPath;
import graphalgorithms.DijkstraShortestPath;
import model.Station;
import model.TransportGraph;

public class TransportGraphLauncher {

    public static void main(String[] args) {
//        assignmentA();
        assignmentB();
    }

    private static void assignmentB() {
        String[] redLine = {"Red", "metro", "Haven", "Marken", "Steigerplein", "Centrum", "Meridiaan", "Dukdalf", "Oostvaarders"};
        double[] redWeight = {4.5, 4.7, 6.1, 3.5, 5.4, 5.6};
        double[] redCords = {14, 1, 12, 3, 10, 5, 8, 8, 6, 9, 3, 10, 0, 11};

        String[] blueLine = {"Blue", "metro", "Trojelaan", "Coltrane Cirkel", "Meridiaan", "Robijnpark", "Violetplantsoen"};
        double[] blueWeight = {6, 5.3, 5.1, 3.3};
        double[] blueCords = {9, 3, 7, 6, 6, 9, 3, 3, 5, 14};

        String[] purpleLine = {"Purple", "metro", "Grote Sluis", "Grootzeil", "Coltrane Cirkel", "Centrum", "Swingstraat"};
        double[] purpleWeight = {6.2, 5.2, 3.8, 3.6};
        double[] purpleCords = {2, 3, 4, 6, 7, 6, 8, 8, 10, 9};

        String[] greenLine = {"Green", "metro", "Ymeerdijk", "Trojelaan", "Steigerplein", "Swingstraat", "Bachgracht", "Nobelplein"};
        double[] greenWeight = {5, 3.7, 6.9, 3.9, 3.4};
        double[] greenCords = {9, 0, 9, 3, 10, 5, 10, 9, 11, 11, 12, 13};

        String[] yellowLine = {"Yellow", "bus", "Grote Sluis", "Ymeerdijk", "Haven", "Nobelplein", "Violetplantsoen", "Oostvaarders", "Grote Sluis"};
        double[] yellowWeight = {26, 19, 37, 25, 22, 28};
        double[] yellowCords = {9, 0, 14, 1, 12, 13, 5, 14, 0, 11, 2, 3, 9, 0};

        TransportGraph transportGraph;
        TransportGraph.Builder builder = new TransportGraph.Builder()
                .addLine(redLine, redWeight)
                .addLine(blueLine, blueWeight)
                .addLine(greenLine, greenWeight)
                .addLine(yellowLine, yellowWeight)
                .buildStationSet()
                .addLinesToStations()
                .buildConnections();

        transportGraph = builder.build();

        // Uncomment to test the builder:
        System.out.println(transportGraph.toString());

        // DepthFirstPath algorithm
        System.out.println("Result of DepthFirstSearch:");
        DepthFirstPath dfpTest = new DepthFirstPath(transportGraph, "Coltrane Cirkel", "Oostvaarders");
        dfpTest.search();
        System.out.println(dfpTest);
        dfpTest.printNodesInVisitedOrder();
        System.out.println();

        // BreadthFirstPath algorithm
        System.out.println("Result of BreadthFirstPath:");
        BreadthFirstPath bfsTest = new BreadthFirstPath(transportGraph, "Coltrane Cirkel", "Oostvaarders");
        bfsTest.search();
        System.out.println(bfsTest);
        bfsTest.printNodesInVisitedOrder();
        System.out.println();

        // DijkstraShortestPath algorithm
        System.out.println("Result of DijkstraShortestPath:");
        DijkstraShortestPath dspTest = new DijkstraShortestPath(transportGraph, "Coltrane Cirkel", "Oostvaarders");
        dspTest.search();
        System.out.println(dspTest);
        dspTest.printNodesInVisitedOrder();
        System.out.printf("Total weight: %.2f\n", dspTest.getTotalWeight());
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
//        System.out.println("\nOerview of all the path from all stations to all other stations with the least connections:");
//        System.out.println("-----------------------------------------------");
//
//        for (Station s : transportGraph.getStationList()) {
//            for (Station s2 : transportGraph.getStationList()) {
//                if (!s.getStationName().equals(s2.getStationName())) {
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
