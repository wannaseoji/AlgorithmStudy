import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_25516 {
    private static List<Integer>[] graph;
    private static int[] apples;
    private static boolean[] visited;
    private static int n, k, result;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        graph = new ArrayList[n];
        apples = new int[n];
        visited = new boolean[n];
        result = 0;

        for(int i=0; i< graph.length; i++) {
            graph[i] = new ArrayList<>();
        }

        for(int i=0; i<n-1; i++) {
            st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            graph[p].add(c);
        }

        apples = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        bfs(0);

        System.out.println(result);

    }

    private static void bfs(int root){
        Deque<Node> queue = new ArrayDeque<>();
        queue.add(new Node(root, 0));
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if(visited[node.value]) continue;
            result += apples[node.value];
            visited[node.value] = true;
            if(node.depth == k) continue;
            for(int i:graph[node.value]) {
                queue.add(new Node(i, node.depth+1));
            }
        }
    }

    private static class Node {
        int value, depth;

        public Node(int value, int depth) {
            this.value = value;
            this.depth = depth;
        }
    }
}
