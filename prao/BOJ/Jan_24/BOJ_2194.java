import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {

    static int N, M;

    static int[] size;
    static int[] dx = {-1, 0, 0, 1};
    static int[] dy = {0, -1, 1, 0};

    static int[][] graph;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        graph = new int[N + 1][M + 1];
        boolean[][] isVisited = new boolean[N + 1][M + 1];

        size = new int[] {Integer.parseInt(st.nextToken()),
                Integer.parseInt(st.nextToken())};

        int K = Integer.parseInt(st.nextToken());

        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            graph[Integer.parseInt(st.nextToken())][Integer.parseInt(st.nextToken())] = -1;
        }

        st = new StringTokenizer(br.readLine());
        int[] start = new int[] {Integer.parseInt(st.nextToken()),
                Integer.parseInt(st.nextToken())};

        st = new StringTokenizer(br.readLine());
        int[] end = new int[] {Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};

        Deque<int[]> queue = new ArrayDeque<>();
        // x,y,dist
        queue.add(new int[] {start[0], start[1], 0});
        isVisited[start[0]][start[1]] = true;
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            if (curr[0] == end[0] && curr[1] == end[1]) {
                System.out.println(curr[2]);
                br.close();
                System.exit(0);
            }
            for (int d = 0; d < 4; d++) {
                int nx = curr[0] + dx[d];
                int ny = curr[1] + dy[d];
                if (canNotMove(d, curr[0], curr[1]) || isVisited[nx][ny]) {
                    continue;
                }
                queue.add(new int[] {nx, ny, curr[2] + 1});
                isVisited[nx][ny] = true;
            }
        }
        System.out.println(-1);
        br.close();
    }

    private static boolean canNotMove(int d, int x, int y) {
        for (int a = x; a < x + size[0]; a++) {
            for (int b = y; b < y + size[1]; b++) {
                int fx = a + dx[d];
                int fy = b + dy[d];

                if (fx < 1 || fy < 1 || fx > N || fy > M || graph[fx][fy] == -1) {
                    return true;
                }
            }
        }
        return false;
    }
}
