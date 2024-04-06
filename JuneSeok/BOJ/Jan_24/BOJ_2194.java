import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {

    private static int[][] map;
    private static int N;
    private static int M;
    private static int A;
    private static int B;
    private static int[] dx = {-1, 1, 0, 0};
    private static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        map = new int[N][M]; // -1 : 벽, 0 : 방문 X, 1 이상 : 방문 O

        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken()) - 1;
            map[y][x] = -1;
        }
        st = new StringTokenizer(br.readLine());
        int startY = Integer.parseInt(st.nextToken()) - 1;
        int startX = Integer.parseInt(st.nextToken()) - 1;
        Unit startUnit = new Unit(startY, startX);

        st = new StringTokenizer(br.readLine());
        int endY = Integer.parseInt(st.nextToken()) - 1;
        int endX = Integer.parseInt(st.nextToken()) - 1;
        Unit endUnit = new Unit(endY, endX);

        int answer = bfs(startUnit, endUnit);
        bw.write((map[endY][endX] == 0 ? -1 : map[endY][endX]) + "");
        bw.flush();
        bw.close();
    }

    public static int bfs(Unit start, Unit end) {
        Deque<Unit> q = new ArrayDeque<>();
        q.addLast(start);

        while (!q.isEmpty()) {
            Unit u = q.pollFirst();

            if (u.comparePos(end)) {
                return map[u.y][u.x];
            }

            for (int i = 0; i < 4; i++) {
                int tx = u.x + dx[i];
                int ty = u.y + dy[i];

                if (vaildPos(ty, tx) && map[ty][tx] == 0) {
                    map[ty][tx] = map[u.y][u.x] + 1;
                    q.addLast(new Unit(ty, tx));
                }
            }
        }
        return -1;
    }

    public static boolean vaildRange(int y, int x) {
        return x < 0 || x >= M || y < 0 || y >= N;
    }

    public static boolean vaildPos(int y, int x) {
        for (int i = y; i < y + A; i++) {
            for (int j = x; j < x + B; j++) {
                if (vaildRange(i, j)) {
                    return false;
                }
                if (map[i][j] == -1) {
                    return false;
                }
            }
        }
        return true;
    }
}

class Unit {
    int x;
    int y;

    public Unit(int y, int x) {
        super();
        this.x = x;
        this.y = y;
    }

    public boolean comparePos(Unit u) {
        return (this.x == u.x) && (this.y == u.y);
    }
}
