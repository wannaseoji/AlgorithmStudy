import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int[] appleArr;
	static int k;
	static int sum;
	static ArrayList<Integer>[] list;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		list = new ArrayList[n];

		for (int i = 0; i < n; i++) {
			list[i] = new ArrayList<>();
		}

		for (int i = 0; i < n - 1; i++) {
			st = new StringTokenizer(br.readLine());
			// 부모
			int p = Integer.parseInt(st.nextToken());
			// 자식
			int c = Integer.parseInt(st.nextToken());
			list[p].add(c);
		}

		st = new StringTokenizer(br.readLine());
		appleArr = new int[n];
		for (int i = 0; i < n; i++) {
			appleArr[i] = Integer.parseInt(st.nextToken());
		}
		bfs(0);
		System.out.println(sum);
	}

	public static void bfs(int node) {
		Queue<Point> que = new LinkedList<>();

		if (appleArr[node] == 1)
			sum++;
		que.add(new Point(node, 0));

		while (!que.isEmpty()) {
			Point pollNode = que.poll();
			List<Integer> pollNodeList = list[pollNode.node];
			if (pollNode.depth >= k)
				return;
			for (int a : pollNodeList) {
				if (appleArr[a] == 1) {
					sum++;
				}
				que.add(new Point(a, pollNode.depth + 1));
			}
		}
	}

	static class Point {
		int node;
		int depth;

		public Point(int node, int depth) {
			this.node = node;
			this.depth = depth;
		}
	}
}
