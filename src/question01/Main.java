package question01;

public class Main {

	public static void main(String[] args) {

		int h = 20;
		int l = 25;

		Point points[] = new Point[7];
		
		points[0] = new Point(0,0);
		points[1] = new Point(2,5);
		points[2] = new Point(5,17);
		points[3] = new Point(11,4);
		points[4] = new Point(16,6);
		points[5] = new Point(20,1);
		points[6] = new Point(l,0);

//		Point points[] = new Point[3];
//		points[0] = new Point(0, 0);
//		points[1] = new Point(2, 5);
//		points[2] = new Point(l, 0);

		System.out.println(rechMaxSurfRectQuadratique(h, points));
	}

	public static int rechMaxSurfRectCubique(int h, Point points[]) {
		int surface;
		int minH;
		int maxS = 0;

		for (int i = 0; i < points.length; i++) {
			for (int j = i; j < points.length; j++) {
				minH = h;
				for (int k = i + 1; k < j; k++) {
					if (points[k].getY() < minH)
						minH = points[k].getY();
				}

				surface = minH * (points[j].getX() - points[i].getX());
				if (maxS < surface)
					maxS = surface;
			}
		}
		return maxS;
	}

	public static int rechMaxSurfRectQuadratique(int h, Point points[]) {
		int sMax = 0;

		for (int i = 0; i < points.length; i++) {
			int minH = h;
			int surface = 0;

			for (int j = i + 1; j < points.length; j++) {
				surface = minH * (points[j].getX() - points[i].getX());
				if (minH > points[j].getY()) {
					minH = points[j].getY();
				}
			}

			if (sMax < surface) {
				sMax = surface;
			}
		}

		return sMax;
	}
}