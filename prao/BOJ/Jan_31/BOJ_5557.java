import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class BOJ_5557 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        int[] num = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            num[i] = Integer.parseInt(st.nextToken());
        }

        long[][] dp = new long[N][21];
        dp[1][num[0]] = 1;

        for (int i = 2; i < N; i++) {
            for (int j = 0; j <= 20; j++) {
                int curr = j + num[i - 1];
                if (curr >= 0 && curr <= 20) {
                    dp[i][curr] += dp[i - 1][j];
                }

                curr = j - num[i - 1];
                if (curr >= 0 && curr <= 20) {
                    dp[i][curr] += dp[i - 1][j];
                }
            }
        }

        bw.write((dp[N - 1][num[N - 1]]) + "");
        bw.flush();
        bw.close();
        br.close();
    }
}
