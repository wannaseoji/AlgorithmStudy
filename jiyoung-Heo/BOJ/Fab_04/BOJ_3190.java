package test;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int n = Integer.parseInt(br.readLine());
		int k = Integer.parseInt(br.readLine());

		int[][] appleArr = new int[n + 1][n + 1];

		for (int i = 0; i < k; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			appleArr[x][y] = 1;
		}

		int l = Integer.parseInt(br.readLine());
		int[] secArr = new int[l];
		String[] dirArr = new String[l];

		for (int i = 0; i < l; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			String c = st.nextToken();
			secArr[i] = x;
			dirArr[i] = c;
		}
		int x = 1;
		int y = 1;
		int nextX = x;
		int nextY = y;

		Queue<Point> que = new ArrayDeque<Point>();

		// 오른쪽 아래 왼쪽 위
		int[] dirX = new int[] { 0, 1, 0, -1 };
		int[] dirY = new int[] { 1, 0, -1, 0 };
		int nowDir = 0;

		appleArr[x][y] = 2;
		que.add(new Point(1, 1));

		int count = 0;
		for (int i = 0; i < secArr.length; i++) {
			int sec = secArr[i];
			String dir = dirArr[i];

			for (int j = 0; j < sec; j++) {
				count++;
				nextX += dirX[nowDir];
				nextY += dirY[nowDir];

				if (nextX > n || nextX <= 0 || nextY > n || nextY <= 0) {
					System.out.println(count);
					System.exit(0);
				}

				if (appleArr[nextX][nextY] == 0) {
					Point p = que.poll();
					appleArr[p.x][p.y] = 0;
				} else if (appleArr[nextX][nextY] == 2) {
					System.out.println(count);
					System.exit(0);
				}
				que.add(new Point(nextX, nextY));

				for (int j2 = 0; j2 < que.size(); j2++) {
					Point point = que.poll();
					appleArr[point.x][point.y] = 2;
					que.add(point);
				}

				x = nextX;
				y = nextY;
			}

			// 오른쪽
			if (dir.equals("D")) {
				nowDir++;
				if (nowDir == dirX.length) {
					nowDir = 0;
				}
			} else if (dir.equals("L")) {
				nowDir--;
				if (nowDir < 0) {
					nowDir = dirX.length - 1;
				}
			}
		}
	}

	class point {
		int x;
		int y;

		public point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
}
