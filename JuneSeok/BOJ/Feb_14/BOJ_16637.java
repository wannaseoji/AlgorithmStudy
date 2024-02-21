import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    static List<Integer> nums = new ArrayList<>();
    static List<Character> ops = new ArrayList<>();
    static int N;
    static int answer = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        String exp = br.readLine();
        for (int i = 0; i < N; i++) {
            if (i % 2 == 0) nums.add(exp.charAt(i) - '0');
            else ops.add(exp.charAt(i));
        }

        dfs(0, ops.size(), nums.get(0));

        System.out.println(answer);
    }

    public static void dfs(int depth, int maxDepth, int result) {

        if (depth == maxDepth) {
            answer = Math.max(answer, result);
            return;
        }

        //왼쪽 먼저 계산 (3 + 8) * 7 ~~~
        int leftResult = calc(ops.get(depth), result, nums.get(depth + 1));
        dfs(depth + 1, maxDepth, leftResult);


        //오른쪽 계산 후 왼쪽과 계산 3 + (8 * 7) ~~~
        if (depth + 2 <= maxDepth) {
            int rightResult = calc(ops.get(depth + 1), nums.get(depth + 1), nums.get(depth + 2));
            dfs(depth + 2, maxDepth, calc(ops.get(depth), result, rightResult));
        }

    }

    public static int calc(char op, int a, int b) {
        if (op == '+') return a + b;
        else if (op == '*') return a * b;
        else if (op == '-') return a - b;
        return -1;
    }
}