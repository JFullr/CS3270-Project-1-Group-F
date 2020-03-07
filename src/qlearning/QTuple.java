package qlearning;

/**
 * The Class QTuple.
 * 
 * @author Amelia Reynolds, Joseph Fuller, Kyle Riggs, Timothy Brooks
 * @version Spring 2020
 */
public class QTuple {

	private QValue state;
	private double weight;

	/**
	 * Instantiates a new q tuple.
	 *
	 * @param state the state
	 * @param weight the weight
	 */
	public QTuple(QValue state, double weight) {
		this.state = state;
		this.weight = weight;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public QValue getState() {
		return this.state;
	}

	/**
	 * Gets the weight.
	 *
	 * @return the weight
	 */
	public Double getWeight() {
		return this.weight;
	}

}
