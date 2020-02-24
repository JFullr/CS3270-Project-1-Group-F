package project.environment;

import java.awt.Point;

public class MarsTile {
	
	public static final MarsTile ILLEGAL_TILE = new MarsTile(null,Double.NEGATIVE_INFINITY);
	
	private double weight;
	
	private Point position;
	
	public MarsTile(Point position) {
		this.weight = 0;
		this.position = position;
	}
	
	public MarsTile(Point position, double weight) {
		this.weight = weight;
		this.position = position;
	}

	public Point getPosition() {
		return this.position;
	}

	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
}
