import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class 파이어볼 {

    static MapInfo[][] map; //격자
    static MapInfo[][] tmpMap; //임시 격자
    static int[][] delta = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}}; //파이어볼 방향
    static int N;
    static int K;
    static Deque<Point> pointQue = new ArrayDeque<>(); //현재 총 파이어볼의 좌표들
    static Deque<Point> mustCombinePointQue = new ArrayDeque<>(); //2개이상인 파이어볼의 좌표들

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        map = new MapInfo[N][N];
        tmpMap = new MapInfo[N][N];

        //격자 초기화
        for (int i = 0; i < N; i++) {
            for (int j = 0 ; j < N; j++) {
                map[i][j] = new MapInfo();
                tmpMap[i][j] = new MapInfo();
            }
        }

        //파이어볼 초기화
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken()) - 1;
            int m = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            pointQue.add(new Point(y, x));
            map[y][x].add(new Fireball(m, s, d));
        }

        calcMovedFireball();

        //파이어볼 이동 후 남아있는 질량의 합 계산
        int answer = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                answer += map[i][j].massSum;
            }
        }
        System.out.println(answer);
    }

    public static void calcMovedFireball() {
        int[][] ballCounts;

        while (K > 0) {
            ballCounts = new int[N][N];
            int pointQueSize = pointQue.size();

            //현재 파이어볼 움직이기
            while (pointQueSize > 0) {
                Point p = pointQue.poll();

                int mapSize = map[p.y][p.x].size();
                for (int i = 0; i < mapSize; i++) {
                    Fireball fireball = map[p.y][p.x].poll();
                    move(ballCounts, fireball, p.y, p.x);
                }
                pointQueSize--;
            }

            //2개 이상인 파이어볼 합치기
            while (!mustCombinePointQue.isEmpty()) {
                Point p = mustCombinePointQue.poll();
                //총 파이어볼의 질량이 5 미만이면 파이어볼 소멸
                if (tmpMap[p.y][p.x].massSum < 5) {
                    tmpMap[p.y][p.x].clear();
                    continue;
                }
                //파이어볼 합치기
                tmpMap[p.y][p.x].combine();
            }

            //파이어볼을 합친 후 임시 격자에서 실제 격자로 이동
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    while (!tmpMap[i][j].isEmpty()) {
                        map[i][j].add(tmpMap[i][j].poll());
                    }
                }
            }

            K--;
        }
    }

    //파이어볼 움직이기
    public static void move(int[][] ballCounts, Fireball fireball, int y, int x) {
        int dir = fireball.dir;
        int speed = fireball.speed;
        int ty = (N + y + delta[dir][0] * (speed % N)) % N;
        int tx = (N + x + delta[dir][1] * (speed % N)) % N;

        ballCounts[ty][tx]++;
        //ty, tx에서 파이어볼이 2개가 되면
        if (ballCounts[ty][tx] == 2) {
            mustCombinePointQue.add(new Point(ty, tx));
        }  else if (ballCounts[ty][tx] == 1){ //
            pointQue.add(new Point(ty, tx));
        }
        //임시 격자에 파이어볼 추가
        //(실제 격자로 추가하면 파이어볼을 움직이고 있는데 추가한 격자에 있던 파이어볼을 움직일시 다 움직이는 문제 발생)
        tmpMap[ty][tx].add(fireball);
    }
}

class MapInfo {
    Deque<Fireball> balls = new ArrayDeque<>();
    int massSum;
    int speedSum;
    int oddDir;
    int evenDir;

    public void add(Fireball fireball) {
        balls.add(fireball);
        massSum += fireball.mass;
        speedSum += fireball.speed;
        if (fireball.dir % 2 == 0) {
            evenDir++;
        } else {
            oddDir++;
        }
    }

    public Fireball poll() {
        Fireball fireball = balls.poll();
        massSum -= fireball.mass;
        speedSum -= fireball.speed;
        if (fireball.dir % 2 == 0) {
            evenDir--;
        } else {
            oddDir--;
        }
        return fireball;
    }

    public int size() {
        return balls.size();
    }

    public boolean isEmpty() {
        return balls.isEmpty();
    }

    public void clear() {
        balls.clear();
        massSum = 0;
        speedSum = 0;
        oddDir = 0;
        evenDir = 0;
    }

    public void combine() {
        int size = size();
        int mass = (int) Math.floor(massSum / 5.0); //합쳐진 파이어볼의 질량 계산
        int speed = (int) Math.floor(speedSum / (double) size); //합쳐진 파이어볼의 속력 계산
        int[] dirs1 = {0, 2, 4, 6};
        int[] dirs2 = {1, 3, 5, 7};


        if (oddDir == 0 || evenDir == 0) { //파이어볼의 방향이 모두 짝수이거나 홀수이면
            clear(); //if문 안에서 clear()를 해야 짝, 홀수를 구분할 수 있음
            for (int i = 0; i < 4; i++) {
                add(new Fireball(mass, speed, dirs1[i]));
            }
        } else { //파이어볼의 방향이 모두 짝수이거나 홀수가 아니라면
            clear(); //if문 안에서 clear()를 해야 짝, 홀수를 구분할 수 있음
            for (int i = 0; i < 4; i++) {
                add(new Fireball(mass, speed, dirs2[i]));
            }
        }
    }

    public int getMassSum() {
        return massSum;
    }
}

class Fireball {
    int mass;
    int speed;
    int dir;

    public Fireball(int mass, int speed, int dir) {
        this.mass = mass;
        this.speed = speed;
        this.dir = dir;
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