import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int n = Integer.parseInt(br.readLine());
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			list.add(Integer.parseInt(br.readLine()));
		}

		int[] dp = new int[n];
		dp[0] = 1;

		for (int i = 0; i < list.size(); i++) {
			dp[i] = 1;
			for (int j = 0; j < i; j++) {
				if (list.get(j) < list.get(i)) {
					dp[i] = Math.max(dp[i], dp[j] + 1);
				}
			}
		}

		Arrays.sort(dp);
		System.out.println(n - dp[n - 1]);
	}
}
