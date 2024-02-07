import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

class FireBall {
    int weight, speed, direction;

    public FireBall(int weight, int speed, int direction) {
        this.weight = weight;
        this.speed = speed;
        this.direction = direction;
    }
}

public class BOJ_20056 {

    public static int N, M, K;

    public static ArrayList<FireBall>[][] map;

    public static final int[] dx = { -1, -1, 0, 1, 1, 1, 0, -1 };
    public static final int[] dy = { 0, 1, 1, 1, 0, -1, -1, -1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        // 맵 초기화
        map = new ArrayList[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = new ArrayList<>();
            }
        }
        // 파이어볼 생성
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(st.nextToken()) - 1;
            int col = Integer.parseInt(st.nextToken()) - 1;
            int weight = Integer.parseInt(st.nextToken());
            int speed = Integer.parseInt(st.nextToken());
            int direction = Integer.parseInt(st.nextToken());
            map[row][col].add(new FireBall(weight, speed, direction));
        }
        // 파이어볼을 움직이고, 파이어볼이 2개인 경우 분리한다.
        for (int i = 0; i < K; i++) {
            move();
            split();
        }
        // 맵을 순회하며 파이어볼의 합을 구한다.
        int answer = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (FireBall ball : map[i][j]) {
                    answer += ball.weight;
                }
            }
        }
        bw.write(String.valueOf(answer));
        bw.close();
        br.close();
    }

    // 파이어볼 이동
    private static void move() {
        ArrayList<FireBall>[][] newMap = new ArrayList[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                newMap[i][j] = new ArrayList<>();
            }
        }
        // 맵을 순회하며 파이어볼이 있는 경우 움직인다.
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j].size() > 0) {
                    for (FireBall ball : map[i][j]) {
                        int ns = ball.speed % N;
                        int nx = i + dx[ball.direction] * ns;
                        int ny = j + dy[ball.direction] * ns;
                        if (nx >= N) {
                            nx -= N;
                        } else if (nx < 0) {
                            nx += N;
                        }
                        if (ny >= N) {
                            ny -= N;
                        } else if (ny < 0) {
                            ny += N;
                        }
                        // 새로운 맵에 파이어볼을 저장한다.
                        newMap[nx][ny].add(new FireBall(ball.weight, ball.speed, ball.direction));
                    }
                }
            }
        }
        map = newMap;
    }

    // 파이어볼이 2개 이상인 경우 분리한다.
    private static void split() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j].size() >= 2) {
                    int weightSum = 0;
                    int speedSum = 0;
                    boolean even = true, odd = true;
                    // 질량합과 속력합을 구한다.
                    for (FireBall ball : map[i][j]) {
                        weightSum += ball.weight;
                        speedSum += ball.speed;
                        // 2의 배수이면 odd를 false로, 아니면 even을 false로 변환한다.
                        if (ball.direction % 2 == 0) {
                            odd = false;
                        } else {
                            even = false;
                        }
                    }
                    int m = weightSum / 5;
                    int s = speedSum / map[i][j].size();
                    map[i][j].clear();
                    if (m > 0) {
                        for (int k = 0; k < 4; k++) {
                            if (odd || even) {
                                map[i][j].add(new FireBall(m, s, 0 + 2 * k));
                            } else {
                                map[i][j].add(new FireBall(m, s, 1 + 2 * k));
                            }
                        }
                    }
                }
            }
        }
    }
}
