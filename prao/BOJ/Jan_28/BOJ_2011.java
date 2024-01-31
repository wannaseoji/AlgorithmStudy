import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class BOJ_2011 {

    private static final int DIVISOR = 1_000_000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String input = br.readLine();
        int len = input.length();

        int[] dp = new int[len + 1];
        dp[0] = 1;
        int prev = -1;
        for (int i = 1; i <= len; i++) {
            int curr = input.charAt(i - 1) - 48;
            if (curr != 0) {
                dp[i] = (dp[i] + dp[i - 1]) % DIVISOR;
            }
            if (i >= 2 && (prev == 1 || (prev == 2 && curr >= 0 && curr <= 6))) {
                dp[i] = (dp[i] + dp[i - 2]) % DIVISOR;
            }
            prev = curr;
        }
        bw.write(dp[len] + "");
        bw.flush();
        bw.close();
        br.close();
    }
}
