import java.util.*;
import java.io.*;

public class BOJ_21938_SEO {
	static int N,M;
	static int[][] pixels;
	static boolean[][] visited;
	static int[] dx = {1,-1,0,0};
	static int[] dy = {0,0,-1,1};
	static int T; //threshold
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = br.readLine();
		StringTokenizer st = new StringTokenizer(input);
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		pixels = new int[N][M];
		visited = new boolean[N][M];
		
		for(int i=0; i<N; i++) {
			input = br.readLine();
			st = new StringTokenizer(input);
			for(int j=0; j<M; j++) {
				pixels[i][j] = (Integer.parseInt(st.nextToken())+Integer.parseInt(st.nextToken())+Integer.parseInt(st.nextToken()))/3;
			}
		}
		
		input = br.readLine();
		st = new StringTokenizer(input);
		T = Integer.parseInt(st.nextToken());
//		for(int i=0; i<N; i++) {
//			
//			for(int j=0; j<M; j++) {
//				System.out.print(pixels[i][j]+" "); 
//			}
//			System.out.println();
//		}
		///////////////////input/////////////////////
		int obj =0;
		for(int i =0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(!visited[i][j]&&pixels[i][j]>=T) {
					BFS(i,j);
					obj++;
					//System.out.println("BFS call");
				}
					
			}
		}
		System.out.println(obj);
		
	}
	static class Node {
		int y,x;
		
		Node(int y, int x){
			this.y = y;
			this.x = x;
			
		}
	}

	private static void BFS(int y, int x) {
		// TODO Auto-generated method stub
		ArrayDeque<Node> adq = new ArrayDeque<Node>();
		Node n = new Node(y,x);
		adq.add(n);
		
		while(!adq.isEmpty()) {
			n = adq.pop();
			int currX = n.x;
			int currY = n.y;
			//System.out.print("From :" +currY+" "+ currX);
			visited[currY][currX]=true;
			
			for(int i=0; i<4; i++) {
				int ny = currY+dy[i];
				int nx = currX+dx[i];
				
				if(ny<0||ny>=N||nx<0||nx>=M||visited[ny][nx]||pixels[ny][nx]<T) {
					continue;
				}
				//System.out.print(" to : "+ny+" "+nx);
				adq.push(new Node(ny,nx));
				visited[ny][nx] = true;
			}
		}
		System.out.println();
		
		
		
	}

}
