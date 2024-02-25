import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class BOJ_14891 {
    private static int[][] wheels;
    private static boolean[] visit;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        wheels = new int[4][8];
        StringTokenizer st;

        for(int i = 0; i < 4; i++) {
            String[] arr = br.readLine().split("");
            for(int j = 0; j < 8; j++) {
                wheels[i][j] = Integer.parseInt(arr[j]);
            }
        }

        int K = Integer.parseInt(br.readLine());
        for(int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int idx = Integer.parseInt(st.nextToken())-1;
            int dir = Integer.parseInt(st.nextToken());
            visit = new boolean[4];
            check(idx, dir);
        }

        int result = 0;

        for(int i = 0; i < 4; i++) {
            if(wheels[i][0] == 1) result += Math.pow(2, i);
        }
        System.out.println(result);
    }

    private static void check(int idx, int dir) {
        if(visit[idx]) return;
        visit[idx] = true;

        if(idx != 3) {
            // 오른쪽 확인
            int left = wheels[idx][2];
            int right = wheels[idx+1][6];
            if(left != right) {
                check(idx+1, dir == 1 ? -1 : 1);
            }
        }
        if(idx != 0) {
            // 왼쪽 확인
            int left = wheels[idx-1][2];
            int right = wheels[idx][6];
            if(left != right) {
                check(idx-1, dir == 1 ? -1 : 1);
            }
        }

        rotation(idx, dir);

    }

    private static void rotation(int idx, int dir){
        // 시계방향 회전
        if(dir == 1) {
            int tmp = wheels[idx][7];
            for(int i = wheels[idx].length-1 ; i > 0 ; i--) {
                wheels[idx][i] = wheels[idx][i-1];
            }
            wheels[idx][0] = tmp;
            return;
        }

        // 반시계방향 회전
        int tmp = wheels[idx][0];
        for(int i = 1 ; i < wheels[idx].length; i++) {
            wheels[idx][i-1] = wheels[idx][i];
        }
        wheels[idx][7] = tmp;
    }
}
