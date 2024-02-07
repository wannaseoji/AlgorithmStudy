import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int n = Integer.parseInt(br.readLine());

		int[][] arr = new int[n][n];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		int[] dirX = { 0, 1, 0, -1 };
		int[] dirY = { -1, 0, 1, 0 };

		int[] sendGoX = { -2, -1, 2, 1, 1, -1, 1, -1, 0, 0 };
		int[] sendGoY = { 0, 0, 0, 0, -1, -1, 1, 1, -2, -1 };
		int[] percent = { 2, 7, 2, 7, 10, 10, 1, 1, 5 };

		int to = -1;
		int result = 0;

		// 시작점
		int nowX = n / 2;
		int nowY = n / 2;

		int moveSize = 0;
		while (nowX != 0 || nowY != 0) {
			for (int i = 0; i < dirX.length; i++) {
				to++;
				if (to == 4) {
					to = 0;
				}
				if (i % 2 == 0) {
					moveSize++;
				}
				int nextX = 0;
				int nextY = 0;

				for (int j = 0; j < moveSize; j++) {
					if (nowX == 0 && nowY == 0)
						break;
					nextX = nowX + dirX[i];
					nextY = nowY + dirY[i];
					// to에따른 배열 차이
					int[] nowGoX = new int[10];
					int[] nowGoY = new int[10];
					// 방향성 체크 0: 왼쪽 1: 아래 2: 오른쪽 3: 위
					// 0=>그대로
					// 1=> sendGoX = sendGoY * -1, sendGoY = sendGoX
					// 2=> sendGoY = sendGoY * -1
					// 3=> sendgoX = sendGoY, sendGoY = SendGoX * -1
					if (to == 0) {
						nowGoX = sendGoX;
						nowGoY = sendGoY;
					} else if (to == 1) {
						for (int k = 0; k < 10; k++) {
							nowGoX[k] = sendGoY[k] * -1;
						}
						nowGoY = sendGoX;
					} else if (to == 2) {
						nowGoX = sendGoX;
						for (int k = 0; k < 10; k++) {
							nowGoY[k] = sendGoY[k] * -1;
						}
					} else if (to == 3) {
						nowGoX = sendGoY;
						for (int k = 0; k < 10; k++) {
							nowGoY[k] = sendGoX[k] * -1;
						}
					}

					int sand = arr[nextX][nextY];
					
					for (int k = 0; k < percent.length; k++) {
						int nowPercent = percent[k];
						double sendSand = Math.floor(sand / 100F * nowPercent);

						if (nextX + nowGoX[k] < 0 || nextX + nowGoX[k] >= n || nextY + nowGoY[k] < 0
								|| nextY + nowGoY[k] >= n) {
							// 결과에 추가
							result += sendSand;
							arr[nextX][nextY] -= sendSand;
							continue;
						}
						arr[nextX + nowGoX[k]][nextY + nowGoY[k]] += sendSand;
						arr[nextX][nextY] -= sendSand;
					}
					
					// 나머지값 옆 칸에 넣기
					if (nextX + nowGoX[9] < 0 || nextX + nowGoX[9] >= n || nextY + nowGoY[9] < 0
							|| nextY + nowGoY[9] >= n) {
						result += arr[nextX][nextY];
					} else {
						arr[nextX + nowGoX[9]][nextY + nowGoY[9]] += arr[nextX][nextY];
					}
					arr[nextX][nextY] = 0;

					nowX = nextX;
					nowY = nextY;
				}
			}
		}
		System.out.println(result);
	}
}
