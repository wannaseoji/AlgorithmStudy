import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int n;
	static boolean[] visited;
	static ArrayList<Integer>[] list;
	static int[] count;
	static int maxCount;

	public static void main(String[] args) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());

		// 컴퓨터 번호
		n = Integer.parseInt(st.nextToken());
		// m개의 줄 제시
		int m = Integer.parseInt(st.nextToken());

		count = new int[n + 1];
		list = new ArrayList[n + 1];
		for (int i = 0; i < n + 1; i++) {
			list[i] = new ArrayList<>();
		}

		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			list[b].add(a);
		}

		for (int i = 1; i < n + 1; i++) {
			visited = new boolean[n + 1];
			bfs(i);
			if (maxCount < count[i])
				maxCount = count[i];
		}

		for (int i = 0; i < count.length; i++) {
			if (count[i] == maxCount) {
				sb.append(i + " ");
			}
		}

		System.out.println(sb);
	}

	public static void bfs(int node) {
		Queue<Integer> que = new LinkedList<Integer>();

		visited[node] = true;
		que.offer(node);

		while (!que.isEmpty()) {
			int pollNumber = que.poll();
			
			for(int availableList : list[pollNumber]) {
				if(!visited[availableList]) {
					visited[availableList] = true;
					que.offer(availableList);
				}

				count[node]++;
			}

		}
	}
}
