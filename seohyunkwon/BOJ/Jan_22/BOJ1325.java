import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ1325 {
    private static List<Integer>[] graph;
    private static int N, M;
    private static int[] result;
    private static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        graph = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            graph[B].add(A);
        }

        result = new int[N + 1];
        int max = Integer.MIN_VALUE;

        for (int i = 1; i <= N; i++) {
            visited = new boolean[N + 1];
            bfs(i);
        }

        for (int value : result) {
            max = Math.max(max, value);
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= N; i++) {
            if (result[i] == max) {
                sb.append(i).append(" ");
            }
        }

        System.out.println(sb.toString().trim());
    }

    private static void bfs(int start) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start] = true;

        int count = 0;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            count++;

            for (int newNode : graph[node]) {
                if (!visited[newNode]) {
                    visited[newNode] = true;
                    queue.add(newNode);
                }
            }
        }

        result[start] = count;
    }
}
