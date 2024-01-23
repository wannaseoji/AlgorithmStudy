import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    private static final int BOARD_SIZE = 5;
    private static final int FAILURE = -1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int[] dr = new int[] {-1, 0, 1, 0};
        int[] dc = new int[] {0, -1, 0, 1};
        int[][] board = new int[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        int[] start = new int[] {Integer.parseInt(st.nextToken()),
                Integer.parseInt(st.nextToken())};

        int goal = (1 << 7) - 1;
        int[][][] visit = new int[BOARD_SIZE][BOARD_SIZE][goal + 1];
        visit[start[0]][start[1]][1] = 1;

        Deque<int[]> queue = new ArrayDeque<>();
        queue.add(new int[] {start[0], start[1], 0, 1});
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();

            if (curr[3] == goal) {
                System.out.println(curr[2]);
                br.close();
                System.exit(0);
            }

            for (int d = 0; d < 4; d++) {
                int nr = curr[0] + dr[d];
                int nc = curr[1] + dc[d];

                if (nr < 0 || nc < 0 || nr >= BOARD_SIZE || nc >= BOARD_SIZE || board[nr][nc] < 0
                        || visit[nr][nc][curr[3]] > 0) {
                    continue;
                }
                int nextMask = curr[3] | (board[nr][nc] > 0 ? 1 << board[nr][nc] : 0);
                visit[nr][nc][nextMask] = 1;
                queue.add(new int[] {nr, nc, curr[2] + 1, nextMask});
            }
        }
        System.out.println(FAILURE);
        br.close();
    }
}
