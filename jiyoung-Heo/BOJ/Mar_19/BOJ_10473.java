import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.StringTokenizer;

public class 인간대포 {
	static Point[] arr;
	static double startX, startY, endX, endY;
	static double result;
	static int n;
	static double[] checked;
	static PriorityQueue<Comb> queue = new PriorityQueue<>(new Comparator<Comb>() {
		@Override
		public int compare(Comb o1, Comb o2) {
			return o1.dist < o2.dist ? -1 : 1;
		}
	});

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		startX = Double.parseDouble(st.nextToken());
		startY = Double.parseDouble(st.nextToken());

		st = new StringTokenizer(br.readLine());
		endX = Double.parseDouble(st.nextToken());
		endY = Double.parseDouble(st.nextToken());

		n = Integer.parseInt(br.readLine());
		
		Point startPoint = new Point(0, startX, startY);
		Point endPoint = new Point(n + 1, endX, endY);
		
		arr = new Point[n + 2];
		checked = new double[n + 2];
		arr[0] = startPoint;
		arr[n + 1] = endPoint;
		for (int i = 1; i < n + 1; i++) {
			st = new StringTokenizer(br.readLine());
			arr[i] = new Point(i, Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()));
		}

		// 두가지 선을 잇는 거리 저장하기
		// 두가지 선 뽑기
		// 거리 측정하기
		for (int i = 0; i < arr.length - 1; i++) {
			combination(i + 1, arr[i]);
		}

		goToEnd();

	}

	static void goToEnd() {
		Comb comb = queue.poll();
		// 출발지 가장 가까운곳으로 출발하기
		double time = calcTime(comb);
		checked[comb.b.index] += time;
		// 나머지는 목적지 도달할 때 까지 수행하기
		while (!queue.isEmpty()) {
			Comb data = queue.poll();

			time = calcTime(data);
			if (checked[data.b.index] == 0) {
				// 첫번째 걸리는게 시간 가장 적게 걸리는것
				checked[data.b.index] += time;
			} else {
				if (checked[data.b.index] > checked[data.a.index] + time) {
					checked[data.b.index] = checked[data.a.index] + time;
				}
			}
		}
		System.out.println(checked[n + 1]);
	}

	static double calcTime(Comb comb) {
		double time = 0.0;
		if (comb.a.index != 0 && comb.a.index != n + 1) {
			// 대포인경우 50m 발사 = 2초
			time = 2;
			comb.dist -= 50;
			time += Math.abs(comb.dist) / 5.0;
		} else {
			time = comb.dist / 5.0;
		}
		return time;
	}

	static void combination(int index, Point point) {
		for (int i = index; i < arr.length; i++) {
			double dist = Math
					.sqrt(Math.pow(Math.abs(point.x - arr[i].x), 2) + Math.pow(Math.abs(point.y - arr[i].y), 2));
			queue.add(new Comb(point, arr[i], dist));
		}
	}

	static class Point {
		int index;
		double x;
		double y;

		public Point(int index, double x, double y) {
			super();
			this.index = index;
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "Point [index=" + index + ", x=" + x + ", y=" + y + "]";
		}

	}

	static class Comb {
		Point a;
		Point b;
		double dist;

		public Comb(Point a, Point b, double dist) {
			super();
			this.a = a;
			this.b = b;
			this.dist = dist;
		}

		@Override
		public String toString() {
			return "Comb [a=" + a + ", b=" + b + ", dist=" + dist + "]";
		}

	}
}
