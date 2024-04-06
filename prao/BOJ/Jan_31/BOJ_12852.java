import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class BOJ_12852 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int N = Integer.parseInt(br.readLine());
        Point[] dp = new Point[N + 1];
        dp[1] = new Point(0, "1");

        for (int now = 2; now <= N; now++) {
            int cnt = Integer.MAX_VALUE;
            int next = -1;
            if (now % 3 == 0) {
                cnt = dp[now / 3].num + 1;
                next = now / 3;
            }
            if (now % 2 == 0) {
                if (dp[now / 2].num + 1 < cnt) {
                    cnt = dp[now / 2].num + 1;
                    next = now / 2;
                }
            }
            if (dp[now - 1].num + 1 < cnt) {
                cnt = dp[now - 1].num + 1;
                next = now - 1;
            }
            dp[now] = new Point(cnt, now + " " + dp[next].history);
        }
        bw.write(dp[N].num + "\n" + dp[N].history);
        bw.flush();
        bw.close();
        br.close();
    }
}

class Point {
    int num;
    String history;

    public Point(int num, String history) {
        this.num = num;
        this.history = history;
    }
}
