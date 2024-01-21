import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class BOJ_25688 {
	static int[][] arr;
	static int number = 1;
	static int[] dirX = { 1, -1, 0, 0 };
	static int[] dirY = { 0, 0, 1, -1 };
	
	static Set<Integer> countSet;
	static int[][] countArr;
	static int[][] visitCount;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		arr = new int[5][5];
		countSet = new HashSet<Integer>();
		countArr = new int[5][5];
		visitCount = new int[5][5];

		for (int i = 0; i < 5; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 5; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		st = new StringTokenizer(br.readLine());
		// 현재 X
		int currX = Integer.parseInt(st.nextToken());
		// 현재 Y
		int currY = Integer.parseInt(st.nextToken());

		int result = bfs(currX, currY);

		System.out.println(result);

	}

	public static int bfs(int x, int y) {
		Queue<int[]> que = new LinkedList<int[]>();

		que.add(new int[] { x, y });

		while (!que.isEmpty()) {
			int[] xyArr = que.poll();

			int currX = xyArr[0];
			int currY = xyArr[1];
			
			visitCount[currX][currY]++;
			
			if (arr[currX][currY] >= 1 && arr[currX][currY] <= 6) {
				countSet.add(arr[currX][currY]);
			}

			if (countSet.size() == 6)
				return countArr[currX][currY];

			if (visitCount[currX][currY] < 6) {
				for (int i = 0; i < 4; i++) {

					int nextX = currX + dirX[i];
					int nextY = currY + dirY[i];

					if (nextX < 0 || nextY < 0 || nextX >= arr.length || nextY >= arr.length
							|| visitCount[nextX][nextY] > 6)
						continue;

					if (arr[nextX][nextY] != -1) {
						countArr[nextX][nextY] = countArr[currX][currY] + 1;

						que.add(new int[] { nextX, nextY });
					}
				}
			}
		}
		return -1;
	}
}
