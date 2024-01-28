import java.io.*;
import java.util.*;

public class Main {

    private static List<List<Integer>> tree;
    private static int[] apples;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        tree = new ArrayList<>();
        apples = new int[n];

        for (int i = 0; i < n; i++) {
            tree.add(new ArrayList<>());
        }

        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int parent = Integer.parseInt(st.nextToken());
            int child = Integer.parseInt(st.nextToken());
            tree.get(parent).add(child);
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            apples[i] = Integer.parseInt(st.nextToken());
        }

        int answer = bfs(0, k);
        bw.write(answer + "");
        bw.flush();
        bw.close();
    }

    public static int bfs(int root, int k) {
        Deque<Node> q = new ArrayDeque<>();
        q.add(new Node(root, 0));
        int apple = 0;

        while (!q.isEmpty()) {
            Node node = q.poll();

            if (node.dist <= k) {
                apple += apples[node.num];
            } else {
                continue;
            }

            for (int child : tree.get(node.num)) {
                q.add(new Node(child, node.dist + 1));
            }
        }
        return apple;
    }
}

class Node {
    int num;
    int dist;

    public Node(int num, int dist) {
        this.num = num;
        this.dist = dist;
    }
}
