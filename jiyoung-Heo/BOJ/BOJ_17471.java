import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int minNum = Integer.MAX_VALUE;
	static int n;
	static Node[] arr;
	// 전체 인구수
	static int allAmount = 0;
	static int sumA = 0;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		n = Integer.parseInt(br.readLine());

		arr = new Node[n];

		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++) {
			arr[i] = new Node();
			arr[i].index = i;
			arr[i].amount = Integer.parseInt(st.nextToken());
			allAmount += arr[i].amount;
		}

		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			int amount = Integer.parseInt(st.nextToken());
			for (int j = 0; j < amount; j++) {
				if (arr[i].up == null) {
					arr[i].up = arr[Integer.parseInt(st.nextToken()) - 1];
				} else if (arr[i].down == null) {
					arr[i].down = arr[Integer.parseInt(st.nextToken()) - 1];
				} else if (arr[i].right == null) {
					arr[i].right = arr[Integer.parseInt(st.nextToken()) - 1];
				} else if (arr[i].left == null) {
					arr[i].left = arr[Integer.parseInt(st.nextToken()) - 1];
				}
			}
		}

		for (int i = 0; i < n; i++) {
			for (int j = 1; j < n - 1; j++) {
				for (int k = 0; k < n; k++) {
					arr[k].visited = false;
				}
				sumA = 0;
				arr[i].visited = true;
				backT(i, 1, j);
				arr[i].visited = false;
			}
		}
        if(n == 2) {
			System.out.println(Math.abs(arr[0].amount-arr[1].amount));
			System.exit(0);
		}
		if(minNum == Integer.MAX_VALUE) {
			System.out.println(-1);
			System.exit(0);
		}
		System.out.println(minNum);
	}

	// 현재 index,현재 뽑은 a선거구 개수, a선거구의 개수
	private static void backT(int currNum, int currCount, int count) {

		// 선거구 1의 합 구하기
		sumA += arr[currNum].amount;
		if (currCount == count) {
			int tempIndex = 0;
			for (int i = 0; i < n; i++) {
				if (!arr[i].visited) {
					tempIndex = i;
					break;
				}
			}

			// 이 상태이면 계산하면 된다.
			// 1. 노드 연결되어있는지 확인
			if (!isConnected(tempIndex, 0, n - count)) {
				return;
			}
			// 2. 연결되어 있다면 a선거구와 b선거구 인구 차이 계산하기
			int sumB = allAmount - sumA;
			minNum = Math.min(Math.abs(sumA - sumB), minNum);
			return;
		}

		if (arr[currNum].up != null && !arr[currNum].up.visited) {
			arr[currNum].up.visited = true;
			backT(arr[currNum].up.index, currCount + 1, count);
			arr[currNum].up.visited = false;
		}
		if (arr[currNum].down != null&& !arr[currNum].down.visited) {
			arr[currNum].down.visited = true;
			backT(arr[currNum].down.index, currCount + 1, count);
			arr[currNum].down.visited = false;
		}
		if (arr[currNum].right != null&& !arr[currNum].right.visited) {
			arr[currNum].right.visited = true;
			backT(arr[currNum].right.index, currCount + 1, count);
			arr[currNum].right.visited = false;
		}
		if (arr[currNum].left != null&& !arr[currNum].left.visited) {
			arr[currNum].left.visited = true;
			backT(arr[currNum].left.index, currCount + 1, count);
			arr[currNum].left.visited = false;
		}
	}

	private static boolean isConnected(int index, int count, int countB) {
		if (arr[index].up != null && !arr[index].up.visited) {
			arr[index].up.visited = true;
			boolean flag = isConnected(arr[index].up.index, count + 1, countB);
			if(flag) {
				return flag;
			}
			count += 1;
		}
		if (arr[index].down != null && !arr[index].down.visited) {
			arr[index].down.visited = true;
			boolean flag = isConnected(arr[index].down.index, count + 1, countB);
			if(flag) {
				return flag;
			}
			count += 1;
		}
		if (arr[index].right != null && !arr[index].right.visited) {
			arr[index].right.visited = true;
			boolean flag = isConnected(arr[index].right.index, count + 1, countB);
			if(flag) {
				return flag;
			}
			count += 1;
		}
		if (arr[index].left != null && !arr[index].left.visited) {
			arr[index].left.visited = true;
			boolean flag = isConnected(arr[index].left.index, count + 1, countB);
			if(flag) {
				return flag;
			}
			count += 1;
		}
		if (count != countB) {
			return false;
		} else {
			return true;
		}
	}

	static class Node {
		boolean visited;
		int index;
		int amount;
		Node right;
		Node left;
		Node up;
		Node down;

		public Node() {
			super();
		}

		public Node(boolean visited, int index, int amount, Node right, Node left, Node up, Node down) {
			super();
			this.visited = visited;
			this.index = index;
			this.amount = amount;
			this.right = right;
			this.left = left;
			this.up = up;
			this.down = down;
		}

	}
}
