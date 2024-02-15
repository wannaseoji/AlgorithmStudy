import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Main {
	private static boolean[] visit;
	private static int max = Integer.MIN_VALUE;
	private static String[] arr;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		arr = br.readLine().split("");
		visit = new boolean[N / 2];

		backtracking(0, 0);

		System.out.println(max);

	}

	private static void backtracking(int idx, int depth) {

		if (depth == visit.length) {
			calc();
			return;
		}

		for (int i = idx; i < visit.length; i++) {
			visit[i] = true;
			backtracking(i + 2, depth + 2);
			visit[i] = false;
			backtracking(i + 1, depth + 1);
		}

	}

	private static void calc() {

		String[] newArr = Arrays.copyOf(arr, arr.length);
		Deque<String> deque = new ArrayDeque<>();

		for (int i = 0; i < newArr.length - 1; i += 2) {

			int number = Integer.parseInt(newArr[i]);
			String str = newArr[i + 1];

			if (i < newArr.length - 2 && visit[i / 2]) {

				switch (str) {
				case "+":
					newArr[i + 2] = Integer.parseInt(newArr[i]) + Integer.parseInt(newArr[i + 2]) + "";
					break;

				case "-":
					newArr[i + 2] = Integer.parseInt(newArr[i]) - Integer.parseInt(newArr[i + 2]) + "";
					break;

				case "*":
					newArr[i + 2] = Integer.parseInt(newArr[i]) * Integer.parseInt(newArr[i + 2]) + "";
					break;
				}

				continue;
			}

			deque.add(newArr[i]);
			deque.add(newArr[i + 1]);

		}
		
		deque.add(newArr[newArr.length - 1]);
		
		while (deque.size() > 2) {
		
			int prev = Integer.parseInt(deque.poll());
			String str = deque.poll();
			int post = Integer.parseInt(deque.poll());
			
			switch (str) {
			case "+":
				deque.offerFirst(prev + post + "");
				break;
			case "-":
				deque.offerFirst(prev - post + "");
				break;
			case "*":
				deque.offerFirst(prev * post + "");
				break;
			}
			
		}

		max = Math.max(max, Integer.parseInt(deque.poll()));
	}
}
