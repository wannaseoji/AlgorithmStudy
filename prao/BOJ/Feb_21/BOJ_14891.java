import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class 톱니바퀴 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st;

    static int answer = 0;
    static int[][] cogwheels = new int[4][8];

    public static void main(String[] args) throws IOException {
        init();
        game();
        printResult();
    }

    static void init() throws IOException {
        for (int row = 0; row < 4; row++) {
            String input = br.readLine();
            for (int col = 0; col < 8; col++) {
                cogwheels[row][col] = input.charAt(col) - '0';
            }
        }
    }

    static void game() throws IOException {
        int count = Integer.parseInt(br.readLine());

        for (int i = 0; i < count; i++) {
            st = new StringTokenizer(br.readLine());
            int idx = Integer.parseInt(st.nextToken()) - 1;
            int dir = Integer.parseInt(st.nextToken());
            operate(idx, dir, true, true);
        }

        for (int i = 0; i < 4; i++) {
            answer += (int)(Math.pow(2, i) * cogwheels[i][0]);
        }
    }

    static void operate(int idx, int dir, boolean isLeft, boolean isRight) {
        if (idx < 0 || idx > 3)
            return;
        int left = idx - 1;
        int right = idx + 1;
        if (isLeft && left >= 0 && cogwheels[idx][6] != cogwheels[left][2]) {
            operate(left, -dir, true, false);
        }
        if (isRight && right <= 3 && cogwheels[idx][2] != cogwheels[right][6]) {
            operate(right, -dir, false, true);
        }
        rotate(idx, dir);
    }

    static void rotate(int idx, int dir) {
        if (dir == 1) {
            int temp = cogwheels[idx][7];
            for (int i = 7; i > 0; i--) {
                cogwheels[idx][i] = cogwheels[idx][i - 1];
            }
            cogwheels[idx][0] = temp;
        } else {
            int temp = cogwheels[idx][0];
            for (int i = 0; i < 7; i++) {
                cogwheels[idx][i] = cogwheels[idx][i + 1];
            }
            cogwheels[idx][7] = temp;
        }
    }

    static void printResult() throws IOException {
        bw.write(String.valueOf(answer));
        release();
    }

    static void release() throws IOException {
        bw.close();
        br.close();
    }
}
