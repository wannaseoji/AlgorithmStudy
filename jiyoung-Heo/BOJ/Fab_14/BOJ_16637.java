import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static char[] arr;
    static int n;
    static int maxNumber = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        String calcString = br.readLine();

        arr = calcString.toCharArray();

        dfs(1, Character.getNumericValue(arr[0]));

        System.out.print(maxNumber);

    }

    private static void dfs(int index, int sum) {
        //마지막 연산까지 모두 끝낸 경우
        if (index >= n) {
            maxNumber = Math.max(maxNumber, sum);
        } else {
            int nextSum = 0;
            //calc 뽑아오기
            char calc = arr[index];
            //calc 뒤 숫자 뽑아오기
            int a = Character.getNumericValue(arr[index + 1]);

            //1. 괄호 넣는 경우연산
            if (index + 3 < n) {
                //괄호 내 calc 뽑아오기
                char innerCalc = arr[index + 2];
                //괄호 처리 할 숫자 받아오기
                int b = Character.getNumericValue(arr[index + 3]);
                //괄호연산 저장 변수
                int calcResult = calc(a, b, innerCalc);
                //괄호 연산결과와 이전 값의 calc
                nextSum = calc(sum, calcResult, calc);
                dfs(index + 4, nextSum);
            }

            //2. 괄호 안넣는 경우 연산
            nextSum = calc(sum, a, calc);
            dfs(index + 2, nextSum);
        }


    }

    private static int calc(int a, int b, char calc) {
        int calcResult = 0;
        if (calc == '+') {
            calcResult = a + b;
        } else if (calc == '-') {
            calcResult = a - b;
        } else if (calc == '*') {
            calcResult = a * b;
        }
        return calcResult;
    }
}
