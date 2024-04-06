import java.io.*;
import java.util.*;

public class Main {

    private static List<List<Integer>> dreams = new ArrayList<>();
    private static int[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        visited = new int[N + 1];
        Arrays.fill(visited, -1);

        for (int i = 0; i <= N; i++) {
            dreams.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            dreams.get(x).add(y);
        }

        bfs(1, N);
        bw.write(visited[N] + "");
        bw.flush();
        bw.close();
    }

    public static void bfs(int start, int N) {
        Deque<Integer> q = new ArrayDeque<>();
        q.add(start);

        while (!q.isEmpty()) {
            int d = q.poll();

            for (int dreamNum : dreams.get(d)) {
                if (visited[dreamNum] == -1) {
                    q.add(dreamNum);
                    visited[dreamNum] = (visited[d] == -1 ? 0 : visited[d])  + 1;
                    if (dreamNum == N) {
                        return;
                    }
                }
            }
        }
    }
}