
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class 괄호추가하기 {
	static int max = Integer.MIN_VALUE;
	static int N ;
	static char[] expression;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N= Integer.parseInt(br.readLine());
		expression = br.readLine().toCharArray();
		recursive(2, expression[0]-'0');
		System.out.println(max);
	}
	private static void recursive(int idx, int res) throws Exception {
		//basis
		if(idx>=N) {
			max = Math.max(max, res);
			return;
		}
		
		//inductive
		 if (idx + 2 < N) {
		      int next = calc(expression[idx] - '0', expression[idx + 2] - '0', expression[idx + 1]);
		      recursive(idx + 4, calc(res, next, expression[idx - 1]));
		    }
		    // 괄호를 사용하지 않는 경우
		 	recursive(idx + 2, calc(res, expression[idx] - '0', expression[idx - 1]));
	}
	private static int calc(int a, int b, char operation) throws Exception {
		if(operation == '+')return a+b;
		if(operation == '-')return a-b;
		if(operation == '*')return a*b;
		throw new Exception();
		
	}
}
