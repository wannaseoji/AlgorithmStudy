import java.util.*;

class Solution {
    
    int maxNodeCnt;
    int[] in;
    int[] out;
    int[] answer = new int[4];
    
    public int[] solution(int[][] edges) {
        init(edges);
        answer[0] = searchStartVertex();
        answer[2] = calcMakdae();
        answer[3] = calcPalja();
        answer[1] = out[answer[0]] - (answer[2] + answer[3]);
        
        return answer;
    }
    
    //8자 세기
    public int calcPalja() {
        int cnt = 0;
        for (int i = 1; i < maxNodeCnt; i++) {
            if (in[i] >= 2 && out[i] >= 2) { //들어오는거, 나가는거 2개 이상
                cnt++;
            }
        }
        return cnt;
    }
    
    //막대 세기
    public int calcMakdae() {
        int cnt = 0;
        for (int i = 1; i < maxNodeCnt; i++) {
            if (in[i] >= 1 && out[i] == 0) { //들어오는거 1개이상 나가는거 0개
                cnt++;
            }
        }
        return cnt;
    }
    
    //초기화
    public void init(int[][] edges) {
        //제일 큰 노드 번호 찾기
        for (int[] edge : edges) {
            maxNodeCnt = Math.max(maxNodeCnt, Math.max(edge[0], edge[1]));
        }
        maxNodeCnt++;
        in = new int[maxNodeCnt];
        out = new int[maxNodeCnt];
        
        for (int[] edge : edges) {
            int a = edge[0];
            int b = edge[1];
            out[a]++;
            in[b]++;
        }
    }
    
    //시작점 찾기
    public int searchStartVertex() {
        for (int i = 1; i < maxNodeCnt; i++) {
            if (out[i] >= 2 && in[i] == 0) {
                return i;
            }
        }
        return -1;
    }
}