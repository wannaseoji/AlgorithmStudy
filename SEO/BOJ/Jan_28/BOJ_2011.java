import java.util.*;
import java.io.*;

// 2        														1
// 2,5 | 25															2
// 2,5,1 | 25,1 													2
// 2,5,1,1 | 25,1,1 | 2,5,11 | 25,11								4
// 2,5,1,1,4 |25,1,1,4 | 2,5,11,4 | 25,11,4 | 2,5,1,14 | 25, 1, 14  6
//dp[n] = dp[n-1]+dp[n-2] // 일반적인 상황


public class 암호코드 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		String input = st.nextToken();
		
		long[] dp = new long[input.length()+1];
		if(input.charAt(0) == '0') {
			System.out.println('0');
			return ;
		}
		
		
		dp[0] = 1;
		dp[1] = 1;
		int mod = 1000000;
		for(int i=2;i<=input.length(); i++) { //0앞에 1이나 2가 온 경우, 10,20만 가능
			if(input.charAt(i-1) == '0') {
				if(input.charAt(i-2) == '1' || input.charAt(i-2)=='2') 
					dp[i] = dp[i-2]%mod;
			}
			else {
				int res = Integer.parseInt(input.substring(i-2,i));
				if(res <27 && res>9) {
					dp[i] = (dp[i-1]+dp[i-2])%mod;
				}
				else {
					dp[i] = dp[i-1]%mod;
				}
			}
		}
		
		
	}
}
