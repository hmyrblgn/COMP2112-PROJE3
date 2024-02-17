// Adjacency Matrix representation in Java

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph {
    private int adjMatrix[][];
    private String cities[];
    private int numVertices;


    // Initialize the matrix and hash table
    public Graph(int numVertices) {
        this.numVertices = numVertices;
        adjMatrix = new int[numVertices][numVertices];

        cities = new String[353];
        for(int i=0;i<cities.length;i++){
            cities[i]="";
        }
    }

    // Hash function for vertices
    public int hashFunction(String key) {
        String keyStr = key.toString();
        int hash = 0;
        for (int i = 0; i < keyStr.length(); i++) {
            hash = 31 * hash + keyStr.charAt(i);
        }
        return Math.abs(hash) % numVertices;
    }


    public void setIndex(String str){
        int hash = hashFunction(str);

        if(!cities[hash].equals("") && !cities[hash].equals(str)){
            for(int i=0;i<cities.length;i++){
                if(cities[i].equals(str)){
                    hash=i;
                    break;
                }
                if(cities[i].equals("")){
                    hash=i;
                    break;
                }
            }
        }
        cities[hash]=str;
    }

    public int getIndex(String str){
        for(int i=0;i< cities.length; i++){
            if(cities[i].equals(str)){
                return i;
            }
        }

        return -1;
    }

    // Add edges
    public void addEdge(String start, String end, int weight) {
        int i = getIndex(start);
        int j = getIndex(end);
        adjMatrix[i][j] = weight;


    }

    // Remove edges
    public void removeEdge(String start, String end) {
        int i = getIndex(start);
        int j = getIndex(end);
        adjMatrix[i][j] = 0;
    }

    public void readGraphFromFile() {
        String filePath = "graph.txt";

        // Read the file and build the graph
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" -> ");
                String startNode = parts[0];

                String[] edges = parts[1].split(", ");
                for (int i=0; i<edges.length; i++) {
                    String[] edgeParts = edges[i].split(": ");
                    String endNode = edgeParts[0];
                    int weight = Integer.parseInt(edgeParts[1]);
                    this.setIndex(startNode);
                    this.setIndex(endNode);
                    this.addEdge(startNode, endNode, weight);
                }
            }

            scanner.close();
        }catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public boolean IsThereAPath(String v1, String v2) {
        int startIdx = getIndex(v1);
        int endIdx = getIndex(v2);

        if (startIdx == -1 || endIdx == -1) {
            // Either v1 or v2 is not in the graph
            return false;
        }

        // Use DFS to check for a path
        boolean[] visited = new boolean[numVertices];
        return DFS(startIdx, endIdx, visited);
    }

    private boolean DFS(int startIdx, int endIdx, boolean[] visited) {
        // Mark the current vertex as visited
        visited[startIdx] = true;

        // If the destination vertex is reached, return true
        if (startIdx == endIdx) {
            return true;
        }

        // Recur for all the vertices adjacent to the current vertex
        for (int i = 0; i < numVertices; i++) {
            if (adjMatrix[startIdx][i] != 0 && !visited[i]) {
                if (DFS(i, endIdx, visited)) {
                    return true;
                }
            }
        }

        // If no path is found, return false
        return false;
    }

    public void BFSfromTo(String v1, String v2) {
        int startIdx = getIndex(v1);
        int endIdx = getIndex(v2);

        if (startIdx == -1 || endIdx == -1) {
            System.out.println("Invalid vertices");
            return;
        }

        boolean[] visited = new boolean[numVertices];
        int[] parent = new int[numVertices];

        Queue<Integer> queue = new LinkedList<>();
        queue.add(startIdx);
        visited[startIdx] = true;
        parent[startIdx] = -1;

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();

            if (currentVertex == endIdx) {
                // Found the destination vertex, print the path
                System.out.println("Sequence of vertices and edges(BFS) from " + cities[startIdx] + " to " + cities[endIdx] + ":");
                printPath(startIdx, endIdx, parent);
                System.out.println();
                return;
            }

            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[currentVertex][i] != 0 && !visited[i]) {
                    queue.add(i);
                    visited[i] = true;
                    parent[i] = currentVertex;
                }
            }
        }

        // No path found
        System.out.println("No path found between " + v1 + " and " + v2);
    }

    public void DFSfromTo(String v1, String v2) {
        int startIdx = getIndex(v1);
        int endIdx = getIndex(v2);

        if (startIdx == -1 || endIdx == -1) {
            System.out.println("Invalid vertices");
            return;
        }

        boolean[] visited = new boolean[numVertices];
        int[] parent = new int[numVertices];

        // Stack to perform DFS
        Stack<Integer> stack = new Stack<>();
        stack.push(startIdx);
        visited[startIdx] = true;
        parent[startIdx] = -1;

        while (!stack.isEmpty()) {
            int currentVertex = stack.pop();

            if (currentVertex == endIdx) {
                // Found the destination vertex, print the path
                System.out.println("Sequence of vertices and edges(DFS) from " + cities[startIdx] + " to " + cities[endIdx] + ":");
                printPath(startIdx, endIdx, parent);
                System.out.println();
                return;
            }

            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[currentVertex][i] != 0 && !visited[i]) {
                    stack.push(i);
                    visited[i] = true;
                    parent[i] = currentVertex;
                }
            }
        }

        // No path found
        System.out.println("No path found between " + v1 + " and " + v2);
    }

    public int NumberOfSimplePaths(String v1, String v2) {
        int startIdx = getIndex(v1);
        int endIdx = getIndex(v2);

        if (startIdx == -1 || endIdx == -1) {
            System.out.println("Invalid vertices");
            return 0;
        }

        boolean[] visited = new boolean[numVertices];
        return countSimplePaths(startIdx, endIdx, visited);
    }

    private int countSimplePaths(int currentVertex, int endIdx, boolean[] visited) {
        // If the current vertex is the destination, there is one path
        if (currentVertex == endIdx) {
            return 1;
        }

        visited[currentVertex] = true;

        int countPaths = 0;

        for (int i = 0; i < numVertices; i++) {
            if (adjMatrix[currentVertex][i] != 0 && !visited[i]) {
                countPaths += countSimplePaths(i, endIdx, visited);
            }
        }

        visited[currentVertex] = false; // Backtrack

        return countPaths;
    }

    public List<String> Neighbors(String v1) {
        int index = getIndex(v1);

        if (index == -1) {
            System.out.println("Invalid vertex");
            return null;
        }

        List<String> neighborsList = new ArrayList<>();

        for (int i = 0; i < numVertices; i++) {
            if (adjMatrix[index][i] != 0) {
                neighborsList.add(cities[i]);
            }
        }

        return neighborsList;
    }

    public List<String> HighestDegree() {
        int maxDegree = -1;
        List<String> verticesWithHighestDegree = new ArrayList<>();

        for (int i = 0; i < numVertices; i++) {
            int degree = calculateDegree(i);

            if (degree > maxDegree) {
                maxDegree = degree;
                verticesWithHighestDegree.clear();
                verticesWithHighestDegree.add(cities[i]);
            } else if (degree == maxDegree) {
                verticesWithHighestDegree.add(cities[i]);
            }
        }

        return verticesWithHighestDegree;
    }

    private int calculateDegree(int vertexIndex) {
        int degree = 0;

        for (int i = 0; i < numVertices; i++) {
            if (adjMatrix[vertexIndex][i] != 0) {
                degree++;
            }
        }

        return degree;
    }

    public boolean IsDirected() {
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (adjMatrix[i][j] != adjMatrix[j][i]) {
                    // If there is any asymmetry in the matrix, the graph is directed
                    return true;
                }
            }
        }
        return false;
    }

    public boolean AreTheyAdjacent(String v1, String v2) {
        int index1 = getIndex(v1);
        int index2 = getIndex(v2);

        if (index1 == -1 || index2 == -1) {
            System.out.println("Invalid vertices");
            return false;
        }

        return adjMatrix[index1][index2] != 0;
    }

    public boolean IsThereACycle(String v1) {
        int startIdx = getIndex(v1);

        if (startIdx == -1) {
            System.out.println("Invalid vertex");
            return false;
        }

        boolean[] visited = new boolean[numVertices];
        List<Integer> path = new ArrayList<>();

        return hasCycleDFS(startIdx, startIdx, visited, path);
    }

    private boolean hasCycleDFS(int currentVertex, int startVertex, boolean[] visited, List<Integer> path) {
        visited[currentVertex] = true;
        path.add(currentVertex);

        for (int i = 0; i < numVertices; i++) {
            if (adjMatrix[currentVertex][i] != 0) {
                if (!visited[i]) {
                    if (hasCycleDFS(i, startVertex, visited, path)) {
                        return true;
                    }
                } else if (i == startVertex) {
                    // We found a cycle
                    printCyclePath(path);
                    return true;
                }
            }
        }

        // Backtrack
        visited[currentVertex] = false;
        path.remove(path.size() - 1);

        return false;
    }

    private void printCyclePath(List<Integer> path) {
        System.out.print("Cycle Path: ");
        for (int vertex : path) {
            System.out.print(cities[vertex] + " ");
        }
        System.out.println();
    }

    public void NumberOfVerticesInComponent(String v1) {
        int startIdx = getIndex(v1);

        if (startIdx == -1) {
            System.out.println("Invalid vertex");
            return;
        }

        boolean[] visited = new boolean[numVertices];
        int componentSize = countComponentVertices(startIdx, visited);

        System.out.println("Number of vertices in the component containing " + v1 + ": " + componentSize);
    }

    private int countComponentVertices(int currentVertex, boolean[] visited) {
        visited[currentVertex] = true;

        int componentSize = 1;

        for (int i = 0; i < numVertices; i++) {
            if (adjMatrix[currentVertex][i] != 0 && !visited[i]) {
                componentSize += countComponentVertices(i, visited);
            }
        }

        return componentSize;
    }



    public void WhatIsShortestPathLength(String v1, String v2) {
        int startIdx = getIndex(v1);
        int endIdx = getIndex(v2);

        if (startIdx == -1 || endIdx == -1) {
            System.out.println("Invalid vertices");
            return;
        }

        List<List<String>> allPaths = new ArrayList<>();
        List<String> currentPath = new ArrayList<>();
        boolean[] visited = new boolean[numVertices];

        findShortestPaths(endIdx, startIdx, visited, currentPath, allPaths);

        if (allPaths.isEmpty()) {
            System.out.println(v1 + " --x-- " + v2);
            return;
        }

        int shortestLength = Integer.MAX_VALUE;
        for (List<String> path : allPaths) {
            int pathLength = calculatePathLength(path);
            if (pathLength < shortestLength) {
                shortestLength = pathLength;
            }
        }
        System.out.println("ALL PATHS FROM "+v1+" to "+v2+": "+allPaths);
        System.out.println("Shortest path length from " + v1 + " to " + v2 + ": " + shortestLength);
    }

    private void findShortestPaths(int endIdx, int currentIdx, boolean[] visited, List<String> currentPath, List<List<String>> allPaths) {
        visited[currentIdx] = true;
        currentPath.add(cities[currentIdx]);

        if (currentIdx == endIdx) {
            List<String> newPath = new ArrayList<>(currentPath);
            allPaths.add(newPath);
        } else {
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[currentIdx][i] != 0 && !visited[i]) {
                    findShortestPaths(endIdx, i, visited.clone(), new ArrayList<>(currentPath), allPaths);
                }
            }
        }
    }

    private int calculatePathLength(List<String> path) {
        int length = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            int fromIdx = getIndex(path.get(i));
            int toIdx = getIndex(path.get(i + 1));
            length += adjMatrix[fromIdx][toIdx];
        }
        return length;
    }



    private void printPath(int startIdx, int endIdx, int[] parent) {
        if (startIdx == endIdx) {
            System.out.print(cities[startIdx]);
        } else {
            printPath(startIdx, parent[endIdx], parent);
            System.out.print(" -> " + cities[endIdx]);
        }

    }



}