import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int[][] fishBowls;
    static int[] bowlCounts; //위에 있는 어항의 개수
    static int N;
    static int K;
    static int maxFish = -1;
    static int minFish = 10001;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        fishBowls = new int[1][N];
        bowlCounts = new int[N];

        //초기 어항 초기화
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            fishBowls[0][i] = Integer.parseInt(st.nextToken());
            bowlCounts[i] = 1;
            maxFish = Math.max(maxFish, fishBowls[0][i]);
            minFish = Math.min(minFish, fishBowls[0][i]);
        }

//		System.out.println(Arrays.toString(fishBowls[0]));

        int answer = 0;

        while ((maxFish - minFish) > K) {

            addFishOfBowls();
            upBowl();
            adjustFish();
            straightBowls();
            segmentAndSpin();
            segmentAndSpin();
            adjustFish();
            straightBowls();

            maxFish = -1;
            minFish = 10001;
            for (int i = 0; i < N; i++) {
                maxFish = Math.max(maxFish, fishBowls[0][i]);
                minFish = Math.min(minFish, fishBowls[0][i]);
            }
            ++answer;
        }

        System.out.println(answer);
    }

    // N / 2로 분할 후 회전
    public static void segmentAndSpin() {
        int height = fishBowls.length;
        int width = fishBowls[0].length / 2;
        int[][] left = new int[height][width];
        int[][] right = new int[height][width];

        for (int i = 0; i < height; i++) {
            System.arraycopy(fishBowls[i], 0, left[i], 0, width);
        }

        for (int i = 0; i < height; i++) {
            System.arraycopy(fishBowls[i], width, right[i], 0, width);
        }

        left = spin(left, height, width, false);
        left = spin(left, width, height, false);

        fishBowls = new int[height * 2][width];
        for (int i = 0; i < height; i++) {
            System.arraycopy(left[i], 0, fishBowls[i], 0, width);
        }

        for (int i = 0; i < height; i++) {
            System.arraycopy(right[i], 0, fishBowls[height + i], 0, width);
        }
    }

    //가장 적게 들어있는 어항에 물고기 추가
    public static void addFishOfBowls() {
        for (int i = 0; i < N; i++) {
            if (minFish == fishBowls[0][i])
                fishBowls[0][i]++;
        }
    }

    //물고기 수 조절
    public static void adjustFish() {
        int[][] delta = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int height = fishBowls.length;
        int[][] copyBowls = new int[height][];
        boolean[][] visited = new boolean[height][];

        for (int i = 0; i < height; i++) {
            copyBowls[i] = new int[fishBowls[i].length];
            visited[i] = new boolean[fishBowls[i].length];
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < fishBowls[i].length; j++) {
                for (int k = 0; k < 4; k++) {
                    int tx = j + delta[k][0];
                    int ty = i + delta[k][1];

                    try {
                        if (visited[ty][tx]) continue;

                        int d = ((Math.abs(fishBowls[i][j] - fishBowls[ty][tx])) / 5);
                        if (d == 0) continue;
                        if (fishBowls[i][j] > fishBowls[ty][tx]) {
                            copyBowls[ty][tx] += d;
                            copyBowls[i][j] -= d;
                        } else if (fishBowls[i][j] < fishBowls[ty][tx]) {
                            copyBowls[i][j] += d;
                            copyBowls[ty][tx] -= d;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        continue;
                    }
                }
                visited[i][j] = true;
            }
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < fishBowls[i].length; j++) {
                fishBowls[i][j] += copyBowls[i][j];
            }
        }
    }

    //일렬로 나열함
    public static void straightBowls() {
        int[][] copyBowls = new int[1][N];
        int idx = 0;
        int height = fishBowls.length - 1;
        int width = fishBowls[height].length;
        int upWidth = fishBowls[0].length;

        for (int i = 0; i < width; i++) {
            copyBowls[0][idx++] = fishBowls[height][i];

            if (i < upWidth) {
                for (int j = height - 1; j >= 0; j--)
                    copyBowls[0][idx++] = fishBowls[j][i];
            }
        }
        fishBowls = copyBowls;
        bowlCounts = new int[N];
        Arrays.fill(bowlCounts, 1);
    }

    //어항 올리기
    public static void upBowl() {
        while (true) {
            int changedN = bowlCounts.length;
            int startIdx = changedN - 1; //위에 어항이 없는 인덱스의 시작
            int height = bowlCounts[0]; //현재 어항의 최대 높이
            int[][] copyBowls = new int[height + 1][];

            //위에 어항이 없는 어항의 첫 인덱스 찾기
            for (int i = changedN - 1; i >= 0; i--) {
                if (bowlCounts[i] > 1) continue;
                startIdx = i;
            }
            if (startIdx == 0) startIdx = 1;

            //어항을 올리고
            for (int i = 0; i < height; i++) {
                copyBowls[i] = new int[startIdx];
                System.arraycopy(fishBowls[i], 0, copyBowls[i], 0, startIdx);
            }

            //회전 시킨 후
            copyBowls = spin(copyBowls, height, startIdx, true);

            //바닥에 어항을 놓음
            int floorN = changedN - startIdx; //바닥의 길이
            copyBowls[startIdx] = new int[floorN];
            System.arraycopy(fishBowls[height - 1], startIdx, copyBowls[startIdx], 0, floorN);

            //위에 있는 어항의 너비가 아래 있는 어항의 너비보다 크면
            if (copyBowls[0].length > copyBowls[startIdx].length) {
                break;
            }

            bowlCounts = new int[floorN];
            Arrays.fill(bowlCounts, 1);
            Arrays.fill(bowlCounts, 0, height, copyBowls.length);


            fishBowls = copyBowls;
        }
    }

    //배열 90도 회전
    public static int[][] spin(int[][] copyBowls, int height, int width, boolean flag) {
        int[][] tmp;
        if (flag) //어항을 올릴 때
            tmp = new int[width + 1][];
        else //어항을 그냥 회전시킬 때
            tmp = new int[width][];

        for (int i = 0; i < width; i++) {
            tmp[i] = new int[height];
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tmp[i][j] = copyBowls[height - j - 1][i];
            }
        }

        return tmp;
    }
}