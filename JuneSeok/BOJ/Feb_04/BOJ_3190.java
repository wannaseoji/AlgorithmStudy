import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class 뱀 {

    static int[][] map;
    static Deque<DirInfo> dirQue = new ArrayDeque<>();
    static int[][] delta = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; //우, 하, 좌, 상
    static char[][] dirMap;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        map = new int[N + 1][N + 1];
        dirMap = new char[N + 1][N + 1];

        int K = Integer.parseInt(br.readLine());
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            map[r][c] = 3; //사과
        }

        int L = Integer.parseInt(br.readLine());
        for (int i = 0; i < L; i++) {
            st = new StringTokenizer(br.readLine());
            int second = Integer.parseInt(st.nextToken());
            char dir = st.nextToken().charAt(0);
            dirQue.add(new DirInfo(second, dir));
        }

        int answer = bfs(N);
        System.out.println(answer);
    }

    public static int bfs(int N) {
        Deque<Snake> q = new ArrayDeque<>();
        q.add(new Snake(new Point(1, 1), new Point(1, 1), 0, 0));
        map[1][1] = 2;
        int curTime = 0;

        while (!q.isEmpty()) {
            Snake snake = q.poll();
            int headX = snake.head.x;
            int headY = snake.head.y;
            int tailX = snake.tail.x;
            int tailY = snake.tail.y;

            //머리 방향 전환
            if (!dirQue.isEmpty() && (dirQue.peek().second == curTime)) {
                DirInfo info = dirQue.poll();
                switch (info.dir) {
                    case 'L':
                        if (snake.headDir - 1 < 0)
                            snake.headDir = 4;
                        snake.headDir = (snake.headDir - 1) % 4;
                        dirMap[headY][headX] = 'L';
                        break;
                    case 'D':
                        snake.headDir = (snake.headDir + 1) % 4;
                        dirMap[headY][headX] = 'D';
                }
            }

            //꼬리 방향 전환
            char dir = dirMap[tailY][tailX];
            switch (dir) {
                case 'L':
                    if (snake.tailDir - 1 < 0)
                        snake.tailDir = 4;
                    snake.tailDir = (snake.tailDir - 1) % 4;
                    dirMap[tailY][tailX] = ' ';
                    break;
                case 'D':
                    snake.tailDir = (snake.tailDir + 1) % 4;
                    dirMap[tailY][tailX] = ' ';
            }

            int headTx = headX + delta[snake.headDir][1];
            int headTy = headY + delta[snake.headDir][0];
            int tailTx = tailX;
            int tailTy = tailY;
            boolean isApple = true;

            //조건 2 : 벽이나 자기자신의 몸과 부딪히면 게임이 끝난다.
            if (invalidRange(headTy, headTx, N) || map[headTy][headTx] == 1) {
                curTime++;
                break;
            }

            //조건 4 : 이동한 칸에 사과가 없다면, 몸길이를 줄여서 꼬리가 위치한 칸을 비워준다. 즉, 몸길이는 변하지 않는다.
            if (map[headTy][headTx] != 3) {
                isApple = false;
                tailTx += delta[snake.tailDir][1];
                tailTy += delta[snake.tailDir][0];
            }

            //조건 1 : 뱀은 몸길이를 늘려 머리를 다음칸에 위치시킨다.
            map[headY][headX] = 1;
            map[headTy][headTx] = 2;

            //조건 3 : 이동한 칸에 사과가 있다면, 그 칸에 있던 사과가 없어지고 꼬리는 움직이지 않는다.
            //조건 4 : 이동한 칸에 사과가 없다면, 몸길이를 줄여서 꼬리가 위치한 칸을 비워준다. 즉, 몸길이는 변하지 않는다.
            if (!isApple) map[tailY][tailX] = 0;

            q.add(new Snake(new Point(headTy, headTx), new Point(tailTy, tailTx), snake.headDir, snake.tailDir));
            curTime++;
        }
        return curTime;
    }

    public static boolean invalidRange(int y, int x, int N) {
        return x <= 0 || x > N || y <= 0 || y > N;
    }
}

class Snake {

    Point head; //map[y][x] : 2
    Point tail; //map[y][x] : 1
    int headDir; //0: 우, 1: 하, 2: 좌, 3: 상
    int tailDir; //0: 우, 1: 하, 2: 좌, 3: 상

    public Snake(Point head, Point tail, int headDir, int tailDir) {
        this.head = head;
        this.tail = tail;
        this.headDir = headDir;
        this.tailDir = tailDir;
    }
}

class Point {
    int x;
    int y;

    public Point(int y, int x) {
        this.x = x;
        this.y = y;
    }
}

class DirInfo {
    int second;
    char dir;

    public DirInfo(int second, char dir) {
        this.second = second;
        this.dir = dir;
    }
}
