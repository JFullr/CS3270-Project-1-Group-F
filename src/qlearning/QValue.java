package qlearning;

import java.awt.Point;

/**
 * The Interface QValue.
 * 
 * @author Amelia Reynolds, Joseph Fuller, Kyle Riggs, Timothy Brooks
 * @version Spring 2020
 */
public interface QValue {

	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	Point getPosition();

	/**
	 * Gets the weight.
	 *
	 * @return the weight
	 */
	double getWeight();

	/**
	 * Sets the weight.
	 *
	 * @param weight the new weight
	 */
	void setWeight(double weight);

	/**
	 * Make copy.
	 *
	 * @return the q value
	 */
	QValue makeCopy();

}
