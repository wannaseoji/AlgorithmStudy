import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int n, m, a, b;
	static int[] dirX = { 1, -1, 0, 0 };
	static int[] dirY = { 0, 0, 1, -1 };
	static int[] endPoint;
	// arr = 장애물배열
	static boolean[][] arr, visited;
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

		arr = new boolean[n + 1][m + 1];
		visited = new boolean[n + 1][m + 1];
		for (int i = 0; i < k; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			arr[x][y] = true;
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
		Queue<Point> que = new ArrayDeque<Point>();
		que.add(new Point(x, y, 0));
		visited[x][y] = true;

		while (!que.isEmpty()) {
			Point point = que.poll();
			// 유닛블록끝지점
			if (point.x == endPoint[0] && point.y == endPoint[1]) {
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

				if (newPointendX > n || newPointendY > m || newPointX < 1 || newPointY < 1 || visited[newPointX][newPointY]) {
					continue;
				}

				boolean movable = true;
				// 장애물 좌표에서 돌지 말고 유닛을 돌기
				for (int j = newPointX; j <= newPointendX; j++) {
					for (int j2 = newPointY; j2 <= newPointendY; j2++) {
						if (arr[j][j2]) {
							// 장애물에 걸리는 경우
							movable = false;
							break;
						}
					}
				}

				if (movable) {
					que.add(new Point(newPointX, newPointY, point.count + 1));
					visited[newPointX][newPointY] = true;
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
