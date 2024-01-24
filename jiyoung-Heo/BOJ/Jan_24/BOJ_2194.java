import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int n, m, a, b;
	static int[] dirX = { 1, -1, 0, 0 };
	static int[] dirY = { 0, 0, 1, -1 };
	static int[] endPoint;
	// 장애물배열
	static int[][] arr;
	static int min;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		// 맵 크기
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		// 유닛 크기
		a = Integer.parseInt(st.nextToken());
		b = Integer.parseInt(st.nextToken());
		// 장애물이 설치된 개수
		int k = Integer.parseInt(st.nextToken());

		arr = new int[k][2];
		for (int i = 0; i < k; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			arr[i][0] = x;
			arr[i][1] = y;
		}
		st = new StringTokenizer(br.readLine());
		int[] startPoint = new int[] { Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()) };
		st = new StringTokenizer(br.readLine());
		endPoint = new int[] { Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()) };

		min = Integer.MAX_VALUE;

		bfs(startPoint[0], startPoint[1]);

		System.out.println(min == Integer.MAX_VALUE ? -1 : min);
	}

	public static void bfs(int x, int y) {
		Queue<Point> que = new LinkedList<Point>();
		que.add(new Point(x, y, 0));

		while (!que.isEmpty()) {
			Point point = que.poll();
			// 유닛블록끝지점
			if (point.x <= endPoint[0] && point.y >= endPoint[1]) {
				if (min > point.count) {
					min = point.count;
				}
				return;
			}

			for (int i = 0; i < dirX.length; i++) {
				int newPointX = point.x + dirX[i];
				int newPointY = point.y + dirY[i];
				int newPointendX = newPointX + a - 1;
				int newPointendY = newPointY + b - 1;

				if (newPointendX > n || newPointendY > m || newPointX < 1 || newPointY < 1) {
					continue;
				}

				int failCount = 0;

				for (int j = 0; j < arr.length; j++) {
					// 장애물좌표
					int tempx = arr[j][0];
					int tempy = arr[j][1];
					// 장애물에 걸리는 경우
					if (tempx >= newPointX && tempx <= newPointendX && tempy >= newPointY && tempy <= newPointendY) {
						failCount++;
						break;
					}
				}

				if (failCount == 0) {
					que.add(new Point(point.x + dirX[i], point.y + dirY[i], point.count + 1));
				}

			}
		}
	}

	static class Point {
		int x;
		int y;
		int count;

		public Point(int x, int y, int count) {
			this.x = x;
			this.y = y;
			this.count = count;
		}
	}
}
