package Jan_31;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
// DP LIS
public class BOJ_2631 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[] children = new int[N];

        for(int i=0; i<N; i++) {
            children[i] = Integer.parseInt(br.readLine());
        }

        int[] dp = new int[N];
        dp[0] = 1;

        for(int i=1; i<children.length; i++) {
            for(int j=0; j<i; j++) {
                if(children[j] < children[i])
                    dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            if(dp[i]==0) dp[i] = 1;
        }

        System.out.println(N-Arrays.stream(dp).max().getAsInt());

    }
}
