import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static char[][] arr = new char[4][8];
	static int num;
	static int dir;
	static boolean[] checked;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		for (int i = 0; i < 4; i++) {
			arr[i] = br.readLine().toCharArray();
		}

		int k = Integer.parseInt(br.readLine());
		for (int i = 0; i < k; i++) {
			checked = new boolean[4];

			st = new StringTokenizer(br.readLine());
			num = Integer.parseInt(st.nextToken()) - 1;
			dir = Integer.parseInt(st.nextToken());

			checked[num] = true;
			for (int j = num + 1; j < arr.length; j++) {
				if (j - 1 < 0)
					break;
				if (arr[j - 1][2] != arr[j][6]) {
					checked[j] = true;
				} else {
					break;
				}
			}
			for (int j = num - 1; j >= 0; j--) {
				if (j + 1 >= arr.length)
					break;
				if (arr[j + 1][6] != arr[j][2]) {
					checked[j] = true;
				} else {
					break;
				}
			}
			moveArr();
		}

		System.out.println(score());
	}
	
	private static int score() {
		int result = 0;
		if (arr[0][0] == '1') {
			result += 1;
		}
		if (arr[1][0] == '1') {
			result += 2;
		}
		if (arr[2][0] == '1') {
			result += 4;
		}
		if (arr[3][0] == '1') {
			result += 8;
		}
		return result;
	}

	private static void moveArr() {
		for (int i = 0; i < checked.length; i++) {
			if (checked[i]) {
				int nowDir = dir;
				if (i % 2 != num % 2) {
					nowDir *= -1;
				}
				if (nowDir == 1) {
					char temp = arr[i][7];
					for (int l = arr[i].length - 1; l >= 1; l--) {
						arr[i][l] = arr[i][l - 1];
					}
					arr[i][0] = temp;
				} else {
					char temp = arr[i][0];
					for (int l = 0; l < arr[i].length - 1; l++) {
						arr[i][l] = arr[i][l + 1];
					}
					arr[i][7] = temp;
				}
			}
		}
	}
}
