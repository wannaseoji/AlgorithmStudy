import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class 인간대포 {

    static class Position {
        double r, c;

        public Position(double r, double c) {
            this.r = r;
            this.c = c;
        }
    }

    static class Vertex implements Comparable<Vertex> {
        int dest;
        float time;

        public Vertex(int dest, float time) {
            this.dest = dest;
            this.time = time;
        }

        @Override
        public int compareTo(Vertex o) {
            return Float.compare(time, o.time);
        }
    }

    static Position start, end;
    static boolean[] check;
    static float[] times;
    static Position[] positions;
    static List<Vertex>[] relation;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String[] input = br.readLine().split(" ");
        float r = Float.parseFloat(input[0]);
        float c = Float.parseFloat(input[1]);
        start = new Position(r, c);

        input = br.readLine().split(" ");
        r = Float.parseFloat(input[0]);
        c = Float.parseFloat(input[1]);
        end = new Position(r, c);

        int N = Integer.parseInt(br.readLine());
        check = new boolean[N + 2];
        times = new float[N + 2];
        positions = new Position[N + 2];
        relation = new ArrayList[N + 2];
        for (int i = 0; i < N + 2; i++) {
            relation[i] = new ArrayList<>();
        }

        positions[0] = start;
        positions[N + 1] = end;
        for (int i = 1; i < N + 1; i++) {
            input = br.readLine().split(" ");
            r = Float.parseFloat(input[0]);
            c = Float.parseFloat(input[1]);
            positions[i] = new Position(r, c);
        }

        for (int i = 1; i < N + 2; i++) {
            relation[0].add(new Vertex(i, (float)(getDist(positions[0], positions[i]) / 5.0)));
        }

        for (int i = 1; i < N + 2; i++) {
            for (int j = 0; j < N + 2; j++) {
                float dist = getDist(positions[i], positions[j]);
                relation[i].add(
                        new Vertex(j, (float)Math.min(dist / 5.0, Math.abs(dist - 50) / 5.0 + 2)));
            }
        }

        dijkstra(0);
        bw.write(String.valueOf(times[N + 1]));
        bw.close();
        br.close();
    }

    static void dijkstra(int start) {
        PriorityQueue<Vertex> pq = new PriorityQueue<>();
        Arrays.fill(times, Integer.MAX_VALUE);
        pq.add(new Vertex(start, 0));
        times[start] = 0;

        while (!pq.isEmpty()) {
            Vertex vertex = pq.poll();
            int dest = vertex.dest;
            if (check[dest]) continue;
            check[dest] = true;
            for (Vertex next : relation[dest]) {
                if (times[next.dest] >= times[dest] + next.time) {
                    times[next.dest] = times[dest] + next.time;
                    pq.add(new Vertex(next.dest, times[next.dest]));
                }
            }
        }
    }

    static float getDist(Position a, Position b) {
        return (float)Math.sqrt(Math.pow(a.r - b.r, 2) + Math.pow(a.c - b.c, 2));
    }
}
