import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	static Set<Character> learnSet;
	static Set<Character> testWordSet;
	static char[][] arr;
	static int result = Integer.MIN_VALUE;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());

		if (k < 5) {
			System.out.println(0);
			return;
		} else if (k == 26) {
			System.out.print(n);
			return;
		}

		learnSet = new HashSet<Character>();
		learnSet.add('a');
		learnSet.add('n');
		learnSet.add('t');
		learnSet.add('i');
		learnSet.add('c');

		// 테스트 단어에 들어있는 알파펫 중복제거된 버전
		testWordSet = new HashSet<Character>();

		arr = new char[n][];

		for (int i = 0; i < n; i++) {
			String word = br.readLine();
			arr[i] = word.toCharArray();
			for (int j = 0; j < word.length(); j++) {
				testWordSet.add(word.charAt(j));
			}
		}

		// 매개변수: 배워야 할 남은 갯수
		dfs(k - 5, 1);
		System.out.println(result);
	}

	private static void dfs(int learnCount, int iStart) {
		// 배울 단어가 0개인 경우 (다배움. 단어 몇개인지 체크)
		if (learnCount == 0) {
			int sum = 0;
			for (int i = 0; i < arr.length; i++) {
				int count = 0;
				for (int j = 4; j < arr[i].length - 4; j++) {
					if (learnSet.contains(arr[i][j])) {
						count++;
					}
				}
				if (count == arr[i].length - 8) {
					sum++;
				}
			}

			result = Math.max(result, sum);
			return;
		}

		char a = 'a' - 1;
		// 모든 알파벳 하나씩 넣어보기
		for (int i = iStart; i <= 26; i++) {
			char next = (char) (a + i);
			// 테스트 단어들에는 포함되어 있지만 아직 추가되지 않은 알파벳 일 때.
			if (testWordSet.contains(next) && !learnSet.contains(next)) {
				learnSet.add(next);
				dfs(learnCount - 1, i + 1);
				learnSet.remove(next);
			}
		}
		if (result == Integer.MIN_VALUE) {
			// 넣을 단어가 하나도 없는 경우 여기로 빠져나옴. 
			//시작부터 모든 테스트 단어들이 배운 알파벳일 꼉우 여기로 옴
			//출력: 길이
			System.out.println(arr.length);
			System.exit(0);
		}
	}
}
