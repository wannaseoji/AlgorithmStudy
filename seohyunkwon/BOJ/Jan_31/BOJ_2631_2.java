package Jan_31;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
// 이분탐색 LIS
public class BOJ_2631_2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        int[] children = new int[N];
        List<Integer> list = new ArrayList<>();
        list.add(Integer.MIN_VALUE);

        for (int i = 0; i < N; i++) {
            children[i] = Integer.parseInt(br.readLine());
        }

        for (int i = 0; i < N; i++) {
            int now = list.get(list.size()-1);
            if (children[i] > now) {
                list.add(children[i]);
                continue;
            }

            int left = 0;
            int right = list.size() - 1;

            while (left < right) {
                int mid = (left + right) / 2;
                if (list.get(mid) >= children[i]) {
                    right = mid;
                    continue;
                }
                left = mid + 1;
            }
            list.set(right, children[i]);
        }
        System.out.println(N-(list.size()-1));
    }
}