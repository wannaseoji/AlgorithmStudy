import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class BOJ_2631 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        int[] children = new int[N];
        int[] dp = new int[N];
        int answer = 0;

        for(int i = 0; i < N; i++) {
            children[i] = Integer.parseInt(br.readLine());
        }

        dp[0] = 1;
        for(int i = 1; i < N; i++) {
            int max = 1;
            for(int j = i -1; j >= 0; j--) {
                if(children[i] > children[j] && dp[j] >= max) {
                    max = dp[j] + 1;
                }
                dp[i] = max;
                answer = Math.max(answer, dp[i]);
            }
        }
        bw.write((N - answer) + "");
        bw.flush();
        bw.close();
        br.close();
    }
}
