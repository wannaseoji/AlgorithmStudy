import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        CogWheel[] cogWheels = new CogWheel[6];

        //톱니바퀴 초기화
        for (int i = 1; i <= 4; i++) {
            cogWheels[i] = new CogWheel(br.readLine());
        }

        //톱니바퀴 회전
        int K = Integer.parseInt(br.readLine());
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int wheelNumber = Integer.parseInt(st.nextToken());
            int spinDir = Integer.parseInt(st.nextToken());
            cogWheels[wheelNumber].spinFlag = spinDir;

            dfs(wheelNumber, cogWheels, -1); //현재 바퀴의 왼쪽들 회전여부 계산
            dfs(wheelNumber, cogWheels, 1); //현재 바퀴의 오른쪽들 회전여부 계산

            //회전여부 계산 후 회전
            for (int j = 1; j <= 4; j++) {
                cogWheels[j].spin();
            }
        }

        int answer = 0;
        for (int i = 0; i < 4; i++) {
            int pole = cogWheels[i + 1].top;
            if (pole == 0) continue;
            answer += (int) Math.pow(2, i);
        }
        System.out.println(answer);
    }

    public static void dfs(int wheelNumber, CogWheel[] cogWheels, int dx) {
        //현재 바퀴가 회전하지 않거나, 옆에 바퀴가 없으면
        if (cogWheels[wheelNumber].spinFlag == 0
                || cogWheels[wheelNumber + dx] == null) {
            return;
        }

        int nextNumber = wheelNumber + dx;
        //dx가 -1이면 왼쪽, 1이면 오른쪽
        if (dx == -1) {
            if (cogWheels[wheelNumber].left != cogWheels[nextNumber].right) {
                cogWheels[nextNumber].spinFlag = cogWheels[wheelNumber].spinFlag * -1;
                dfs(nextNumber, cogWheels, dx);
            }
        } else if (dx == 1) {
            if (cogWheels[wheelNumber].right != cogWheels[nextNumber].left) {
                cogWheels[nextNumber].spinFlag = cogWheels[wheelNumber].spinFlag * -1;
                dfs(nextNumber, cogWheels, dx);
            }
        }
    }
}

class CogWheel {
    int[] poles = new int[8];
    int top;
    int left;
    int right;
    int spinFlag;

    CogWheel(String poleInfo) {
        for (int i = 0; i < 8; i++) {
            poles[i] = poleInfo.charAt(i) - '0';
        }
        top = poles[0];
        right = poles[2];
        left = poles[6];
    }

    public void spin() { //회전
        if (spinFlag == 1)
            rightSpin();
        else if (spinFlag == -1)
            leftSpin();
        spinFlag = 0;
        top = poles[0];
        right = poles[2];
        left = poles[6];
    }

    public void leftSpin() { //반시계 방향 회전
        int[] tmp = new int[8];
        for (int i = 0; i < 8; i++) {
            tmp[i] = poles[(i + 1) % 8];
        }
        poles = tmp;
    }

    public void rightSpin() { //시계 방향 회전
        int[] tmp = new int[8];
        for (int i = 1; i <= 8; i++) {
            tmp[i % 8] = poles[(i - 1) % 8];
        }
        poles = tmp;
    }
}