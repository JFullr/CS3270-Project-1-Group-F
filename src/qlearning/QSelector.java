package qlearning;

import java.util.ArrayList;
import java.util.HashMap;

public class QSelector {

	private double epsilon;
	private double gamma;
	private double alpha;

	private QMemory memory;
	private HashMap<QValue, Iterable<QValue>> stateMap;

	public QSelector(HashMap<QValue, Iterable<QValue>> stateMap) {
		this.init(.5, .9, .1, stateMap, null);
	}

	public QSelector(HashMap<QValue, Iterable<QValue>> stateMap, QMemory memory) {
		this.init(.5, .9, .1, stateMap, memory);
	}

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

	public QTuple select(QValue startState, double currentWeight) {

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
		if (Math.random() < this.epsilon) {

			int tuple = (int) Math.floor(Math.random() * possibleValues.size());

			nextState = possibleValues.get(tuple);

		} else {

			nextState = this.getMaxState(startState);

		}
		
		double qWeight = this.memory.getWeight(nextState, this.getMaxState(nextState));
		double reward = -1;
		double calc2 = this.calculate(qWeight, nextState.getWeight()+reward, this.memory.getWeight(startState, nextState));
		this.memory.setWeight(startState, nextState,calc2);

		return new QTuple(nextState, calc2);

	}

	public double getEpsilon() {
		return this.epsilon;
	}

	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

	public double getGamma() {
		return this.gamma;
	}

	public void setGamma(double gamma) {
		this.gamma = gamma;
	}

	public double getAlpha() {
		return this.alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public QSelector makeCopy() {
		return new QSelector(this.alpha, this.gamma, this.epsilon, this.stateMap, this.memory);
	}

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

		return values.get((int) Math.floor(Math.random() * values.size()));
	}

	private double calculate(double maxOfNext, double mapStateWeightReward, double stateActionValue) {
		return stateActionValue + alpha * (mapStateWeightReward + gamma * maxOfNext - stateActionValue);
	}

}
