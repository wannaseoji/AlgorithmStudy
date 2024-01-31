import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];
        long[][] dp = new long[N][21];
        st = new StringTokenizer(br.readLine());


        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        dp[0][arr[0]]++;

        for (int i = 1 ; i < N - 1; i++) {
            for (int j = 0; j <= 20; j++) {
                if (dp[i - 1][j] >= 1) {
                    int n1 = j + arr[i];
                    int n2 = j - arr[i];
                    if (0 <= n1 && n1 <= 20) {
                        dp[i][n1] += dp[i - 1][j];
                    }
                    if (0 <= n2 && n2 <= 20) {
                        dp[i][n2] += dp[i - 1][j];
                    }
                }
            }
        }
        System.out.println(dp[N - 2][arr[N - 1]]);
    }

}
