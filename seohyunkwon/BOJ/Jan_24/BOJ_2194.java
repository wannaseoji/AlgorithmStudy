package Jan_24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_2194 {

    private static int[][] map;
    private static boolean[][] visited;
    private static int N, M, A,B;
    private static Unit startUnit, targetUnit;
    private static int result = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        // 입력
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        // map & visited 배열 선언
        map =  new int[N+1][M+1];
        visited = new boolean[N+1][M+1];

        // map 값 입력받기
        for(int i=0; i<K; i++) {
            st = new StringTokenizer(br.readLine());
            int X = Integer.parseInt(st.nextToken());
            int Y = Integer.parseInt(st.nextToken());
            map[X][Y] = -1;
        }

        // start Node와 target 노드 입력
        int[] start = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        int[] target = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        // start 유닛 & target 유닛 생성
        startUnit = new Unit(new Node(start[0], start[1]), 0);
        targetUnit = new Unit(new Node(target[0], target[1]), 0);

        if(!startUnit.isMoveable()) {
            System.out.println(-1);
            return;
        }

        // bfs 실행
        bfs(startUnit);

        // 결과 출력
        System.out.println(result == Integer.MAX_VALUE ? -1 : result);
    }

    private static void bfs(Unit startUnit) {

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        Queue<Unit> queue = new LinkedList<>();
        queue.add(startUnit);
        startUnit.checkVisit();

        while(!queue.isEmpty()) {
            Unit unit = queue.poll();
            // 타겟 유닛에 도달한 경우 거리의 최솟값 산정
            if(unit.nodes[0].x == targetUnit.nodes[0].x && unit.nodes[0].y == targetUnit.nodes[0].y) {
                result = Math.min(result, unit.dist);
                return;
            }

            // 탐색
            for(int i=0; i<dx.length; i++) {
                int nx = unit.nodes[0].x + dx[i];
                int ny = unit.nodes[0].y + dy[i];

                Unit tmp = new Unit(new Node(nx, ny), unit.dist + 1);

                if(tmp.isMoveable()) {
                    tmp.checkVisit();
                    queue.add(tmp);
                }

            }
        }
    }

    // 유닛 클래스
    private static class Unit {
        Node[] nodes;
        int dist;

        // 유닛의 각 모서리 초기화
        public Unit(Node node1, int dist) {
            nodes = new Node[4];
            nodes[0] = node1;
            nodes[1] = new Node(node1.x, node1.y+B-1);
            nodes[2] = new Node(node1.x+A-1, node1.y);
            nodes[3] = new Node(node1.x+A-1, node1.y+B-1);
            this.dist = dist;
        }

        // 이동 가능한 범위인지 체크
        public boolean isMoveable() {

            for(int i=0; i<nodes.length; i++) {
                // 배열의 범위 체크
                if(nodes[i].x < 1 || nodes[i].y < 1 || nodes[i].x > N || nodes[i].y > M)
                    return false;
            }
            if(visited[this.nodes[0].x][this.nodes[0].y])
                return false;
            // 장애물 탐색
            for(int i=0; i<A; i++) {
                for(int j=0; j<B; j++) {
                    if(map[nodes[0].x+i][nodes[0].y+j] == -1) return false;
                }
            }
            return true;
        }

        public void checkVisit() {
            visited[nodes[0].x][nodes[0].y] = true;
        }

    }

    // 노드 클래스
    private static class Node {
        int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
