import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int K;
    static Set<Character> baseSet = new HashSet<>(List.of('a', 'n', 't', 'i', 'c'));
    static List<Character> list = new ArrayList<>();
    static Set<Character> tmpSet = new HashSet<>();
    static Set<Character> set = new HashSet<>();
    static String[] words;
    static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        words = new String[N];

        if (K < 5) { //읽을 수 있는 개수가 a, n, t, i, c보다 작으면
            System.out.println(answer);
            return;
        } else if (K == 26) { //알파벳 모두 읽을 수 있다면
            System.out.println(N);
            return;
        }

        for (int i = 0; i < N; i++) {
            String tmp = br.readLine();
            for (int j = 4; j < tmp.length() - 4; j++) {
                if (baseSet.contains(tmp.charAt(j))) continue;
                tmpSet.add(tmp.charAt(j));
            }
            words[i] = tmp;
        }

        if (tmpSet.size() < K - 5) {
            System.out.println(N);
            return;
        }
        for (char c : tmpSet) {
            list.add(c);
        }
        dfs(0, 0);

        System.out.println(answer);
    }

    public static void dfs(int depth, int idx) {
        if (depth == K - 5) {
            int cnt = 0;
            for (String word : words) {
                boolean flag = true;
                for (int i = 4; i < word.length() - 4; i++) {
                    char spell = word.charAt(i);
                    if (!baseSet.contains(spell) && !set.contains(spell)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) cnt++;
            }
            answer = Math.max(answer, cnt);
            return;
        }

        for (int i = idx; i < list.size(); i++) {
            char c = list.get(i);
            set.add(c);
            dfs(depth + 1, i + 1);
            set.remove(c);
        }
    }
}