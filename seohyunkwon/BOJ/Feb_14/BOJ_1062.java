import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static int N, K, M;
    private static int max = Integer.MIN_VALUE;
    private static boolean[] visit;
    private static String[] words;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        M = K - 5;
        if(M < 0) {
            System.out.println(0);
            return;
        }
        visit = new boolean[26];

        char[] arr = {'a', 'n', 't', 'i', 'c'};

        for(int i = 0; i < arr.length; i++) {
            visit[arr[i]-'a'] = true;
        }

        words = new String[N];
        for(int i = 0; i < N; i++) {
            String tmp = br.readLine();
            words[i] = tmp.substring(4, tmp.length()-4);
        }

        backtracking(0, 0);

        System.out.println(max);

    }

    private static void backtracking(int idx, int depth){
        if(depth == M) {
            isVaild();
            return;
        }

        for(int i = idx; i < visit.length; i++) {
            if(visit[i]) continue;
            visit[i] = true;
            backtracking(i+1, depth + 1);
            visit[i] = false;
        }
    }

    private static void isVaild() {
        int count = 0;
        loop :
        for(String str : words) {
            for(char c : str.toCharArray()) {
                if(!visit[c-'a']) continue loop;
            }
            count++;
        }
        max = Math.max(count, max);
    }
}
