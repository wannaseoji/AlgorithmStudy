import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int n = Integer.parseInt(br.readLine());
		boolean[][] arr = new boolean[101][101];
		// 0 오른, 1 위 2 왼 3 아래
		int[] dirX = { 1, 0, -1, 0 };
		int[] dirY = { 0, -1, 0, 1 };

		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			// x행
			int x = Integer.parseInt(st.nextToken());
			// y열
			int y = Integer.parseInt(st.nextToken());
			// 방향
			int d = Integer.parseInt(st.nextToken());
			// 세대
			int g = Integer.parseInt(st.nextToken());

			ArrayList<Point> list = new ArrayList<Point>();

			// 0세대 세팅
			arr[y][x] = true;
			list.add(new Point(y, x));

			int lastY = y + dirY[d];
			int lastX = x + dirX[d];
			arr[lastY][lastX] = true;
			list.add(new Point(lastY, lastX));

//			// 1세대
//			int tempY = lastY - y;
//			int tempX = lastX - x;
//			arr[lastY + (tempX * -1)][lastX + tempY] = true;
			// 2세대
			// ...
			// 세대 돌기
			for (int j = 1; j <= g; j++) {
				// 중간지점 지정
				lastY = list.get(list.size() - 1).y;
				lastX = list.get(list.size() - 1).x;
				int tempListSize = list.size();

				for (int j2 = tempListSize - 1; j2 >= 0; j2--) {
					// 이전 지점
					Point p = list.get(j2);
					// 이전 지점에서 기준 지점 뺀 차이 저장
					int tempY = lastY - p.y;
					int tempX = lastX - p.x;
					// 다음 지점
					arr[lastY + (tempX * -1)][lastX + tempY] = true;

					// 새로운 지점 리스트에 추가하기
					list.add(new Point(lastY + (tempX * -1), lastX + tempY));
				}
			}

		}
		int count = 0;
		// 네 꼭지점이 모두 드래곤 커브의 일부인 것
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				if (arr[i][j] && arr[i + 1][j] && arr[i][j + 1] && arr[i + 1][j + 1]) {
					count++;
				}
			}
		}
		System.out.println(count);
	}

	static class Point {
		int y;
		int x;

		public Point(int y, int x) {
			super();
			this.y = y;
			this.x = x;
		}
	}
}
