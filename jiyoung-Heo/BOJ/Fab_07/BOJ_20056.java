import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		// 격자크기
		int n = Integer.parseInt(st.nextToken());
		// 파이어볼개수
		int M = Integer.parseInt(st.nextToken());
		// 이동횟수
		int k = Integer.parseInt(st.nextToken());

		ArrayList<point>[][] arr = new ArrayList[n + 1][n + 1];
		for (int i = 0; i < n + 1; i++) {
			for (int j = 0; j < n + 1; j++) {
				arr[i][j] = new ArrayList<>();
			}
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			// 행
			int x = Integer.parseInt(st.nextToken());
			// 열
			int y = Integer.parseInt(st.nextToken());
			// 질량
			int m = Integer.parseInt(st.nextToken());
			// 속력
			int s = Integer.parseInt(st.nextToken());
			// 방향
			int d = Integer.parseInt(st.nextToken());

			arr[x][y].add(new point(x, y, m, s, d, false));
		}

		// 이동할 좌표값 세팅
		int[] dirX = { -1, -1, 0, 1, 1, 1, 0, -1 };
		int[] dirY = { 0, 1, 1, 1, 0, -1, -1, -1 };

		// 체크여부 확인할 변수 시작은
		boolean isChecked = true;

		for (int kk = 0; kk < k; kk++) {
			// 좌표 이동하기
			for (int i = 1; i < n + 1; i++) {
				for (int j = 1; j < n + 1; j++) {
					// 해당 좌표에 파이어볼이 있는경우
					if (!arr[i][j].isEmpty()) {
						int count = arr[i][j].size();
						while (count > 0) {
							// 저장되어있는 포인트 불러오기
							point getPoint = arr[i][j].get(0);
							if (getPoint.isMoved != isChecked) {
								// 질량
								int inM = getPoint.m;
								// 속력
								int inS = getPoint.s;
								// 방향
								int inD = getPoint.d;
								// 이동할 좌표 세팅하기
								int moveX = i + dirX[inD] * inS;
								int moveY = j + dirY[inD] * inS;
								// 좌표 넘어갈경우 회귀
								// x
								while (moveX <= 0) {
									moveX = n + moveX;
								}
								while (moveX >= n + 1) {
									moveX = moveX - n;
								}
								// y
								while (moveY <= 0) {
									moveY = n + moveY;
								}
								while (moveY >= n + 1) {
									moveY = moveY - n;
								}
								arr[i][j].remove(0);

								// 움직인 좌표에 포인트 새로 세팅해주기.
								arr[moveX][moveY].add(new point(moveX, moveY, inM, inS, inD, isChecked));
							}
							count--;
						}
					}
				}
			}

			// 이동 후 합치거나 다음연산준비하는 로직
			for (int i = 1; i < n + 1; i++) {
				for (int j = 1; j < n + 1; j++) {
					Set<Integer> checkD = new HashSet<>();
					// 2개 이상겹쳐있는 부분 찾아서 하나로 합치기
					if (arr[i][j].size() >= 2) {
						// 질량과 속력 연산하기
						int sumM = 0;
						int sumS = 0;

						// 방향 결정하기: 다 짝수거나 다 홀수면 짝수. 그렇지않다면 홀수로 갈라진다.
						// 짝수면 0, 홀수면 1 넣기
						// checkD 사이즈가 2이면 홀수 1이면 짝수 갈라지기

						for (int j2 = 0; j2 < arr[i][j].size(); j2++) {
							point point = arr[i][j].get(j2);
							sumM += point.m;
							sumS += point.s;

							// 홀짝 결정
							if (point.d == 0 || point.d == 2 || point.d == 4 || point.d == 6) {
								checkD.add(0);
							} else {
								checkD.add(1);
							}

						}

						int changeM = sumM / 5;
						int changeS = sumS / arr[i][j].size();

						arr[i][j].clear();

						if (changeM == 0) {
							continue;
						}

						if (checkD.size() == 1) {
							// 방향 0,2,4,6 에 값 넣어주기
							arr[i][j].add(new point(i, j, changeM, changeS, 0, !isChecked));
							arr[i][j].add(new point(i, j, changeM, changeS, 2, !isChecked));
							arr[i][j].add(new point(i, j, changeM, changeS, 4, !isChecked));
							arr[i][j].add(new point(i, j, changeM, changeS, 6, !isChecked));
						} else if (checkD.size() == 2) {
							// 방향 1,3,5,7 에 값 넣어주기
							arr[i][j].add(new point(i, j, changeM, changeS, 1, !isChecked));
							arr[i][j].add(new point(i, j, changeM, changeS, 3, !isChecked));
							arr[i][j].add(new point(i, j, changeM, changeS, 5, !isChecked));
							arr[i][j].add(new point(i, j, changeM, changeS, 7, !isChecked));
						}
						// arr[i][j] 가 1인경우 다음에 또 이동해야하니까 ismoved false 처리해주기
					} else if (arr[i][j].size() == 1) {
						arr[i][j].get(0).setMoved(!arr[i][j].get(0).isMoved);
					}
				}
			}
		}

		int result = 0;
		for (int i = 1; i < n + 1; i++) {
			for (int j = 1; j < n + 1; j++) {
				if (!arr[i][j].isEmpty()) {
					for (int j2 = 0; j2 < arr[i][j].size(); j2++) {
						result += arr[i][j].get(j2).m;
					}
				}
			}
		}
		System.out.println(result);

	}

	static class point {
		int x;
		int y;
		int m;
		int s;
		int d;
		boolean isMoved;

		public point(int x, int y, int m, int s, int d, boolean isMoved) {
			super();
			this.x = x;
			this.y = y;
			this.m = m;
			this.s = s;
			this.d = d;
			this.isMoved = isMoved;
		}

		public boolean isMoved() {
			return isMoved;
		}

		public void setMoved(boolean isMoved) {
			this.isMoved = isMoved;
		}

	}
}
