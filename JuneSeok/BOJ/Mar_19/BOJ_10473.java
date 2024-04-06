import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    static final int INF = Integer.MAX_VALUE;
    static List<Edge>[] list;
    static float[] weights;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        float x = Float.parseFloat(st.nextToken());
        float y = Float.parseFloat(st.nextToken());
        Vertex startV = new Vertex(x, y);

        st = new StringTokenizer(br.readLine());
        x = Float.parseFloat(st.nextToken());
        y = Float.parseFloat(st.nextToken());
        Vertex endV = new Vertex(x, y);

        int N = Integer.parseInt(br.readLine());
        list = new ArrayList[N + 2];
        weights = new float[N + 2];
        Vertex[] vertices = new Vertex[N + 2];
        vertices[0] = startV;
        vertices[N + 1] = endV;

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            x = Float.parseFloat(st.nextToken());
            y = Float.parseFloat(st.nextToken());
            vertices[i] = new Vertex(x, y);
        }

        for (int i = 0; i <= N + 1; i++) {
            weights[i] = INF;
            list[i] = new ArrayList<>();
            for (int j = 0; j <= N + 1; j++) {
                if (i == j) continue;
                float dist = getDist(vertices[i], vertices[j]);
                list[i].add(new Edge(j, dist, 0));
            }
        }

        dijkstra(N);
        System.out.println(weights[N + 1]);
    }

    private static void dijkstra(int n) {
        PriorityQueue<Edge> pq = new PriorityQueue<>((o1, o2) -> Float.compare(o1.sec, o2.sec));
        weights[0] = 0;
        pq.add(new Edge(0, 0, 0));

        while (!pq.isEmpty()) {
            Edge e = pq.poll();

            int cur = e.target;
            float sec = e.sec;

            if (weights[cur] < sec) continue;

            for (Edge edge : list[cur]) {
                int next = edge.target;
                float dist = edge.dist;

                if(cur > 0 && cur <= n){
                    if(dist >= 50) {
                        float nSec = sec + 2 + (dist - 50) / 5.0f;

                        if (weights[next] > nSec) {
                            weights[next] = nSec;
                            pq.add(new Edge(next, 0, nSec));
                        }
                    }
                    else{
                        float nSec = sec + 2 + (50 - dist) / 5.0f;

                        if (weights[next] > nSec) {
                            weights[next] = nSec;
                            pq.add(new Edge(next, 0, nSec));
                        }
                    }
                }

                //오직 걸어간다
                float nSec = sec + (dist / 5.0f);
                if(weights[next] > nSec){
                    weights[next] = nSec;
                    pq.add(new Edge(next, 0, nSec));
                }
            }
        }
    }

    private static float getDist(Vertex vertex, Vertex vertex1) {
        return (float) Math.sqrt( Math.pow(vertex.y - vertex1.y, 2) + Math.pow(vertex.x - vertex1.x, 2) );
    }
}

class Vertex {
    float x, y;
    public Vertex(float x, float y) {
        this.x = x;
        this.y = y;
    }
}

class Edge {
    int target;
    float dist, sec;
    public Edge(int target, float dist, float sec) {
        this.target = target;
        this.dist = dist;
        this.sec = sec;
    }
}