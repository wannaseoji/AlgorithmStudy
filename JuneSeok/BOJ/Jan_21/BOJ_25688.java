import java.io.*;
import java.util.*;

public class Main {

    private static int[][] map = new int[5][5];
    private static boolean[][] visited;
    private static boolean[] dfsVisited = new boolean[6];
    private static List<Point> points = new ArrayList<Point>();
    private static List<Integer> seqList = new LinkedList<>();
    private static Point startP;
    private static int answer = Integer.MAX_VALUE;
    private static int startR;
    private static int startC;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        for (int i = 0; i < 5; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 5; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] >= 1) {
                    points.add(new Point(i, j));
                }
            }
        }
        st = new StringTokenizer(br.readLine());
        startR = Integer.parseInt(st.nextToken());
        startC = Integer.parseInt(st.nextToken());
        startP = new Point(startR, startC);

        dfs(0);
        System.out.println(answer);
    }

    public static int bfs(Point sp, Point ep) {
        Deque<Point> q = new ArrayDeque<Point>();
        q.addLast(sp);
        visited = new boolean[5][5];
        visited[sp.r][sp.c] = true;
        int[] dc = {-1, 1, 0, 0};
        int[] dr = {0, 0, -1, 1};

        while (!q.isEmpty()) {
            Point p = q.pollFirst();

            if (p.compare(ep)) {
                return p.dist;
            }

            for (int i = 0; i < 4; i++) {
                int tc = p.c + dc[i];
                int tr = p.r + dr[i];

                if (validRange(tc, tr)) {
                    continue;
                }

                if (!visited[tr][tc] && map[tr][tc] >= 0) {
                    q.addLast(new Point(tr, tc, p.dist + 1));
                    visited[tr][tc] = true;
                }
            }
        }
        return -1;
    }

    public static void dfs(int depth) {
        if (depth == 6) {
            int distSum = 0;
            for (int seqN : seqList) {
                Point destPoint = points.get(seqN);

                int dist = bfs(startP, destPoint);
                if (dist == -1) {
                    System.out.println(-1);
                    System.exit(0);
                }
                distSum += dist;
                startP = destPoint;
            }
            answer = Math.min(answer, distSum);
            startP = new Point(startR, startC);
            return;
        }

        for (int i = 0; i <= 5; i++) {
            if (!dfsVisited[i]) {
                dfsVisited[i] = true;
                seqList.add(i);
                dfs(depth + 1);
                seqList.remove(depth);
                dfsVisited[i] = false;
            }
        }
    }

    public static boolean validRange(int c, int r) {
        return c < 0 || c >= 5 || r < 0 || r >= 5;
    }
}

class Point {
    int r;
    int c;
    int dist;

    public Point(int r, int c) {
        this.r = r;
        this.c = c;
        this.dist = 0;
    }

    public Point(int r, int c, int dist) {
        this(r, c);
        this.dist = dist;
    }

    public boolean compare(Point p) {
        return (this.r == p.r) && (this.c == p.c);
    }
}