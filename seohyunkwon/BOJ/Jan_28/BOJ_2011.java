package Jan_28;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ_2011 {
    public static void main(String[] args) throws IOException {
        // 10, 20, 30 과 같이 0이 포함되는 String 예외 처리 필요
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] keys = Arrays.stream(br.readLine().split(""))
                .mapToInt(Integer::parseInt)
                .toArray();
        int[] dp = new int[keys.length+1];
        dp[0] = 1;
        dp[1] = 1;
        if(dp[0] == 0) {
            System.out.println(0);
            return;
        }
        for(int i=2; i<keys.length+1; i++) {
            int num = Integer.parseInt(keys[i-2]+""+keys[i-1]);
            if(0<num && num<27 && keys[i-1] != 0) {
                dp[i] = dp[i-1]+dp[i-2];
                continue;
            }
            dp[i] = dp[i-1];
        }
        System.out.println(dp[keys.length]);
    }
}
