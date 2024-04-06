import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class 병사배치하기 {
	public static int n;
	public static List<Integer> list = new ArrayList<>();
	public static int[] dp = new int[2001];
	
	
	public static void main(String[] args) {
		// 가장 길게 증가하는 수열의 길이를 찾아 총 병사수에서 빼기
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		
		for(int i=0; i<n; i++) {
			list.add(sc.nextInt());
			dp[i] = 1;
		}
		Collections.reverse(list); //list 뒤집기
		
		int result = dp[0]; 
		
		for(int i=1; i<n; i++) {
			for(int j=0; j<i; j++) { //0부터 i-1 까지 돌면서 최댓값을 갱신
				if(list.get(j)<list.get(i)) {
					dp[i] = Math.max(dp[i], dp[j]+1);
					
				}
				System.out.print("dp[i] :"+dp[i]+" ");
			}
			result = Math.max(result, dp[i]);
		}
		System.out.println(n-result);
	}

}
