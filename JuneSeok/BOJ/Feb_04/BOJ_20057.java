import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class 토네이도 {

    public static int[][] map;
    public static int N;
    //모래가 흩어지는 비율의 delta 좌, 하 (우, 상이면 -1을 곱해서 사용)
    public static int[][][] delta = {
            {{0, -2}, {-1, -1}, {1, -1}, {-1, 0}, {1, 0}, {-2, 0}, {2, 0}, {-1, 1}, {1, 1}},
            {{2, 0}, {1, 1}, {1, -1}, {0, 1}, {0, -1}, {0, 2}, {0, -2}, {-1, 1}, {-1, -1}}
    };
    //토네이도가 움직이는 delta
    public static int[][] moveDelta = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
    //모래가 흩어지는 비율 5%, 10%, 10%, 7%, 7%, 2%, 2%, 1%, 1%
    public static double[] ratios = {0.05, 0.1, 0.1, 0.07, 0.07, 0.02, 0.02, 0.01, 0.01};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];

        //지도 초기화
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int mid = N / 2;
        System.out.println(bfs(mid, mid));
    }

    public static int bfs(int r, int c) {
        //방향 전환 카운트 배열
        int dirSize = N * 2 - 1;
        int[] dirCounts = new int[dirSize];
        dirCounts[dirSize - 1] = N -1;
        int tmp = 0;
        for (int i = 0; i < dirSize - 1; i++) {
            if (i % 2 == 0) {
                tmp++;
            }
            dirCounts[i] = tmp;
        }

        Deque<Point> q = new ArrayDeque<>();
        q.add(new Point(r, c));
        int idx = 0;    //방향 전환 배열 index
        int curDir = 0; //0: 좌, 1: 하, 2: 우, 3: 상
        int answer = 0; //격자밖 모래의 양

        while (!q.isEmpty()) {
            Point p = q.poll();
            if (p.x == 0 && p.y == 0) { //0,0이면 끝남
                break;
            }

            //토네이도가 이동할 좌표
            int tx = p.x + moveDelta[curDir][1];
            int ty = p.y + moveDelta[curDir][0];

            //흩날리는 모래 계산
            int sand = map[ty][tx];
            if (sand > 0) {
                for (int i = 0; i < 9; i++) {
                    int mTx = 0; //흩날리는 모래의 좌표
                    int mTy = 0; //흩날리는 모래의 좌표

                    //움직이는 방향이 우, 상이면 -1을 곱하고, 좌, 하이면 그대로
                    if (curDir >= 2) {
                        mTx = tx + delta[curDir % 2][i][1] * -1;
                        mTy = ty + delta[curDir % 2][i][0] * -1;
                    } else {
                        mTx = tx + delta[curDir % 2][i][1];
                        mTy = ty + delta[curDir % 2][i][0];
                    }

                    //흩날리는 모래의 양
                    int flutteringSand = (int) (sand * ratios[i]);
                    map[ty][tx] -= flutteringSand;

                    //모래가 격자 밖으로 나가면
                    if (invalidRange(mTy, mTx)) {
                        answer += flutteringSand;
                    } else { //모래가 격자 이내이면
                        map[mTy][mTx] += flutteringSand;
                    }
                }

                //모래가 흩날린 후 남은 모래를 앞에 이동시킴
                int tx2 = tx + moveDelta[curDir][1];
                int ty2 = ty + moveDelta[curDir][0];
                if (invalidRange(ty2, tx2)) {
                    answer += map[ty][tx];
                } else {
                    map[ty2][tx2] += map[ty][tx];
                }
                map[ty][tx] = 0;
            }
            q.add(new Point(ty, tx));

            //토네이도 방향 전환
            dirCounts[idx]--;
            if (dirCounts[idx] == 0) {
                curDir = (curDir + 1) % 4;
                ++idx;
            }
        }

        return answer;
    }

    public static boolean invalidRange(int y, int x) {
        return x < 0 || x >= N || y < 0 || y >= N;
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
