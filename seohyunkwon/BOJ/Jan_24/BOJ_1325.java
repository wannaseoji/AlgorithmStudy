package Jan_24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_1325 {
    private static List<Integer>[] graph;
    private static boolean[] visited;
    private static int[] counts;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        graph = new ArrayList[N+1];
        counts = new int[N+1];

        for(int i=1; i<=N; i++) {
            graph[i] = new ArrayList<>();
        }

        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            graph[A].add(B);
        }

        for(int i=1; i<=N; i++) {
            visited = new boolean[N+1];
            bfs(i);
        }

        int max = Integer.MIN_VALUE;

        for(int i=1; i<counts.length; i++) {
            max = max>counts[i]?max:counts[i];
        }

        StringBuilder sb = new StringBuilder();

        for(int i=1; i<counts.length; i++) {
            if(counts[i] == max) sb.append(i+" ");
        }

        System.out.println(sb.toString().trim());
    }

    private static void bfs(int root) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(root);
        visited[root] = true;
        while(!queue.isEmpty()) {
            int computer = queue.poll();
            for(int tmp : graph[computer]) {
                if(visited[tmp]) continue;
                visited[tmp] = true;
                counts[tmp]++;
                queue.add(tmp);
            }
        }

    }
}
