package Jan_31;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.StringTokenizer;

/**
 *  Unsolved
 */
public class BOJ_5557 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        br.readLine();
        int[] numbers = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int target = numbers[numbers.length - 1];
        int num = numbers[0];
        int[][] dp = new int[numbers.length][21];
        for (int i = 1; i < numbers.length - 1; i++) {
            if (isValidNumber(num + numbers[i])) {
                dp[i][num + numbers[i]]++;
            }

            if (isValidNumber(num - numbers[i])) {
                dp[i][num - numbers[i]]++;
            }

            if (isValidNumber(Integer.parseInt(num+""+numbers[i]))) {
                dp[i][Integer.parseInt(num+""+numbers[i])]++;
            }
        }
        System.out.println(Arrays.toString(dp));
    }

    private static boolean isValidNumber(int num) {
        if (0 <= num && num <= 20)
            return true;
        return false;
    }
}