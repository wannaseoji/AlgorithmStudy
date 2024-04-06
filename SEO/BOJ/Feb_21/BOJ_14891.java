import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static ArrayList<Integer>[] gears = new ArrayList[4];
    public static int[] gearDirections = new int[4];     // 1 : 오른쪽 회전, -1 : 왼쪽 회전, 0 : 회전x
    public static int score = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 톱니 4개 입력
        for (int i = 0; i < 4; i++) {
            gears[i] = new ArrayList<>();
            String tokens[] = br.readLine().split("");
            for (int j = 0; j < 8; j++) {
                gears[i].add(Integer.parseInt(tokens[j]));
            }
        }
        // 회전 입력값
        int K = Integer.parseInt(br.readLine());              // 회전 횟수
        for (int i = 0; i < K; i++) {
            Arrays.fill(gearDirections, 0);               // 회전 방향 초기화
            String tokens[] = br.readLine().split(" ");
            int gearNum = Integer.parseInt(tokens[0]) - 1;    // 기어 숫자
            int direction = Integer.parseInt(tokens[1]);       // 회전 방향

            gearDirections[gearNum] = direction;
            checkRight(gearNum);
            checkLeft(gearNum);

            // 회전 시작!
            for (int j = 0; j < 4; j++) {
                if (gearDirections[j] == 1) {
                    turnRight(j);
                } else if (gearDirections[j] == -1) {
                    turnLeft(j);
                } else {
                    continue;
                }
            }

        }
        calculateScore();
        System.out.print(score);

    }

    // 왼쪽 기어와 비교
    static void checkLeft(int gearNum) {
        if (gearNum == 0) return;

        if (gears[gearNum - 1].get(2) != gears[gearNum].get(6)) {
            if (gearDirections[gearNum] == 1) {
                gearDirections[gearNum - 1] = -1;
            } else if (gearDirections[gearNum] == -1) {
                gearDirections[gearNum - 1] = 1;
            }
            checkLeft(gearNum - 1);
        }
    }

    // 오른쪽 기어와 비교
    static void checkRight(int gearNum) {
        if (gearNum == 3) return;                                  // 기어가 3이 마지막이므로 종료

        if (gears[gearNum].get(2) != gears[gearNum + 1].get(6)) {   // gear0[2], gear1[6]비교
            if (gearDirections[gearNum] == 1) {
                gearDirections[gearNum + 1] = -1;
            } else if (gearDirections[gearNum] == -1) {
                gearDirections[gearNum + 1] = 1;
            }
            checkRight(gearNum + 1);
        }
    }

    // 반시계 방향 회전 (-1)
    static void turnLeft(int i) {
        gears[i].add(gears[i].get(0));
        gears[i].remove(0);
    }

    // 시계 방향 회전 (1)
    static void turnRight(int i) {
        gears[i].add(0, gears[i].get(7));
        gears[i].remove(8);
    }

    // 최종 점수 계산
    static void calculateScore() {
        for (int i = 0; i < 4; i++) {
            score += gears[i].get(0) * (1 << i);
        }
    }
}
