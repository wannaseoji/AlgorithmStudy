package Jan_28;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ_18353 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] arr = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        int[] dp = new int[N];

        dp[0] = 1;
        for(int i=1; i<arr.length; i++) {
            for(int j=0; j<i; j++) {
                if(arr[j]>arr[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
                if(dp[i] == 0) dp[i] = 1;
            }
        }
        System.out.println(N-Arrays.stream(dp).max().getAsInt());
    }
}
