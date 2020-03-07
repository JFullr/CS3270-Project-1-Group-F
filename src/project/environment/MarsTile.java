package project.environment;

import java.awt.Point;

import qlearning.QValue;

/**
 * The Class MarsTile.
 */
public class MarsTile implements QValue {

	/** The Constant ILLEGAL_TILE. */
	public static final MarsTile ILLEGAL_TILE = new MarsTile(null, Double.NEGATIVE_INFINITY);

	/** The weight. */
	private double weight;

	/** The position. */
	private Point position;

	/**
	 * Instantiates a new mars tile.
	 *
	 * @param position the position
	 */
	public MarsTile(Point position) {
		this.weight = 0;
		this.position = position;
	}

	/**
	 * Instantiates a new mars tile.
	 *
	 * @param position the position
	 * @param weight the weight
	 */
	public MarsTile(Point position, double weight) {
		this.weight = weight;
		this.position = position;
	}

	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public Point getPosition() {
		return this.position;
	}

	/**
	 * Gets the weight.
	 *
	 * @return the weight
	 */
	public double getWeight() {
		return this.weight;
	}

	/**
	 * Sets the weight.
	 *
	 * @param weight the new weight
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * Make copy.
	 *
	 * @return the q value
	 */
	public QValue makeCopy() {
		return new MarsTile(this.getPosition(), this.getWeight());
	}

	/**
	 * Pad data for output table.
	 *
	 * @param padding the padding
	 * @return the string
	 */
	public String padDataForOutputTable(int padding) {
		return String.format("%-" + padding + "s", this.getWeight());
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String toString() {
		return "[" + this.getPosition().y + ", " + this.getPosition().x + "]: " + this.getWeight();
	}

}
