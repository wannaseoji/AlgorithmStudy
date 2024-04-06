import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class BOJ_3190 {

    static int N;

    static int[][] map;
    static int[][] move = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    static List<int[]> snake = new ArrayList<>();
    static Map<Integer, String> change = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = parseInt(br.readLine());
        map = new int[N][N];

        int K = parseInt(br.readLine());
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            map[parseInt(st.nextToken()) - 1][parseInt(st.nextToken()) - 1] = 1;
        }

        int L = parseInt(br.readLine());
        for (int i = 0; i < L; i++) {
            st = new StringTokenizer(br.readLine());
            change.put(parseInt(st.nextToken()), st.nextToken());
        }
        game();
    }

    private static void game() {
        int curX = 0;
        int curY = 0;
        int time = 0;
        int dir = 0;

        snake.add(new int[] {curX, curY});

        while (true) {
            time++;

            int nx = curX + move[dir][0];
            int ny = curY + move[dir][1];

            if (isEnd(nx, ny)) {
                break;
            }

            if (map[nx][ny] == 1) {
                map[nx][ny] = 0;
                snake.add(new int[] {nx, ny});
            } else {
                snake.add(new int[] {nx, ny});
                snake.remove(0);
            }

            if (change.containsKey(time)) {
                if (change.get(time).equals("D")) {
                    dir += 1;
                    if (dir == 4) {
                        dir = 0;
                    }
                } else {
                    dir -= 1;
                    if (dir == -1) {
                        dir = 3;
                    }
                }
            }
            curX = nx;
            curY = ny;
        }
        System.out.println(time);
    }

    private static boolean isEnd(int x, int y) {
        if (x < 0 || y < 0 || x >= N || y >= N) {
            return true;
        }

        for (int i = 0; i < snake.size(); i++) {
            int[] tail = snake.get(i);
            if (x == tail[0] && y == tail[1]) {
                return true;
            }
        }
        return false;
    }
}
