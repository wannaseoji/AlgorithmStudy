import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_15685 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st;
		
		boolean[][] arr = new boolean[101][101];
		
		int[] dy = {0, -1, 0, 1};
		int[] dx = {1, 0, -1, 0};
		
		for(int t=0; t<N; t++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			int g = Integer.parseInt(st.nextToken());
			
			arr[x][y] = true;
			arr[x+dx[d]][y+dy[d]] = true;
			
			List<Point> list = new ArrayList<>();
			list.add(new Point(x, y));
			list.add(new Point(x+dx[d], y+dy[d]));
			
			while(g-- > 0) {
				Point last = list.get(list.size()-1);
				int size = list.size()-1;
				for(int i = size; i >= 0; i--) {
					Point tmp = list.get(i);
					int dist_x = last.x - tmp.x;
					int dist_y = last.y - tmp.y;
					Point newTmp = new Point(last.x + dist_y, last.y - dist_x);
					list.add(newTmp);
					arr[newTmp.x][newTmp.y] = true;
				}
			}
		}
		
		int result = 0;
		for(int i=0; i<arr.length-1; i++) {
			for(int j=0; j<arr.length-1; j++) {
				if(arr[i][j] && arr[i+1][j] && arr[i][j+1] && arr[i+1][j+1]) result++;
			}
		}
		
		System.out.println(result);
	}
	
	private static class Point {
		int x, y;

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		
	}
}
