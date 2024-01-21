import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Point {
    int x;
    int y;
    public Point(int y, int x) {
        this.x = x;
        this.y = y;
    }
}

public class Main {

    private static int[][] rgbs;
    private static int[][] pixels;
    private static boolean[][] visited;
    private static int[] dx = {-1, 1, 0, 0};
    private static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        rgbs = new int[N][M * 3];
        pixels = new int[N][M];
        visited = new boolean[N][M];
        int answer = 0;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M * 3; j++) {
                rgbs[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int bound = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            int m = 0;
            for (int j = 0; j < M * 3; j += 3) {
                int r = rgbs[i][j];
                int g = rgbs[i][j + 1];
                int b = rgbs[i][j + 2];
                int average = (r + g + b) / 3;
                if (average >= bound) {
                    pixels[i][m++] = 255;
                } else {
                    pixels[i][m++] = 0;
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (!visited[i][j] && pixels[i][j] == 255) {
                    bfs(i, j);
                    ++answer;
                }
            }
        }

        System.out.println(answer);
    }

    public static void bfs(int y, int x) {
        Queue<Point> q = new LinkedList<>();
        q.add(new Point(y, x));
        visited[y][x] = true;

        while (!q.isEmpty()) {
            Point p = q.poll();

            for (int i = 0; i < 4; i++) {
                int tx = p.x + dx[i];
                int ty = p.y + dy[i];

                try {
                    if (!visited[ty][tx] && pixels[ty][tx] == 255) {
                        q.add(new Point(ty, tx));
                        visited[ty][tx] = true;
                    }
                } catch (ArrayIndexOutOfBoundsException e){
                    continue;
                }
            }
        }
    }
}
