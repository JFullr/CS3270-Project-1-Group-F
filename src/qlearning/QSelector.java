package qlearning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * The Class QSelector.
 * 
 * @author Amelia Reynolds, Joseph Fuller, Kyle Riggs, Timothy Brooks
 * @version Spring 2020
 */
public class QSelector {
	
	private static final Random RAND = new Random();

	private double epsilon;
	private double gamma;
	private double alpha;
	private QMemory memory;
	private HashMap<QValue, Iterable<QValue>> stateMap;
	
	/**
	 * Instantiates a new q selector.
	 *
	 * @param stateMap the state map
	 */
	public QSelector(HashMap<QValue, Iterable<QValue>> stateMap) {
		this.init(.5, .9, .1, stateMap, null);
	}

	/**
	 * Instantiates a new q selector.
	 *
	 * @param stateMap the state map
	 * @param memory   the memory
	 */
	public QSelector(HashMap<QValue, Iterable<QValue>> stateMap, QMemory memory) {
		this.init(.5, .9, .1, stateMap, memory);
	}

	/**
	 * Instantiates a new q selector.
	 *
	 * @param alpha    the alpha
	 * @param gamma    the gamma
	 * @param epsilon  the epsilon
	 * @param stateMap the state map
	 * @param memory   the memory
	 */
	public QSelector(double alpha, double gamma, double epsilon, HashMap<QValue, Iterable<QValue>> stateMap,
			QMemory memory) {
		this.init(alpha, gamma, epsilon, stateMap, memory);
	}

	private void init(double alpha, double gamma, double epsilon, HashMap<QValue, Iterable<QValue>> stateMap,
			QMemory memory) {
		this.gamma = gamma;
		this.epsilon = epsilon;
		this.alpha = alpha;
		this.memory = memory;
		if (memory == null) {
			this.memory = new QMemory();

			for (QValue key : stateMap.keySet()) {
				for (QValue target : stateMap.get(key)) {
					if (key != null && target != null) {
						this.memory.setWeight(key, target, 0.0);
					}
				}
			}

		} else {
			this.memory = memory;
		}
		this.stateMap = stateMap;
	}

	/**
	 * Selects the new state using the specified state.
	 *
	 * @param startState    the start state
	 * @return the q tuple
	 */
	public QTuple select(QValue startState) {

		ArrayList<QValue> possibleValues = new ArrayList<QValue>();
		for (QValue value : this.stateMap.get(startState)) {
			if (value != null) {
				possibleValues.add(value);
			}
		}

		if (possibleValues.size() < 1) {
			return null;
		}

		QValue nextState = null;
		if (RAND.nextDouble() < this.epsilon) {

			int tuple = (int) Math.floor(RAND.nextDouble() * possibleValues.size());

			nextState = possibleValues.get(tuple);

		} else {

			nextState = this.getMaxState(startState);

		}

		double qWeight = this.memory.getWeight(nextState, this.getMaxState(nextState));
		double reward = -1;
		double calc2 = this.calculate(qWeight, nextState.getWeight() + reward,
				this.memory.getWeight(startState, nextState));
		this.memory.setWeight(startState, nextState, calc2);

		return new QTuple(nextState, calc2);

	}

	/**
	 * Gets the epsilon.
	 *
	 * @return the epsilon
	 */
	public double getEpsilon() {
		return this.epsilon;
	}

	/**
	 * Sets the epsilon.
	 *
	 * @param epsilon the new epsilon
	 */
	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

	/**
	 * Gets the gamma.
	 *
	 * @return the gamma
	 */
	public double getGamma() {
		return this.gamma;
	}

	/**
	 * Sets the gamma.
	 *
	 * @param gamma the new gamma
	 */
	public void setGamma(double gamma) {
		this.gamma = gamma;
	}

	/**
	 * Gets the alpha.
	 *
	 * @return the alpha
	 */
	public double getAlpha() {
		return this.alpha;
	}

	/**
	 * Sets the alpha.
	 *
	 * @param alpha the new alpha
	 */
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	/**
	 * Make copy.
	 *
	 * @return the q selector
	 */
	public QSelector makeCopy() {
		return new QSelector(this.alpha, this.gamma, this.epsilon, this.stateMap, this.memory);
	}

	/**
	 * Gets the memory value.
	 *
	 * @param state the state
	 * @return the memory value
	 */
	public HashMap<QValue, Double> getMemoryValue(QValue state) {
		return this.memory.getWeightsOf(state);
	}

	private QValue getMaxState(QValue state) {

		ArrayList<QValue> values = new ArrayList<QValue>();

		double max = Double.NEGATIVE_INFINITY;
		for (QValue value : this.stateMap.get(state)) {
			if (value != null) {

				if (this.memory.getWeight(state, value) > max) {
					values.clear();
					max = this.memory.getWeight(state, value);
					values.add(value);
				} else if (this.memory.getWeight(state, value) == max) {
					values.add(value);
				}

			}
		}

		return values.get((int) Math.floor(RAND.nextDouble() * values.size()));
	}

	/**
	 * Calculates the new qvalue of the state action pair.
	 *
	 * @param maxOfNext            the max Q value of the next state
	 * @param mapStateWeightReward the map state weight reward
	 * @param stateActionValue     the state action value
	 * @return the double
	 */
	private double calculate(double maxOfNext, double mapStateWeightReward, double stateActionValue) {
		return stateActionValue + this.alpha * (mapStateWeightReward + this.gamma * maxOfNext - stateActionValue);
	}

}
