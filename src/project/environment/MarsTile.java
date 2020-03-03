package project.environment;

import java.awt.Point;

import qlearning.QValue;

public class MarsTile implements QValue {

	public static final MarsTile ILLEGAL_TILE = new MarsTile(null, Double.NEGATIVE_INFINITY);

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

	public QValue makeCopy() {
		return new MarsTile(this.getPosition(), this.getWeight());
	}

	public String padDataForOutputTable(int padding) {
		return String.format("%-" + padding + "s", this.getWeight());
	}

	public String toString() {
		return "[" + this.getPosition().y + ", " + this.getPosition().x + "]: " + this.getWeight();
	}

}
