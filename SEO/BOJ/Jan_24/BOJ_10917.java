//BFS: BOJ_10917 / Your life / 실버1

import java.io.*;
import java.util.*;

public class BOJ_10917 {

    static ArrayList<Integer>[] dreams;
    static boolean[] visited;
    static int numberOfNodes;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        numberOfNodes = Integer.parseInt(tokenizer.nextToken());
        int numberOfEdges = Integer.parseInt(tokenizer.nextToken());

        // Initialize the adjacency list
        dreams = new ArrayList[numberOfNodes + 1];
        for (int i = 1; i <= numberOfNodes; i++) {
        	dreams[i] = new ArrayList<>();
        }

        // Populate the adjacency list
        for (int i = 0; i < numberOfEdges; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int sourceNode = Integer.parseInt(tokenizer.nextToken());
            int destinationNode = Integer.parseInt(tokenizer.nextToken());
            dreams[sourceNode].add(destinationNode);
        }

        // Perform BFS and print the result
        visited = new boolean[numberOfNodes + 1];
        visited[1] = true;
        System.out.println(BFS(1));
    }

    static int BFS(int start) {
        Deque<Node> queue = new ArrayDeque<>();
        queue.addLast(new Node(start, 0));

        while (!queue.isEmpty()) {
            Node curr = queue.pollFirst();

            for (int nextNode : dreams[curr.value]) {
                if (visited[nextNode]) {
                    continue;
                }

                if (nextNode == numberOfNodes) {
                    return curr.distance + 1;
                }

                visited[nextNode] = true;
                queue.addLast(new Node(nextNode, curr.distance + 1));
            }
        }

        return -1; // No path found
    }
}

class Node {
    int value, distance;

    public Node(int value, int distance) {
        this.value = value;
        this.distance = distance;
    }
}
