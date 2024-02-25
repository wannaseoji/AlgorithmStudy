import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int N;
    static int[] people;
    static List<Integer>[] adjList;
    static boolean[] isSelected;
    static boolean[] visited;
    static int answer = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        people = new int[N + 1];
        adjList = new ArrayList[N + 1];
        isSelected = new boolean[N + 1];

        //인접리스트 초기화
        for (int i = 0; i <= N; i++) {
            adjList[i] = new ArrayList<>();
        }

        //인구 초기화
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            people[i] = Integer.parseInt(st.nextToken());
        }

        //인접 구역 정보 초기화
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            int m = Integer.parseInt(st.nextToken());
            for (int j = 0; j < m; j++) {
                int n = Integer.parseInt(st.nextToken());
                adjList[i].add(n);
            }
        }

        subSet(1);

        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }

    //인접구역 확인
    public static int dfs(int start, Set<Integer> set, int depth) {

        visited[start] = true;
        int depthSum = 1;
        for (int adj : adjList[start]) {
            if (set.contains(adj) && !visited[adj])
                depthSum += dfs(adj, set, depth + 1);
        }
        return depthSum;
    }

    //부분집합 생성
    public static void subSet(int depth) {
        if (depth == N + 1) {
            Set<Integer> set1 = new HashSet<Integer>();
            Set<Integer> set2 = new HashSet<Integer>();
            int areaA = 0;
            int areaB = 0;
            int startA = -1;
            int startB = -1;

            for (int i = 1; i <= N; i++) {
                if (isSelected[i]) {
                    set1.add(i);
                    areaA += people[i];
                    if (startA == -1) startA = i;
                }
                else {
                    set2.add(i);
                    areaB += people[i];
                    if (startB == -1) startB = i;
                }
            }
            if (set1.isEmpty() || set2.isEmpty()) return;

            visited = new boolean[N + 1];
            int size1 = dfs(startA, set1, 0);
            int size2 = dfs(startB, set2, 0);
            if (size1 == set1.size() && size2 == set2.size()) {
                answer = Math.min(answer, Math.abs(areaA - areaB));
            }
            return;
        }

        isSelected[depth] = true;
        subSet(depth + 1);
        isSelected[depth] = false;
        subSet(depth + 1);
    }
}