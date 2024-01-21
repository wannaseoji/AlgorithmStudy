import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_21938 {
	private static final int COLOR_COUNT = 3;
	
	static int t, n, m, count;
	
	static ArrayList<ArrayList<Long>> list;
	static ArrayList<ArrayList<Integer>> averageList;
	static boolean[][] visited;
	
	static int[] dirX = { 1, -1, 0, 0 };
	static int[] dirY = { 0, 0, 1, -1 };
	

	public static void main(String[] args) throws IOException {
		StringTokenizer st;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		// 세로
		n = Integer.parseInt(st.nextToken());
		// 가로
		m = Integer.parseInt(st.nextToken());

		list = new ArrayList<ArrayList<Long>>();
		averageList = new ArrayList<ArrayList<Integer>>();
		visited = new boolean[n][m];

		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			list.add(new ArrayList<Long>());
			for (int j = 0; j < m; j++) {
				int red = Integer.parseInt(st.nextToken());
				int green = Integer.parseInt(st.nextToken());
				int blue = Integer.parseInt(st.nextToken());
				long average = (red + green + blue) / COLOR_COUNT;
				list.get(i).add(average);
			}
		}
		
		t = Integer.parseInt(br.readLine());
		
		for (int i = 0; i < list.size(); i++) {
			averageList.add(new ArrayList<Integer>());
			for (int j = 0; j < list.get(i).size(); j++) {
				if (list.get(i).get(j) >= t) {
					averageList.get(i).add(255);
				} else {
					averageList.get(i).add(0);
				}
			}
		}

		
		for (int i = 0; i < visited.length; i++) {
			for (int j = 0; j < visited[i].length; j++) {
				if (!visited[i][j] && averageList.get(i).get(j) != 0) {
					bfs(i, j);
				}
			}
		}
		
		System.out.println(count);
	}

	public static void bfs(int x, int y) {
		Queue<int[]> que = new LinkedList<int[]>();
		
		que.offer(new int[] { x, y });
		visited[x][y] = true;

		while (!que.isEmpty()) {
			int[] temp = que.poll();
			for (int i = 0; i < dirX.length; i++) {
				int curX = temp[0] + dirX[i];
				int curY = temp[1] + dirY[i];
				if (curX < 0 || curY < 0 || curX >= n || curY >= m)
					continue;
				
				if (!visited[curX][curY] && averageList.get(curX).get(curY) != 0) {
					que.offer(new int[] { curX, curY });
					visited[curX][curY] = true;
				}
			}
		}
		
		count++;
	}
}
