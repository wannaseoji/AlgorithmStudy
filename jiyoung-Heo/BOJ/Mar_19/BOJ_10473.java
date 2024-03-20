import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	static double startX, startY, endX, endY;
	static double result;
	static int n;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		startX = Double.parseDouble(st.nextToken());
		startY = Double.parseDouble(st.nextToken());

		st = new StringTokenizer(br.readLine());
		endX = Double.parseDouble(st.nextToken());
		endY = Double.parseDouble(st.nextToken());

		n = Integer.parseInt(br.readLine());

		ArrayList<Node>[] arr = new ArrayList[n + 2];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = new ArrayList<>();
		}

		Node[] dae = new Node[n + 2];
		dae[0] = new Node(0, startX, startY);
		dae[n + 1] = new Node(n + 1, endX, endY);

		for (int i = 1; i < n + 1; i++) {
			st = new StringTokenizer(br.readLine());
			double x = Double.parseDouble(st.nextToken());
			double y = Double.parseDouble(st.nextToken());

			dae[i] = new Node(i, x, y);
		}

		// 모든 경우의 수 넣기
		for (int i = 0; i < n + 2; i++) {
			for (int j = 0; j < n + 2; j++) {
				if (i == j)
					continue;
				double time = calcTime(dae[i], dae[j]);
				dae[j].sec = time;
				arr[i].add(new Node(dae[j].index, dae[j].x, dae[j].y, dae[j].sec));
			}
		}
		// 최단거리 배열 초기화
		double[] sec = new double[n + 2];
		Arrays.fill(sec, 50000);

		boolean[] visited = new boolean[n + 2];

		sec[0] = 0;
		for (Node node : arr[0]) {
			sec[node.index] = Math.min(sec[node.index], sec[0] + node.sec);
		}

		visited[0] = true;
		for (int i = 0; i < sec.length - 1; i++) {
			double min = 50000;
			int index = 0;
			// 값이 가장 작은 노드 구하기
			for (int j = 0; j < visited.length; j++) {
				if (!visited[j]) {
					min = sec[j] < min ? sec[j] : min;
					if (min == sec[j]) {
						index = j;
					}
				}
			}
			// 방문체크
			visited[index] = true;
			ArrayList<Node> temp = arr[index];
			for (Node node : temp) {
				sec[node.index] = Math.min(sec[node.index], sec[index] + node.sec);
			}
		}
		System.out.println(sec[n + 1]);
	}

	static double calcTime(Node one, Node two) {
		double distance = calcDistance(one, two);
		double time = 0.0;
		if (one.index != 0) {
			// 대포인경우 50m 발사 = 2초
			time += Math.min(Math.abs(distance - 50) / 5.0 + 2, distance / 5.0);
		} else {
			time = distance / 5.0;
		}
		return time;
	}

	static double calcDistance(Node one, Node two) {
		return Math.sqrt(Math.pow(Math.abs(one.x - two.x), 2) + Math.pow(Math.abs(one.y - two.y), 2));
	}

	static class Node {
		int index;
		double x;
		double y;
		double sec;

		public Node(int index, double x, double y) {
			super();
			this.index = index;
			this.x = x;
			this.y = y;
		}

		public Node(int index, double x, double y, double sec) {
			super();
			this.index = index;
			this.x = x;
			this.y = y;
			this.sec = sec;
		}

	}

}
