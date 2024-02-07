import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class 드래곤커브 {

    static boolean[][] map = new boolean[101][101];
    static int[][] delta = {{0, 1}, {-1, 0}, {0, -1}, {1, 0}}; //우, 상, 좌, 하

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());

        //커브 그리기
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int g = Integer.parseInt(st.nextToken());
            makeCurve(y, x, d, g);
        }

        StringBuilder sb = new StringBuilder(calcCurveInSquare() + "");
        System.out.println(sb);
    }

    //1x1인 정사각형의 네 꼭짓점이 모두 드래곤 커브의 일부인 개수 계산
    public static int calcCurveInSquare() {
        int count = 0;
        for (int i = 0; i <= 99; i++) {
            for (int j = 0; j <= 99; j++) {
                if (map[i][j] && map[i + 1][j] && map[i][j + 1] && map[i + 1][j + 1]) {
                    ++count;
                }
            }
        }
        return count;
    }

    //커브 생성
    public static void makeCurve(int y, int x, int d, int g) {
        List<Integer> list = new ArrayList<>();
        list.add(d);
        int idx = 1;
        map[y][x] = true;
        //0세대는 먼저 만들기
        y += delta[d][0];
        x += delta[d][1];
        map[y][x] = true;

        for (int i = 1; i <= g; i++) {
            int end = (int) Math.pow(2, i); //세대 당 커브의 끝

            //커브의 방향은 계속 뒤에서 앞 순으로 +1 되어짐
            for (int j = list.size() - 1; j >= 0; j--) {
                int dir = (list.get(j) + 1) % 4;
                list.add(dir);
            }

            //커브 생성후 마저 그리기
            for (int j = idx; j < end; j++) {
                int dir = list.get(j);
                y += delta[dir][0];
                x += delta[dir][1];
                map[y][x] = true;
            }
            idx = end;
        }
    }
}
