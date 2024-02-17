import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Graph graph = new Graph(60); // You need to implement the initializeGraph method

        while (true) {
            displayMenu();

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    graph.readGraphFromFile();
                    break;
                case 2:
                    System.out.print("Enter start vertex: ");
                    String startVertex = scanner.nextLine();
                    System.out.print("Enter end vertex: ");
                    String endVertex = scanner.nextLine();
                    System.out.println("Is there a path? " + graph.IsThereAPath(startVertex, endVertex));
                    break;
                case 3:
                    System.out.print("Enter start vertex: ");
                    startVertex = scanner.nextLine();
                    System.out.print("Enter end vertex: ");
                    endVertex = scanner.nextLine();
                    graph.BFSfromTo(startVertex, endVertex);
                    break;
                case 4:
                    System.out.print("Enter start vertex: ");
                    startVertex = scanner.nextLine();
                    System.out.print("Enter end vertex: ");
                    endVertex = scanner.nextLine();
                    graph.DFSfromTo(startVertex, endVertex);
                    break;
                case 5:
                    System.out.print("Enter start vertex: ");
                    startVertex = scanner.nextLine();
                    System.out.print("Enter end vertex: ");
                    endVertex = scanner.nextLine();
                    System.out.println("Number of simple paths: " + graph.NumberOfSimplePaths(startVertex, endVertex));
                    break;
                case 6:
                    System.out.print("Enter vertex: ");
                    String vertex = scanner.nextLine();
                    List<String> neighbors = graph.Neighbors(vertex);
                    if (neighbors != null) {
                        System.out.println("Neighbors of " + vertex + ": " + neighbors);
                    }
                    break;
                case 7:
                    List<String> verticesWithHighestDegree = graph.HighestDegree();
                    System.out.println("Vertices with the highest degree: " + verticesWithHighestDegree);
                    break;
                case 8:
                    System.out.println("Is the graph directed? " + graph.IsDirected());
                    break;
                case 9:
                    System.out.print("Enter first vertex: ");
                    String vertex1 = scanner.nextLine();
                    System.out.print("Enter second vertex: ");
                    String vertex2 = scanner.nextLine();
                    System.out.println("Are they adjacent? " + graph.AreTheyAdjacent(vertex1, vertex2));
                    break;
                case 10:
                    System.out.print("Enter a vertex: ");
                    String vertexForCycle = scanner.nextLine();
                    System.out.println("Is there a cycle? " + graph.IsThereACycle(vertexForCycle));
                    break;
                case 11:
                    System.out.print("Enter a vertex: ");
                    String vertexForComponent = scanner.nextLine();
                    graph.NumberOfVerticesInComponent(vertexForComponent);
                    break;
                case 12:
                    System.out.print("Enter start vertex: ");
                    String startVertexForShortestPath = scanner.nextLine();
                    System.out.print("Enter end vertex: ");
                    String endVertexForShortestPath = scanner.nextLine();
                    graph.WhatIsShortestPathLength(startVertexForShortestPath, endVertexForShortestPath);
                    break;
                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("====== Graph Operations Menu ======");
        System.out.println("1. Read graph from file");
        System.out.println("2. Check if there is a path between two vertices");
        System.out.println("3. Perform BFS from one vertex to another");
        System.out.println("4. Perform DFS from one vertex to another");
        System.out.println("5. Count the number of simple paths between two vertices");
        System.out.println("6. Get neighbors of a vertex");
        System.out.println("7. Get vertices with the highest degree");
        System.out.println("8. Check if the graph is directed");
        System.out.println("9. Check if two vertices are adjacent");
        System.out.println("10. Check if there is a cycle in the graph from a given vertex");
        System.out.println("11. Count the number of vertices in the component containing a vertex");
        System.out.println("12. Find the shortest path length between two vertices");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }


}