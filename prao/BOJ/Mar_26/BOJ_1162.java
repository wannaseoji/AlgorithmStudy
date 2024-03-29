import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class 도로포장 {

    static class Vertex implements Comparable<Vertex> {
        int end, count;
        long time;

        public Vertex(final int end, final long time, final int count) {
            this.end = end;
            this.time = time;
            this.count = count;
        }

        @Override
        public int compareTo(final Vertex o) {
            return (int)((time - o.time) % Integer.MAX_VALUE);
        }
    }

    static int N, M, K, s, e, t;
    static long[][] time;
    static List<Vertex>[] vertices;
    static Long min;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        time = new long[N + 1][K + 1];
        vertices = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            vertices[i] = new ArrayList<>();
            Arrays.fill(time[i], Long.MAX_VALUE);
        }
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            s = Integer.parseInt(st.nextToken());
            e = Integer.parseInt(st.nextToken());
            t = Integer.parseInt(st.nextToken());
            vertices[s].add(new Vertex(e, t, 0));
            vertices[e].add(new Vertex(s, t, 0));
        }
        dijkstra();
        min = Long.MAX_VALUE;
        for (long l : time[N]) {
            min = Math.min(min, l);
        }
        System.out.println(min);
    }

    static void dijkstra() {
        PriorityQueue<Vertex> pq = new PriorityQueue<>();
        pq.add(new Vertex(1, 0, 0));
        time[1][0] = 0; //서울(출발지)로 향하는 시간은 0으로 처리

        while (!pq.isEmpty()) {
            Vertex curr = pq.poll();
            //만약 현재 시간이 count만큼 도로를 포장한 시간보다 크다면 제외
            if (curr.time > time[curr.end][curr.count]) continue;
            //연결된 도로를 순회
            for (Vertex next : vertices[curr.end]) {
                //다음 시간 = 연결된 도로까지의 시간 + 현재까지의 시간
                long nt = next.time + curr.time;
                //다음 시간이 next의 도착점까지 count만큼 도로를 포장한 시간보다 작다면
                if (nt < time[next.end][curr.count]) {
                    //시간 업데이트
                    time[next.end][curr.count] = nt;
                    //도로를 pq에 추가
                    pq.add(new Vertex(next.end, nt, curr.count));
                }
                //현재 포장 횟수가 K보다 작고 현재 시간이 다음의 도착점까지 포장을 한번 더한 것보다 작다면
                if (curr.count < K && curr.time < time[next.end][curr.count + 1]) {
                    //시간 초기화
                    time[next.end][curr.count + 1] = curr.time;
                    //pq에 포장을 1회 추가하여 도로 추가
                    pq.add(new Vertex(next.end, curr.time, curr.count + 1));
                }
            }
        }
    }
}
