import java.io.*;
import java.util.*;

public class Main {

    private static List<Integer>[] computers;
    private static int[] counts;
    private static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        computers = new ArrayList[N + 1];
        counts = new int[N + 1];

        for (int i = 0; i <= N ;i++) {
            computers[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            computers[a].add(b);
        }

        for (int computerNum = 1; computerNum <= N; computerNum++) {
            bfs(computerNum);
        }

        StringBuilder answer = new StringBuilder();
        int max = -1;
        for (int computerNum = 1; computerNum <= N; computerNum++) {
            if (max < counts[computerNum]) {
                max = counts[computerNum];
                answer = new StringBuilder();
            }
            if (max == counts[computerNum]) {
                answer.append(computerNum + " ");
            }
        }
        System.out.println(answer);
    }

    public static void bfs(int computerNum) {
        Deque<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[N + 1];
        q.addLast(computerNum);
        visited[computerNum] = true;

        while (!q.isEmpty()) {
            int com = q.pollFirst();

            for (int trustCom : computers[com]) {
                if (!visited[trustCom]) {
                    q.addLast(trustCom);
                    counts[trustCom]++;
                    visited[trustCom] = true;
                }
            }
        }
    }
}
