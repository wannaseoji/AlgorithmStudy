import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 가르침 {

    static int N, K, result;
    static String[] words;
    static boolean[] check = new boolean[26];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        words = new String[N];

        if(K < 5) {
            System.out.println(0);
            return;
        } else if(K == 26) {
            System.out.println(N);
            return;
        }
        //초기상태
        check['a' - 'a'] = true;
        check['n' - 'a'] = true;
        check['t' - 'a'] = true;
        check['i' - 'a'] = true;
        check['c' - 'a'] = true;

        for(int i = 0; i < N; i++) {
            String input = br.readLine();
            words[i] = input.substring(4,input.length()-4);
        }

        check(0,0);

        System.out.println(result);
    }

    static void check(int startIndex, int count) {
        if (count == K - 5) {
            getMaxCount();
            return;
        }

        for (int i = startIndex; i < 26; i++) {
            if (!check[i]) {
                check[i] = true;
                check(i, count + 1);
                check[i] = false;
            }
        }
    }

    static void getMaxCount() {
        int curr = 0;

        for (int i = 0; i < N; i++) {
            boolean canRead = true;

            for (int j = 0; j < words[i].length(); j++) {
                if (!check[words[i].charAt(j) - 'a']) {
                    canRead = false;
                    break;
                }
            }
            if (canRead) {
                curr++;
            }
        }

        result = Math.max(curr, result);
    }

}
