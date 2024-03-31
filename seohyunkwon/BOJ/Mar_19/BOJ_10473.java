import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 출발지점
        StringTokenizer st = new StringTokenizer(br.readLine());
        Cannon cur = new Cannon(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()));
        // 도착 지점
        st = new StringTokenizer(br.readLine());
        Cannon target = new Cannon(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()));
        
        // 대포의 개수
        int n = Integer.parseInt(br.readLine());
        
        // 대포 배열, 방문 배열, 거리 배열
        Cannon[] Cannons = new Cannon[n + 2];
        boolean[] visit = new boolean[n + 2];
        double[] time = new double[n + 2];
        double[][] dist = new double[n+2][n+2];
        Arrays.fill(time, Double.MAX_VALUE);
        
        Cannons[n] = cur;
        Cannons[n + 1] = target;
        
        time[n] = 0;
        
        // 대포 입력
        for(int i = 0; i <n; i++) {
            st = new StringTokenizer(br.readLine());
            Cannons[i] = new Cannon(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()));
        }
        
        // 거리 입력
        for(int i = 0; i <dist.length; i++) {
        	for(int j = 0; j<dist.length; j++) {
        		if(i == j) continue;
        		dist[i][j] = getDist(Cannons[i], Cannons[j]);
        	}
        }
        
        // 다익스트라 시작
        for(int i = 0; i < Cannons.length; i++) {
        	
            double min_value = Double.MAX_VALUE; 
            int min_idx = -1;
            
            // 최소 시간 찾기
            for(int j = 0; j < Cannons.length; j++) {
                if(!visit[j] && min_value > time[j]) {
                    min_idx = j;
                    min_value = time[j];
                }
            }
            
//            System.out.println("min_idx = "+min_idx);
            
            if(min_idx == -1 || min_idx == n+1) break;
            visit[min_idx] = true;
            
            // 최소 거리 설정
            for(int j = 0; j < Cannons.length; j++) {
                if(min_idx == j) continue;
                
                // 직선 거리 / 속력
                if(time[j] > time[min_idx] +  dist[min_idx][j] / 5.0) {
                    time[j] = time[min_idx] + dist[min_idx][j] / 5.0;
                }
                
                // 대포 사용 시 ..
                // dist가 50보다 작거나 같으면 현재 시간에서 + 2초
                if(min_idx >= n) continue;
               
                
                // dist가 50보다 크면 현재 시간 + 2초 + 나머지 거리 / 속력
                if(dist[min_idx][j] > 50) {
                    time[j] = Math.min(time[j],time[min_idx] + (dist[min_idx][j] - 50) / 5.0 + 2.0);
                }else 
                	time[j] = Math.min(time[j],time[min_idx] + (50 - dist[min_idx][j]) / 5.0 + 2.0);
            }
            
//            System.out.println(Arrays.toString(time));
        }
        
        System.out.println(time[n+1]);
        
    }
    static class Cannon {
        double x,y;
        
        public Cannon(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
    
    static double getDist(Cannon c1, Cannon c2) {
        return Math.sqrt(Math.pow(Math.abs(c1.x - c2.x), 2) + Math.pow(Math.abs(c1.y - c2.y), 2));
    }
}
