package qlearning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QSelector {

	private double epsilon;
	private double gamma;
	private double alpha;

	private QMemory memory;
	private HashMap<QValue,Iterable<QValue>> stateMap;

	public QSelector(HashMap<QValue,Iterable<QValue>> stateMap) {
		this.init(.5, .9, .1, stateMap, null);
	}
	
	public QSelector(HashMap<QValue,Iterable<QValue>> stateMap, QMemory memory) {
		this.init(.5, .9, .1, stateMap, memory);
	}
	
	public QSelector(double alpha, double gamma, double epsilon, HashMap<QValue,Iterable<QValue>> stateMap, QMemory memory) {
		this.init(alpha, gamma, epsilon, stateMap, memory);
	}

	private void init(double alpha, double gamma, double epsilon, HashMap<QValue,Iterable<QValue>> stateMap, QMemory memory) {
		this.gamma = gamma;
		this.epsilon = epsilon;
		this.alpha = alpha;
		this.memory = memory;
		if(memory == null) {
			this.memory = new QMemory();
		}else {
			this.memory = memory;
		}
		this.stateMap = stateMap;
	}

	public QTuple select(QValue positionState, double currentWeight) {

		ArrayList<QTuple> possibleValues = new ArrayList<QTuple>();
		
		for (QValue value : this.stateMap.get(positionState)) {
			if (value != null) {
				double calc = this.calculate(this.memory.getWeight(value), value.getWeight(), currentWeight);
				possibleValues.add(new QTuple(value, calc));
			}
		}

		// all illegal states, or no connecting states
		if (possibleValues.size() < 1) {
			return null;
		}
		
		ArrayList<QTuple> memonizedValues = new ArrayList<QTuple>();
		for (QTuple pos : possibleValues) {
			memonizedValues.add(new QTuple(pos.getState(), this.memory.getWeight(pos.getState())));
		}

		QTuple finalState = null;
		// FIXME check for correctness
		if (Math.random() <= this.epsilon) {

			int tuple = (int) Math.floor(Math.random() * possibleValues.size());

			finalState = possibleValues.get(tuple);

		} else {

			ArrayList<Integer> bestValues = new ArrayList<Integer>();
			bestValues.add(0);
			int best = 0;

			for (int i = 1; i < memonizedValues.size(); i++) {
				if (memonizedValues.get(i).getWeight() > memonizedValues.get(best).getWeight()) {
					bestValues.clear();
					best = i;
					bestValues.add(i);
				} else if (memonizedValues.get(i).getWeight() == memonizedValues.get(best).getWeight()) {
					bestValues.add(i);
				}
			}

			int tuple = bestValues.get((int) Math.floor(Math.random() * bestValues.size()));

			finalState = possibleValues.get(tuple);
		}
		
		double qWeight = this.getMaxQValue(this.stateMap.get(finalState.getState()));
		double calc = this.calculate(qWeight, finalState.getState().getWeight(), currentWeight);
		
		//this.memory.setWeight(finalState.getState(), finalState.getWeight());
		//this.memory.setWeight(finalState.getState(), calc);
		this.memory.setWeight(finalState.getState(), calc);
		
		return new QTuple(finalState.getState(),calc);
		
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
	
	public void setMemoryValue(QValue state, double value) {
		this.memory.getWeight(state);
	}
	public double getMemoryValue(QValue state) {
		return this.memory.getWeight(state);
	}
	
	private double getMaxQValue(Iterable<QValue> values) {
		double max = Double.NEGATIVE_INFINITY;
		for(QValue value : values) {
			if(value != null && this.memory.getWeight(value) > max) {
				max =  this.memory.getWeight(value);
			}
		}
		return max;
	}
	
	/// TODO max state value
	private double calculate(double memoryWeight, double mapStateWeight, double currentTravelWeight) {
		//return currentTravelWeight + this.alpha * (mapStateWeight + this.gamma * (memoryWeight) - currentTravelWeight);
		return (1 - this.alpha) * currentTravelWeight + this.alpha * (mapStateWeight + this.gamma * memoryWeight);
	}
	
}
