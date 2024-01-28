// 병사 배치하기

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_18353 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		int[] dp = new int[n];
		dp[0] = 1;
		for (int i = 1; i < n; i++) {
			int dpMax = 1;
			for (int j = 0; j < i; j++) {
				if (arr[i] < arr[j]) {
					dpMax = Math.max(dpMax, dp[j] + 1);
				}
			}
			dp[i] = dpMax;
		}
		
		int max = 0;
		for (int i = 0; i < n; i++) {
			max = Math.max(max, dp[i]);
		}
		
		System.out.println(n - max);
	}
}
