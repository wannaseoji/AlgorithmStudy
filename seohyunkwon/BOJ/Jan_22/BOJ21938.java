import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ21938 {
    private static int[] dx = {-1, 1, 0, 0};
    private static int[] dy = {0, 0, -1, 1};
    private static int[][] graph;
    private static boolean[][] visited;
    private static int N, M, count;
    public static void main(String[] args)  throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        graph = new int[N][M];
        visited = new boolean[N][M];
        count = 0;
        for(int i=0; i<N; i++) {
            int[] tmp = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            int idx = 0;
            for(int j=0; j<tmp.length; j+=3) {
                int avg = (tmp[j]+tmp[j+1]+tmp[j+2])/3;
                graph[i][idx++] = avg;
            }
        }
        int T = Integer.parseInt(br.readLine());
        for(int i=0; i<N; i++) {
            for(int j=0; j<M; j++) {
                graph[i][j] = graph[i][j]>=T?255:0;
            }
        }


        for(int i=0; i<N; i++) {
            for(int j=0; j<M; j++) {
                if(graph[i][j] != 255) continue;
                bfs(i, j);
                count++;
            }
        }

        System.out.println(count);

    }

    private static void bfs(int x, int y) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {x, y});
        while(!queue.isEmpty()) {
            int[] tmp = queue.poll();
            if(visited[tmp[0]][tmp[1]]) continue;
            for(int i=0; i<dx.length; i++) {
                int nx = tmp[0]+dx[i];
                int ny = tmp[1]+dy[i];
                if(nx < 0 || ny < 0 || nx >= N || ny >= M || graph[nx][ny] != 255) continue;
                graph[nx][ny] = 0;
                queue.add(new int[] {nx, ny});
            }
        }
    }
}
