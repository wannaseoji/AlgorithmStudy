package work;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int[] operands;
    static long[][] dp;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        operands = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            operands[i] = Integer.parseInt(st.nextToken());
        }

        dp = new long[21][100];

        for (int i = 0; i <= 20; i++) {
            for (int j = 0; j < 100; j++) {
                dp[i][j] = -1;
            }
        }

        bw.write(recursion(operands[0], 0) + "\n");
        bw.flush();
        
    }

    public static long recursion(int currentSum, int currentIndex) {
       
        if (currentSum < 0 || currentSum > 20) {
            return 0;
        }

       
        if (currentIndex == N - 2) {
            return (currentSum == operands[N - 1]) ? 1 : 0;
        }

        if (dp[currentSum][currentIndex] != -1) {
            return dp[currentSum][currentIndex];
        }

        dp[currentSum][currentIndex] = 0;

        return dp[currentSum][currentIndex] +=
                recursion(currentSum + operands[currentIndex + 1], currentIndex + 1) +
                recursion(currentSum - operands[currentIndex + 1], currentIndex + 1);
    }
}

