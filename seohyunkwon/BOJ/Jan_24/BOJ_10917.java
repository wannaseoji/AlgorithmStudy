package Jan_24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_10917 {
    private static List<Integer>[] dreams;
    private static boolean[] visited;
    private static int N, M, result;
    private static boolean isAllVisited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        dreams = new ArrayList[N+1];
        visited = new boolean[N+1];
        result = Integer.MAX_VALUE;
        isAllVisited = false;

        for(int i=1; i<=N; i++) {
            dreams[i] = new ArrayList<>();
        }

        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            dreams[x].add(y);
        }
        bfs(1);
        System.out.println(isAllVisited?result:-1);
    }

    private static void bfs(int root){
        Deque<Node> deque = new ArrayDeque<>();
        deque.add(new Node(root, 0));
        while (!deque.isEmpty()) {
            Node node = deque.poll();
            if(visited[node.value]) continue;
            visited[node.value] = true;
            if(node.value == N) {
                result = Math.min(result, node.dist);
                isAllVisited = true;
                continue;
            }
            for(int i : dreams[node.value]) {
                if(visited[i]) continue;
                deque.add(new Node(i, node.dist+1));
            }
        }
    }

    private static class Node {
        int value, dist;

        public Node(int value, int dist) {
            this.value = value;
            this.dist = dist;
        }
    }
}
