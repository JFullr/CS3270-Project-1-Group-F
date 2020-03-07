package qlearning;

import java.util.HashMap;
import java.util.Set;

/**
 * The Class QMemory.
 * 
 * @author Amelia Reynolds, Joseph Fuller, Kyle Riggs, Timothy Brooks
 * @version Spring 2020
 */
public class QMemory {

	private HashMap<QValue, HashMap<QValue, Double>> valueMap;

	/**
	 * Instantiates a new q memory.
	 */
	public QMemory() {
		this.valueMap = new HashMap<QValue, HashMap<QValue, Double>>();
	}

	/**
	 * Sets the weight.
	 *
	 * @param start the start
	 * @param next the next
	 * @param weight the weight
	 */
	public void setWeight(QValue start, QValue next, double weight) {
		this.tetherState(start, next);
		this.valueMap.get(start).put(next, weight);
	}

	/**
	 * Gets the weight.
	 *
	 * @param start the start
	 * @param next the next
	 * @return the weight
	 */
	public double getWeight(QValue start, QValue next) {
		this.tetherState(start, next);
		return this.valueMap.get(start).get(next);
	}

	/**
	 * Gets the weights of.
	 *
	 * @param state the state
	 * @return the weights of
	 */
	public HashMap<QValue, Double> getWeightsOf(QValue state) {
		if (!this.valueMap.containsKey(state)) {
			this.valueMap.put(state, new HashMap<QValue, Double>());
		}
		return this.valueMap.get(state);
	}

	/**
	 * Gets the keys.
	 *
	 * @return the keys
	 */
	public Set<QValue> getKeys() {
		return this.valueMap.keySet();
	}

	private void tetherState(QValue start, QValue next) {
		if (start == null || next == null) {
			throw new IllegalArgumentException("ILLEGAL KEY");
		}
		if (!this.valueMap.containsKey(start)) {
			this.valueMap.put(start, new HashMap<QValue, Double>());
			this.valueMap.get(start).put(next, 0.0);
		} else if (!this.valueMap.get(start).containsKey(next)) {
			this.valueMap.get(start).put(next, 0.0);
		}
	}

}
