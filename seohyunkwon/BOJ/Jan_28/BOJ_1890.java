package Jan_28;

import java.io.*;
import java.util.Arrays;

public class BOJ_1890 {
    private static int N;
    public static void main(String[] args) throws IOException  {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        int[][] board = new int[N][N];

        for(int i=0; i<N; i++) {
            board[i] = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        long[][] dp = new long[N][N];
        dp[0][0] = 1;

        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                if(dp[i][j] == 0 || board[i][j] == 0) continue;
                int right = j+board[i][j];
                int down = i+board[i][j];
                if(inMap(i, right)) dp[i][right] += dp[i][j];
                if(inMap(down, j)) dp[down][j] += dp[i][j];

            }
        }
        System.out.println(dp[N-1][N-1]);
    }

    private static boolean inMap(int x, int y){
        if(x < 0 || y < 0 || x > N-1 || y > N-1) return false;
        return true;
    }
}
