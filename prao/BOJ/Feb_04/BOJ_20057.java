import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class BOJ_20057 {

	static int N;

	// 좌,하,우,상
	static int[] dx = { 0, 1, 0, -1 };
	static int[] dy = { -1, 0, 1, 0 };
	static int[] dc = { 1, 1, 2, 2 };

	static int[][] map;
	// 좌,하,우,상
	static int[][] sdx = {
			{ -1, 1, -2, -1, 1, 2, -1, 1, 0 }, 
			{ -1, -1, 0, 0, 0, 0, 1, 1, 2 },
			{ 1, -1, 2, 1, -1, -2, 1, -1, 0 }, 
			{ 1, 1, 0, 0, 0, 0, -1, -1, -2 } 
			};
	static int[][] sdy = { 
			{ 1, 1, 0, 0, 0, 0, -1, -1, -2 }, 
			{ -1, 1, -2, -1, 1, 2, -1, 1, 0 },
			{ -1, -1, 0, 0, 0, 0, 1, 1, 2 }, 
			{ 1, -1, 2, 1, -1, -2, 1, -1, 0 } 
			};
	static int[] ratio = { 1, 1, 2, 7, 7, 2, 10, 10, 5 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());
		map = new int[N][N];

		for (int row = 0; row < N; row++) {
			st = new StringTokenizer(br.readLine());

			for (int col = 0; col < N; col++) {
				map[row][col] = Integer.parseInt(st.nextToken());
			}
		}
		
		int result = getSand(N/2,N/2);
		bw.write(result+"");
		bw.flush();
		bw.close();
		br.close();
	}
	
	private static int getSand(int x, int y) {
		int total = 0;
		int curX = x;
		int curY = y;

		while (true) {
			for (int dir = 0; dir < 4; dir++) {
				for (int move = 0; move < dc[dir]; move++) {
					int nx = curX + dx[dir];
					int ny = curY + dy[dir];

					if (nx < 0 || ny < 0 || nx >= N || ny >= N) {
						return total;
					}

					int sand = map[nx][ny];
					map[nx][ny] = 0;
					int totalSpread = 0;

					for (int index = 0; index < 9; index++) {
						int sx = nx + sdx[dir][index];
						int sy = ny + sdy[dir][index];
						int spread = (sand * ratio[index]) / 100;

						if (sx < 0 || sy < 0 || sx >= N || sy >= N) {
							total += spread;
						} else {
							map[sx][sy] += spread;
						}
						totalSpread += spread;
					}
					// alpha
					int ax = nx + dx[dir];
					int ay = ny + dy[dir];
					int aTotal = sand - totalSpread;

					if (ax < 0 || ay < 0 || ax >= N || ay >= N) {
						total += aTotal;
					} else {
						map[ax][ay] += aTotal;
					}

					curX = nx;
					curY = ny;
				}
			}

			for (int index = 0; index < 4; index++) {
				dc[index] += 2;
			}
		}
	}
}
