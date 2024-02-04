package work;

import java.io.*;
import java.util.*;

public class BOJ1로만들기 {
	static int []dp;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int X = Integer.parseInt(br.readLine());
		int trace[] = new int[X +1];
		dp = new int[X+1];
//		dp[1] = 0;
//		dp[2] = 1; //2/2 or 2-1 =1
//		dp[3] = 1; //3/3 =1
//		dp[4] = 2; //(4-1)/3 or 4/2-1 
//		dp[5] = 3;   // (5-1)/2-1 or (5-1-1)/3
	   // 6/3-1  6/2/3

		dp[0] = dp[1] = 0;
        for(int i=2; i<=X ; i++){
            dp[i] = dp[i-1]+1;
            trace[i] = i-1;
            if(i%2==0 && dp[i]>dp[i/2]+1){
                dp[i] = dp[i/2]+1;
                trace[i] = i/2;
            }
            if(i%3==0 && dp[i]>dp[i/3]+1){
                dp[i] = dp[i/3]+1;
                trace[i] = i/3;
            }
        }
        System.out.println(dp[X ]);

        StringBuilder sb = new StringBuilder();
        while(X >0){
            sb.append(X + " ");
            X  = trace[X];
        }
        System.out.println(sb);
	}
//탑다운 디피가 더 쉬울 것 같은데 안익숙해서 못풀겠습니다. 혹시 하신분 있으신지,.. 
//	private static void makeOne(int x) {
//		if(x==1)return;
//		
//		if(x%3==0)
//			makeOne(x/3);
//		if(x%2==0) 
//			makeOne(x/2);
//		makeOne(x-1);
//		
//		
//	}
}
