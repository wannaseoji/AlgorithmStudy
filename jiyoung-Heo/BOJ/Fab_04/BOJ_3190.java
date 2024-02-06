import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int n = Integer.parseInt(br.readLine());
		int k = Integer.parseInt(br.readLine());

		int[][] groundArr = new int[n + 1][n + 1];

		for (int i = 0; i < k; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			groundArr[x][y] = 1;
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

		//1 사과 2 뱀
		groundArr[x][y] = 2;
		que.add(new Point(1, 1));
		int allSec = 0;
		int count = 0;
		for (int i = 0; i < secArr.length; i++) {
			//해당 방향으로 움직일 초
			int sec = secArr[i] - allSec;

			for (int j = 0; j < sec; j++) {
				count++;
				// 다음 좌표 미리 지정하기
				nextX += dirX[nowDir];
				nextY += dirY[nowDir];
				//벽에 닿는 경우 끝내기.
				if (nextX > n || nextX <= 0 || nextY > n || nextY <= 0) {
					System.out.println(count);
					System.exit(0);
				}
				// 다음 좌표에 사과가 없을 경우
				if (groundArr[nextX][nextY] == 0) {
					//que 값 받아오고 뱀 꼬리 자르기
					Point p = que.poll();
					groundArr[p.x][p.y] = 0;
				} else if (groundArr[nextX][nextY] == 2) {
					//뱀이랑 닿는 경우
					System.out.println(count);
					System.exit(0);
				}
				//뱀이 움직일 좌표 que에 반영하기
				que.add(new Point(nextX, nextY));
				
				// 큐 값을 배열에 넣어주기
				for (int j2 = 0; j2 < que.size(); j2++) {
					Point point = que.poll();
					groundArr[point.x][point.y] = 2;
					que.add(point);
				}
				//다음 좌표를 현재 좌표로 지정
				x = nextX;
				y = nextY;
				//전체 시간 더해주기
				allSec++;
			}
			//방향 저장 변수 불러오기
			String dir = dirArr[i];
			//오른쪽 회전
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

		// 회전이 모두 끝났을 때 벽이나 몸에 붙을때까지 전진하기
		while (nextX <= n && nextX > 0 && nextY <= n && nextY > 0) {
			// 이동한 수 증가
			count++;
			// 다음좌표 설정
			nextX += dirX[nowDir];
			nextY += dirY[nowDir];
			// que에 뱀의 새로운 이동 위치 넣기
			que.add(new Point(nextX, nextY));
			// 아무것도 없는 경우 마지막 꼬리 자르기
			if (groundArr[nextX][nextY] == 0) {
				Point p = que.poll();
				groundArr[p.x][p.y] = 0;
			} else if (groundArr[nextX][nextY] == 2) {
				//뱀이랑 닿는경우 끝내기
				System.out.println(count);
				System.exit(0);
			}
			// 큐 값을 배열에 반영하기
			for (int j2 = 0; j2 < que.size(); j2++) {
				Point point = que.poll();
				groundArr[point.x][point.y] = 2;
				que.add(point);
			}

		}
		System.out.println(count);
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
