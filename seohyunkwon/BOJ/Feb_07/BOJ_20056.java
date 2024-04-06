import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_20056 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        // board 생성
        List<FireBall>[][] board = new ArrayList[N][N];

        for(int i=0; i<board.length; i++) {
            for(int j=0; j< board.length; j++) {
                board[i][j] = new ArrayList<>();
            }
        }

        // 방향 델타 배열 생성
        int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
        int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};

        // 파이어볼 입력
        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(st.nextToken())-1; // 행
            int col = Integer.parseInt(st.nextToken())-1; // 열
            int mass = Integer.parseInt(st.nextToken());   // 질량
            int speed = Integer.parseInt(st.nextToken());   // 속도
            int dir = Integer.parseInt(st.nextToken());   // 방향
            board[row][col].add(new FireBall(row, col, mass, speed, dir));
        }

        // K만큼 주문 시행
        while(K-- > 0) {
            List<FireBall> newBalls = new ArrayList<>();
            for(int i = 0; i < board.length; i++) {
                for(int j = 0; j < board.length; j++) {
                    List<FireBall> list = board[i][j];
                    int size = list.size();
                    for (int k =0; k<size; k++) {
                        FireBall fireBall = list.get(k);
                        int row = (N + fireBall.row + (dx[fireBall.dir] * fireBall.speed) % N) % N;
                        int col = (N + fireBall.col + (dy[fireBall.dir] * fireBall.speed) % N) % N;
                        FireBall newBall = new FireBall(row, col, fireBall.mass, fireBall.speed, fireBall.dir);
                        newBalls.add(newBall);
                    }
                    board[i][j].clear();
                }
            }

            for(int i=0; i<newBalls.size(); i++) {
                board[newBalls.get(i).row][newBalls.get(i).col].add(newBalls.get(i));
            }

            for(int i = 0; i < board.length; i++) {
                for(int j = 0; j < board.length; j++) {
                    int size = board[i][j].size();
                    if(size < 2) continue;
                    // 2개 이상의 파이어볼이 있는 칸
                    // 파이어볼의 질량 = 파이어볼의 질량 합 / 5
                    // 파이어볼의 속력 = 파이어볼 속력 합 / 합쳐진 파이어볼 개수
                    // 합쳐진 파이어볼 방향 모두 홀수 or 짝수 0,2,4,6 / 1, 3, 5, 7
                    // 질량 == 0 이면 소멸
                    int mass = 0;
                    int speed = 0;
                    int odd_count = 0;
                    int even_count = 0;
                    for(int k=0; k<size; k++) {
                        mass += board[i][j].get(k).mass;
                        speed += board[i][j].get(k).speed;
                        if(board[i][j].get(k).dir % 2 == 0) even_count++;
                        else odd_count++;
                    }
                    mass /= 5;
                    speed /= board[i][j].size();
                    board[i][j].clear();
                    if(mass == 0) continue;
                    if(odd_count != 0 && even_count != 0) {
                        for(int k=1; k<=7; k+=2) {
                            board[i][j].add(new FireBall(i, j, mass, speed, k));
                        }
                        continue;
                    }
                    for(int k=0; k<=6; k+=2) {
                        board[i][j].add(new FireBall(i, j, mass, speed, k));
                    }
                }
            }
        }

        int sum = 0;

        for(int i=0; i<board.length; i++) {
            for(int j=0; j< board.length; j++) {
                List<FireBall> list = board[i][j];
                for(FireBall ball : list) {
                    sum += ball.mass;
                }
            }
        }

        System.out.println(sum);

    }

    private static class FireBall {
        int row, col, mass, speed, dir;

        public FireBall(int row, int col, int mass, int speed, int dir) {
            this.row = row;
            this.col = col;
            this.mass = mass;
            this.speed = speed;
            this.dir = dir;
        }

        @Override
        public String toString() {
            return "FireBall{" +
                    "row=" + row +
                    ", col=" + col +
                    ", mass=" + mass +
                    ", speed=" + speed +
                    ", dir=" + dir +
                    '}';
        }
    }
}
