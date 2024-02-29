import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int n;
	static int[][] arr;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());

		int k = Integer.parseInt(st.nextToken());
		arr = new int[n][n];

		st = new StringTokenizer(br.readLine());
		int count = 0;

		for (int i = 0; i < n; i++) {
			arr[0][i] = Integer.parseInt(st.nextToken());
		}

		while (true) {
			count++;
			int min = Integer.MAX_VALUE;

			for (int i = 0; i < n; i++) {
				min = Math.min(arr[0][i], min);
			}

			// 물고기의 수가 최소인 어항 모두에 한 마리씩 넣는다
			for (int i = 0; i < n; i++) {
				if (arr[0][i] == min) {
					arr[0][i]++;
				}
			}

			int height = 1;
			int width = 0;
			int bottomLength = arr[0].length;

			// 공중 부양 작업
			while (true) {
				if (bottomLength - width < height) {
					break;
				}
				// 처음 세팅 2*2 만들고 시작
				if (width == 0) {
					arr[1][1] = arr[0][0];
					arr[1][0] = arr[0][1];
					for (int i = 0; i < arr.length - 2; i++) {
						arr[0][i] = arr[0][i + 2];
					}
					arr[0][arr[0].length - 1] = 0;
					arr[0][arr[0].length - 2] = 0;

					height = 2;
					width = 2;
					bottomLength -= width;
					continue;
				}

				int[][] temp = new int[n][n];
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						temp[i][j] = arr[i][j];
					}
				}

				for (int i = 0; i < width; i++) {
					for (int j = 0; j < height; j++) {
						arr[i + 1][j] = temp[j][width - 1 - i];
					}
				}

				for (int i = width; i < arr[0].length; i++) {
					arr[0][i - width] = arr[0][i];
				}
				bottomLength -= width;

				if (width == height) {
					height++;
				} else {
					width++;
				}
			}

			// 공중 부양 작업이 모두 끝나면, 어항에 있는 물고기의 수를 조절
			arr = moveFishes(arr);

			// 다시 어항을 바닥에 일렬로 놓아야 한다.
			// 바닥에 있는 것 들 먼저 뒤로 밀기
			int[][] newArr = new int[n][n];
			int index = 0;
			for (int i = 0; i < arr[0].length; i++) {
				for (int j = 0; j < arr.length; j++) {
					if (arr[j][i] == 0) {
						continue;
					}
					newArr[0][index] = arr[j][i];
					index++;
				}
			}
			// 한번 쌓기
			for (int i = n / 2 - 1; i >= 0; i--) {
				newArr[1][i] = newArr[0][n / 2 - 1 - i];
				newArr[0][n / 2 - 1 - i] = newArr[0][n / 2 - 1 + n / 2 - i];
				newArr[0][n / 2 - 1 + n / 2 - i] = 0;
			}
			// 두번 쌓기
			for (int i = n / 4 - 1; i >= 0; i--) {
				newArr[3][i] = newArr[0][n / 4 - 1 - i];
				newArr[2][i] = newArr[1][n / 4 - 1 - i];
				newArr[0][n / 4 - 1 - i] = newArr[0][n / 4 - 1 + n / 4 - i];
				newArr[1][n / 4 - 1 - i] = newArr[1][n / 4 - 1 + n / 4 - i];
				newArr[0][n / 4 - 1 + n / 4 - i] = 0;
				newArr[1][n / 4 - 1 + n / 4 - i] = 0;
			}

			// 공중 부양 작업이 모두 끝나면, 어항에 있는 물고기의 수를 조절
			newArr = moveFishes(newArr);

			// 다시 어항을 바닥에 일렬로 놓아야 한다.
			// 바닥에 있는 것 들 먼저 뒤로 밀기
			arr = new int[n][n];
			index = 0;
			int minResult = Integer.MAX_VALUE;
			int maxResult = Integer.MIN_VALUE;

			for (int i = 0; i < arr[0].length; i++) {
				for (int j = 0; j < arr.length; j++) {
					if (newArr[j][i] == 0) {
						continue;
					}
					arr[0][index] = newArr[j][i];
					minResult = Math.min(arr[0][index], minResult);
					maxResult = Math.max(arr[0][index], maxResult);
					index++;
				}
			}

			if (maxResult - minResult <= k) {
				System.out.println(count);
				System.exit(0);
			}
		}
	}

	/**
	 * 공중 부양 작업이 모두 끝나면, 어항에 있는 물고기의 수를 조절
	 * 
	 * @param arr
	 * @return
	 */
	private static int[][] moveFishes(int[][] arr) {
		int[][] sumArr = new int[n][n];
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = 0; j < arr[0].length - 1; j++) {
				if (arr[i][j] != 0) {
					int my = arr[i][j];
					// 아래
					int down = arr[i + 1][j];
					int right = arr[i][j + 1];
					// 아래와 오른쪽 탐색해서 이 차이를
					if (down != 0) {
						// 5로 나눈 몫을 d
						int d = Math.abs(my - down) / 5;
						// 두 어항 중 물고기의 수가 많은 곳에 있는 물고기 d 마리를 적은 곳에 있는 곳으로 보낸다
						if (my > down) {
							sumArr[i][j] -= d;
							sumArr[i + 1][j] += d;
						} else {
							sumArr[i][j] += d;
							sumArr[i + 1][j] -= d;
						}
					}
					if (right != 0) {
						// 5로 나눈 몫을 d
						int d = Math.abs(my - right) / 5;
						// 두 어항 중 물고기의 수가 많은 곳에 있는 물고기 d 마리를 적은 곳에 있는 곳으로 보낸다
						if (my > right) {
							sumArr[i][j] -= d;
							sumArr[i][j + 1] += d;
						} else {
							sumArr[i][j] += d;
							sumArr[i][j + 1] -= d;
						}
					}
				}
			}
		}
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				arr[i][j] += sumArr[i][j];
			}
		}
		return arr;
	}
}
