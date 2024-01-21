import java.io.*;
import java.util.*;

public class BOJ_1325_SEO {
    //신뢰하는 -> 방향을 가장 많이 얻은 마지막 노드가 정답.
    //relation[10000][100000] //memory over
    static int N,M;
    static ArrayList<Integer>[] relation;
    static boolean [] visited;
    static int nodeCnt[];
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
       
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        relation = new ArrayList[N+1];

        nodeCnt = new int[N+1];
        for(int i=0; i<=N; i++) {
            relation[i]=new ArrayList<>();
        }
        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());

            relation[n1].add(n2);

        }
        for(int i=1; i<=N; i++) {
        	visited = new boolean[N+1];// 매번 visited 초기화
            BFS(i);
        }

   
        
        /////////////////////////
        ArrayList<Integer> maxIndex = new ArrayList<>();
       
        int max = Integer.MIN_VALUE;
        for(int i=1;i<=N;i++){
            if(nodeCnt[i] > max){
                maxIndex.clear();
                max = nodeCnt[i];
                maxIndex.add(i);
            }
            else if(nodeCnt[i] == max){
                maxIndex.add(i);
            }
        }

        StringBuilder ans = new StringBuilder();
        for(int idx:maxIndex){
            ans.append(idx).append(" ");
        }

        bw.write(ans.toString());

        bw.flush();


        br.close();
        bw.close();


    }

    private static void BFS(int start) { //방문횟수 저장하면 정답
    	
        ArrayDeque<Integer> adq = new ArrayDeque<Integer>();
        adq.add(start);
        visited[start] = true;
        
        while(!adq.isEmpty()) {
            int currNode = adq.poll();
            
            for(int next : relation[currNode]) {
                //System.out.println(next);
            	if(visited[next])continue;
            	visited[next] = true;
                nodeCnt[next]+=1;
                adq.add(next);

            }
        }

    }

}