import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String s = br.readLine();
        int N = s.length();
        int[] dp = new int[N + 1];
        int start = s.charAt(0) == '0' ? 0 : 1;
        if (start == 0) {
            bw.write(dp[N] + "");
            bw.close();
            return;
        }
        dp[0] = start;
        dp[1] = start;

        for (int i = 2; i <= N; i++) {
            int front = s.charAt(i - 2) - '0';
            int curNum = s.charAt(i - 1) - '0';
            int twoNum = front * 10 + curNum;
            if ((curNum == 0 && front == 0) || (curNum == 0 && front > 2)) {
                break;
            }
            if (twoNum % 10 == 0) {
                dp[i - 1] = dp[i - 2];
                dp[i] = dp[i - 2];
            } else if (twoNum > 26 || front == 0) {
                dp[i] = dp[i - 1];
            } else {
                dp[i] = (dp[i - 1] + dp[i - 2]) % 1000000;
            }
        }
        bw.write(dp[N] + "");
        bw.close();
    }
}