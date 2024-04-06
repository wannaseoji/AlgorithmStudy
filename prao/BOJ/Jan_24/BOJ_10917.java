import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        boolean[] isVisited = new boolean[N + 1];

        List<Integer>[] graph = new ArrayList[N + 1];
        for (int i = 0; i < N + 1; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            graph[Integer.parseInt(st.nextToken())].add(Integer.parseInt(st.nextToken()));
        }

        Deque<int[]> queue = new ArrayDeque<>();
        queue.add(new int[] { 1, 0 });
        isVisited[1] = true;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            if (curr[0] == N) {
                System.out.println(curr[1]);
                System.exit(0);
            }
            for (int next : graph[curr[0]]) {
                if (!isVisited[next]) {
                    isVisited[next] = true;
                    queue.add(new int[] { next, curr[1] + 1 });
                }
            }
        }
        System.out.println(-1);
    }
}
