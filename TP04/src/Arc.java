
public class Arc {
	private int origine;
	private int destination;
	private int distance;

	public Arc(int origine, int destination, int distance) {
		super();
		this.origine = origine;
		this.destination = destination;
		this.distance = distance;
	}

	public int getOrigine() {
		return origine;
	}

	public int getDestination() {
		return destination;
	}

	public int getDistance() {
		return distance;
	}

	public void setOrigine(int origine) {
		this.origine = origine;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
}
