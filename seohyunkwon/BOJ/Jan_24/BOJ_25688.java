package Jan_24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_25688 {
    private static int[][] board;
    private static boolean[][] board_visited;
    private static boolean[] number_visited;
    private static int[] dx = {-1, 1, 0, 0};
    private static int[] dy = {0, 0, -1, 1};
    private static int result;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        board = new int[5][5];
        board_visited = new boolean[5][5];
        number_visited = new boolean[7];
        number_visited[0] = true;
        result = Integer.MAX_VALUE;

        for(int i=0; i<5; i++) {
            board[i] = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        bfs(r, c);
//        System.out.println("number_visited = " + Arrays.toString(number_visited));
        System.out.println(result == Integer.MAX_VALUE?-1:result);
    }

    private static void bfs(int x, int y) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(x, y, 0));
        while(!queue.isEmpty()) {
            Node node = queue.poll();

            number_visited[board[node.x][node.y]] = true;

            boolean flag = true;
            for (int i = 1; i <= 6; i++) {
                if (!number_visited[i]) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                result = Math.min(result, node.depth);
                return;
            }

            board_visited[node.x][node.y] = true;

            for(int i = 0; i < dx.length; i++) {
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];

                if(nx < 0 || ny < 0 || nx >= board.length || ny >= board[0].length) continue;
                if(board[nx][ny] == -1 || board_visited[nx][ny] || number_visited[board[nx][ny]]) continue;
                queue.add(new Node(nx, ny, node.depth + 1));
            }
        }
    }

    private static class Node {
        int x, y, depth;
        Node(int x, int y, int depth) {
            this.x = x;
            this.y = y;
            this.depth = depth;
        }
    }
}