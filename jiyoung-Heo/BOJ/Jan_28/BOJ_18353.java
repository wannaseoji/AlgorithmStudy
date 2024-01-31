import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		ArrayList<Integer> powerList = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			powerList.add(Integer.parseInt(st.nextToken()));
		}
		int[] dp = new int[n];
		int max = 1;
		dp[0] = 1;
		for (int i = 1; i < powerList.size(); i++) {
			for (int j = 0; j < i; j++) {
				if (powerList.get(j) > powerList.get(i)) {
					dp[i] = Math.max(dp[i], dp[j] + 1);
					max = Math.max(dp[i], max);
					continue;
				}
				dp[i] = Math.max(dp[i], 1);
			}
		}
		System.out.println(powerList.size() - max);
	}
}
