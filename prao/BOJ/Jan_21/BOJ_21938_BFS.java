package prao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_21938_BFS {
    static int N, M, T;
    static int[] dx = {-1, 0, 0, 1};
    static int[] dy = {0, -1, 1, 0};
    static int[][] screen;
    static boolean[][] visit;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        screen = new int[N][M];
        visit = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                int red = Integer.parseInt(st.nextToken());
                int green = Integer.parseInt(st.nextToken());
                int blue = Integer.parseInt(st.nextToken());
                screen[i][j] = (red + green + blue) / 3;
            }
        }

        T = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (screen[i][j] >= T) {
                    screen[i][j] = 255;
                    continue;
                }
                screen[i][j] = 0;
            }
        }

        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (screen[i][j] != 255 || visit[i][j]) {
                    continue;
                }
                result++;
                bfs(new Pixel(i, j));
            }
        }
        System.out.println(result);
        br.close();
    }

    private static void bfs(Pixel pixel) {
        Queue<Pixel> queue = new LinkedList<>();
        queue.add(pixel);
        visit[pixel.x][pixel.y] = true;

        while (!queue.isEmpty()) {
            Pixel target = queue.poll();

            for (int i = 0; i < 4; i++) {
                int nx = target.x + dx[i];
                int ny = target.y + dy[i];

                if (nx < 0 || ny < 0 || nx >= N || ny >= M || visit[nx][ny]
                        || screen[nx][ny] == 0) {
                    continue;
                }
                visit[nx][ny] = true;
                queue.add(new Pixel(nx, ny));
            }
        }
    }
}

class Pixel {
    int x;
    int y;

    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
