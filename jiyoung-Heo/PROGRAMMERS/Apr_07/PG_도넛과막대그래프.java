import java.util.*;
import java.io.*;

class Solution {
    static int[] outCount;
    static int[] inCount;
    static  int[] answer;
    static List<Integer>[] listArr;
    public int[] solution(int[][] edges) {
        answer = new int[] {0,0,0,0};
        Set<Integer> set = new HashSet<>();
        
        for(int i = 0 ; i<edges.length; i++){
            set.add(edges[i][0]);
            set.add(edges[i][1]);
        }
        Integer[] temp = set.toArray(new Integer[0]);
        Arrays.sort(temp);
        
        //간선정보저장할 배열
        listArr = new ArrayList[temp[temp.length-1]+1];
        for(int i = 0; i<listArr.length; i++){
            listArr[i] = new ArrayList<>();
        }
        //진출차수가 2개 이상이고 있고 진입차수는 없는것이 추가한 노드이다. 
        outCount = new int[listArr.length];
        inCount = new int[listArr.length];
        //0번째는 사용하지 않는다. 
        for(int i = 0 ; i<edges.length; i++){
            //2 1 2에서 1로 간다. 1 진입차수 ++ 2 진출차수 ++
            outCount[edges[i][0]]++;
            inCount[edges[i][1]]++;
            listArr[edges[i][0]].add(edges[i][1]);
        }
        //1. 추가 노드 찾기
        answer[0] = findNode();
        
        //해당 노드랑 연결된 간선이 있다면 진출/진입 지워주기
        deleteEdge(edges);
        
        // 연결성 체크
        // 도넛 모양 그래프 찾기
        // 조건: 진출 1, 진입 1의 형태
        
        // 막대 모양 그래프 찾기
        // 조건: 진출 1 진입 1의 형태
        int lineCount =0;
        int dounutCount = 0;
        int eightCount = 0;
        boolean[] visited = new boolean[outCount.length];        
        boolean[] checked = new boolean[outCount.length];
        for(int i = 1 ; i < outCount.length ; i++){
            if(outCount[i] == 2){
                checked[i] = true;
                eightCount ++;
                continue;
            }

            if(outCount[i] == 0 && i != answer[0] && set.contains(i)){
                checked[i] = true;
                lineCount++;
                continue;
            }
            
        }
        for(int i = 1 ; i < outCount.length ; i++){
            Arrays.fill(visited,false);
            
            if(outCount[i] == 1 && !visited[i]){
                int currNode = i;
                while(!visited[currNode] && !checked[currNode]){
                    visited[currNode] = true;
                    currNode = listArr[currNode].get(0);
                }
                if(!checked[currNode]){
                    dounutCount++;     
                }
                checked[i] = true;
            }
        }
        
        answer[1] = dounutCount;
        answer[2] = lineCount;
        answer[3] = eightCount;
        
        return answer;
    }
    int findNode(){
        for(int i = 1; i<outCount.length; i++){
            if(outCount[i] >=2 && inCount[i] == 0){
                return i;
            }
        }
        return -1;
    }
    void deleteEdge(int[][] edges){
        for(int i = 0 ; i<edges.length; i++){
            if(edges[i][0] == answer[0]){
                outCount[edges[i][0]]--;    
                inCount[edges[i][1]]--;
                listArr[edges[i][0]].remove((Object)edges[i][1]);
            }
        }
    }
}
