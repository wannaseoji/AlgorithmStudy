package Jan_31;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;

public class BOJ_12852 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] dp = new int[N+1];
        int[] path = new int[N+1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[N] = 0;
        for(int i=N; i>0; i--) {
            if(i-1>0 && dp[i]+1 < dp[i-1]) {
                dp[i-1] = dp[i]+1;
                path[i-1] = i;
            }
            if(i%2==0 && dp[i]+1 < dp[i/2]) {
                dp[i/2] = dp[i]+1;
                path[i/2] = i;
            }
            if(i%3==0 && dp[i]+1 < dp[i/3]) {
                dp[i/3] = dp[i]+1;
                path[i/3] = i;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(dp[1]+"\n");
        int idx = 1;
        int[] result = new int[dp[1]+1];
        for(int i=0; i<result.length; i++) {
            result[i] = idx;
            idx = path[idx];
        }
        for(int i=result.length-1; i>=0; i--) {
            sb.append(result[i]+" ");
        }
        System.out.println(sb.toString());
    }
}
