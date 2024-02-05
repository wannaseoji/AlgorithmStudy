import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ_20057 {
    private static long sum,remain = 0;
    private static long[][] arr;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        arr = new long[N][N];
        for (int i = 0; i < N; i++) {
            arr[i] = Arrays.stream(br.readLine().split(" "))
                    .mapToLong(Long::parseLong)
                    .toArray();
        }

        int[] dx = {0, 1, 0, -1};
        int[] dy = {-1, 0, 1, 0};

        int[] move = new int[N * 2 - 1];
        move[move.length-1] = N;
        int n = 1;
        for (int i = 0; i < move.length - 1; i+=2) {
            move[i] = n;
            move[i+1] = n++;
        }

        int r = N / 2;
        int c = r;

        int direct_idx = 0;
        int move_idx = 0;

        while (move[move.length-1]>1) {
            r += dx[direct_idx];
            c += dy[direct_idx];
            long sand = arr[r][c];
            arr[r][c] = 0;
            remain = sand;
            switch (direct_idx) {
                case 0:
                    left(r, c, sand);
                    if(isVaild(r, c-1)) {
                        arr[r][c-1] += remain;
                    } else {
                        sum += remain;
                    }
                    break;
                case 1:
                    down(r, c, sand);
                    if(isVaild(r+1, c)) {
                        arr[r+1][c] += remain;
                    } else {
                        sum += remain;
                    }
                    break;
                case 2:
                    right(r, c, sand);
                    if(isVaild(r, c+1)) {
                        arr[r][c+1] += remain;
                    } else {
                        sum += remain;
                    }
                    break;
                case 3:
                    up(r, c, sand);
                    if(isVaild(r-1, c)) {
                        arr[r-1][c] += remain;
                    } else {
                        sum += remain;
                    }
                    break;
            }
            move[move_idx]--;

            if (move[move_idx] == 0) {
                move_idx++;
                direct_idx = (direct_idx + 1) % 4;
            }
        }

        System.out.println(sum);
    }

    private static void left(int r, int c, long sand) {
        int[] dx = { -1, 1, 0, -1, 1, -2, -1, 1, 2 };
        int[] dy = { -1, -1, -2, 1, 1, 0, 0, 0, 0 };
        double[] weight = { 0.1, 0.1, 0.05, 0.01, 0.01, 0.02, 0.07, 0.07, 0.02 };
        sumSand(r, c, sand, dx, dy, weight);
    }

    private static void down(int r, int c, long sand) {
        int[] dx = { -1, -1, 0, 0, 0, 0, 1, 1, 2 };
        int[] dy = { -1, 1, -1, 1, -2, 2, -1, 1, 0 };
        double[] weight = { 0.01, 0.01, 0.07, 0.07, 0.02, 0.02, 0.1, 0.1, 0.05 };
        sumSand(r, c, sand, dx, dy, weight);
    }

    private static void up(int r, int c, long sand) {
        int[] dx = { -2, -1, -1, 0, 0, 0, 0, 1, 1 };
        int[] dy = { 0, -1, 1, -2, -1, 1, 2, -1, 1 };
        double[] weight = { 0.05, 0.1, 0.1, 0.02, 0.07, 0.07, 0.02, 0.01, 0.01 };
        sumSand(r, c, sand, dx, dy, weight);
    }

    private static void right(int r, int c, long sand) {
        int[] dx = { -2, -1, -1, -1, 0, 1, 1, 1, 2 };
        int[] dy = { 0, -1, 0, 1, 2, -1, 0, 1, 0 };
        double[] weight = { 0.02, 0.01, 0.07, 0.1, 0.05, 0.01, 0.07, 0.1, 0.02 };
        sumSand(r, c, sand, dx, dy, weight);
    }

    private static void sumSand(int r, int c, long sand, int[] dx, int[] dy, double[] weight) {
        int tmp;
        for(int i=0; i<dx.length; i++) {
            tmp = (int)(sand*weight[i]);
            remain -= tmp;
            int nr = r+dx[i];
            int nc = c+dy[i];
            if(!isVaild(nr, nc)) {
                sum += tmp;
                continue;
            }
            arr[nr][nc] += tmp;
        }
    }

    private static boolean isVaild(int nr, int nc) {
        return nr>=0 && nc>=0 && nr<arr.length && nc<arr.length;
    }
}
