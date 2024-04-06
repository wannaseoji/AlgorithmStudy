import java.io.*;
import java.util.*;

public class Main {

    private static int[][] map;
    private static int N;
    private static long[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        dp = new long[N][N];
        dp[0][0] = 1;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == N - 1 && j == N - 1) {
                    break;
                }
                if (dp[i][j] > 0) {
                    int tx = j + map[i][j];
                    int ty = i + map[i][j];

                    if (tx < N) {
                        dp[i][tx] += dp[i][j];
                    }
                    if (ty < N) {
                        dp[ty][j] += dp[i][j];
                    }
                }
            }
        }

        bw.write(dp[N-1][N-1] + "");
        bw.close();
    }
}