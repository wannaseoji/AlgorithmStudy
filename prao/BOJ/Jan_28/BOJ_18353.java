import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class BOJ_18353 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] soldiers = new int[N];
        for (int i = 0; i < N; i++) {
            soldiers[i] = Integer.parseInt(st.nextToken());
        }

        int[] dp = new int[N];
        dp[0] = 1;
        int answer = 1;
        for (int i = 1; i < N; i++) {
            int max = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (soldiers[j] > soldiers[i] && dp[j] >= max) {
                    max = dp[j] + 1;
                }
            }
            dp[i] = max;
            answer = Math.max(answer, dp[i]);
        }
        bw.write((N - answer) + "");
        bw.flush();
        bw.close();
        br.close();
    }
}
