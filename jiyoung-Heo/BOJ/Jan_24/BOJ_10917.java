import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static ArrayList<Integer>[] list;
	static int n;
	static boolean[] visited;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());

		list = new ArrayList[n + 1];
		for (int i = 1; i < n + 1; i++) {
			list[i] = new ArrayList<Integer>();
		}

		for (int i = 1; i < m + 1; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			list[x].add(y);
		}

		visited = new boolean[n + 1];
		int result = bfs(1);

		System.out.println(result);
	}

	public static int bfs(int node) {
		Queue<Point> que = new LinkedList<>();
		que.add(new Point(node, 0));

		while (!que.isEmpty()) {
			Point point = que.poll();

			for (int dreamNode : list[point.node]) {
				if (visited[dreamNode]) {
					continue;
				}
				if (dreamNode == n) {
					return point.depth + 1;
				}
				visited[dreamNode] = true;
				que.add(new Point(dreamNode, point.depth + 1));
			}
		}
		return -1;
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
