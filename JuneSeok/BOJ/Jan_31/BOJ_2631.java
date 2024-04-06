import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] childs = new int[N + 1];
        int[] dp = new int[N + 1];
        dp[1] = 1;
        int lisNum = -1;
        int answer = 0;

        for (int i = 1; i <= N; i++) {
            childs[i] = Integer.parseInt(br.readLine());
        }

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j < i; j++) {
                if (childs[i] > childs[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            if (dp[i] == 0) {
                dp[i] = 1;
            }
            lisNum = Math.max(lisNum, dp[i]);
        }
        answer = N - lisNum;
        System.out.println(answer);
    }
}
