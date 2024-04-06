package prao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_21938_DFS {
    static int N, M, T;
    static int count = 0;
    static int[][] screen;
    static int[][] move = { { -1, 0 }, { 0, -1 }, { 0, 1 }, { 1, 0 } };
    static boolean[][] visit;

    public static void main(String[] args) throws Exception {

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
                } else {
                    screen[i][j] = 0;
                }
            }
        }

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                if(screen[i][j] != 255 || visit[i][j]) {
                    continue;
                }
                dfs(i,j);
                count++;
            }
        }

        System.out.println(count);
        br.close();
    }

    private static void dfs(int x, int y) {
        visit[x][y] = true;

        for (int i = 0; i < 4; i++) {
            int nx = x + move[i][0];
            int ny = y + move[i][1];
            if (nx < 0 || ny < 0 || nx >= N || ny >= M || screen[nx][ny] < T || screen[nx][ny] == 0 || visit[nx][ny]) {
                continue;
            }
            dfs(nx, ny);
        }
    }
}
