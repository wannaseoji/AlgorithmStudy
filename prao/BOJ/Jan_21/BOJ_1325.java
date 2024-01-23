package prao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_1325 {
    static int[] answer;
    static boolean[] visit;
    static List<Integer>[] relation;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        answer = new int[N + 1];
        relation = new ArrayList[N + 1];
        for (int i = 0; i < N + 1; i++) {
            relation[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            relation[A].add(B);
        }

        for (int i = 1; i < N + 1; i++) {
            visit = new boolean[N + 1];
            bfs(i);
        }

        int max = Integer.MIN_VALUE;

        for (int i = 1; i < N + 1; i++) {
            max = Math.max(max, answer[i]);
        }

        for (int i = 1; i < N + 1; i++) {
            if (answer[i] == max) {
                System.out.print(i + " ");
            }
        }
        br.close();
    }

    private static void bfs(int computer) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(computer);
        visit[computer] = true;

        while (!queue.isEmpty()) {
            int target = queue.poll();
            for (int friend : relation[target]) {
                if (visit[friend]) {
                    continue;
                }
                visit[friend] = true;
                answer[friend]++;
                queue.add(friend);
            }
        }
    }
}
