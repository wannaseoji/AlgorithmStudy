import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public class 게리맨더링 {

    static int N;
    static int[] population;
    static boolean[] select;
    static boolean[] visit;
    static List<Integer> areaA, areaB;
    static List<Integer>[] conn;

    static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        N = Integer.parseInt(br.readLine());
        population = new int[N];
        select = new boolean[N];
        conn = new ArrayList[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            conn[i] = new ArrayList<>();
            population[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int cnt = Integer.parseInt(st.nextToken());
            for (int j = 0; j < cnt; j++) {
                int area = Integer.parseInt(st.nextToken()) - 1;
                conn[i].add(area);
            }
        }

        dfs(0);
        if (min == Integer.MAX_VALUE) {
            bw.write(String.valueOf(-1));
        } else {
            bw.write(String.valueOf(min));
        }
        bw.close();
        br.close();
    }

    //areaA와 areaB로 나눈다
    static void dfs(int cnt) {
        if (cnt == N) {
            areaA = new ArrayList<>();
            areaB = new ArrayList<>();
            //true이면 areaA에, false이면 areaB에 추가한다
            for (int idx = 0; idx < N; idx++) {
                if (select[idx]) {
                    areaA.add(idx);
                } else {
                    areaB.add(idx);
                }
            }
            //두 리스트 중 하나라도 비어있다면 함수를 종료한다
            if (areaA.isEmpty() || areaB.isEmpty()) {
                return;
            }
            //두 리스트 모두 연결된 지역으로 구성되어 있는지 체크한다
            if (check(areaA) && check(areaB)) {
                getDiff();
            }
            return;
        }

        select[cnt] = true;
        dfs(cnt + 1);
        select[cnt] = false;
        dfs(cnt + 1);
    }

    //연결된 지역인지 체크한다
    static boolean check(List<Integer> area) {
        Deque<Integer> q = new ArrayDeque<>();
        visit = new boolean[N];
        visit[area.get(0)] = true;
        q.offer(area.get(0));

        int cnt = 1;
        while (!q.isEmpty()) {
            int curr = q.poll();
            for (int i = 0; i < conn[curr].size(); i++) {
                int next = conn[curr].get(i);
                if (area.contains(next) && !visit[next]) {
                    q.offer(next);
                    visit[next] = true;
                    cnt++;
                }
            }
        }

        return cnt == area.size();
    }

    //두 지역의 인구수 차이를 구한다
    static void getDiff() {
        int cntA = 0;
        int cntB = 0;
        for (int i = 0; i < N; i++) {
            if (select[i]) {
                cntA += population[i];
            } else {
                cntB += population[i];
            }
        }

        int diff = Math.abs(cntA - cntB);
        min = Math.min(min, diff);
    }
}
