package qlearning;

import java.util.ArrayList;
import java.util.Random;

public class QSelector {

	private double epsilon;
	private double gamma;
	private double alpha;

	private QMemory memory;

	public QSelector(QMemory memory) {
		this.init(.5, .9, .1, memory);
	}

	public QSelector(double alpha, double gamma, double epsilon, QMemory memory) {
		this.init(alpha, gamma, epsilon, memory);
	}

	private void init(double alpha, double gamma, double epsilon, QMemory memory) {
		this.gamma = gamma;
		this.epsilon = epsilon;
		this.alpha = alpha;
		this.memory = memory;
	}

	public QTuple select(Iterable<QValue> nextStates, double currentWeight) {

		ArrayList<QTuple> possibleValues = new ArrayList<QTuple>();

		for (QValue value : nextStates) {
			if (value.getPosition() != null) {
				double calc = this.calculate(this.memory.getWeight(value.getPosition()), value.getWeight(),
						currentWeight);
				possibleValues.add(new QTuple(value, calc));
			}
		}

		// no states given, or all illegal states
		if (possibleValues.size() < 1) {
			return null;
		}

		ArrayList<QTuple> memonizedValues = new ArrayList<QTuple>();
		for (QTuple pos : possibleValues) {
			memonizedValues.add(new QTuple(pos.getState(), this.memory.getWeight(pos.getState().getPosition())));
		}

		// FIXME check for correctness
		if (Math.random() <= this.epsilon) {

			int tuple = (int) Math.floor(Math.random() * possibleValues.size());
			
			if(this.memory.getWeight(possibleValues.get(tuple).getState().getPosition()) < possibleValues.get(tuple).getWeight()) {
				this.memory.setWeight(possibleValues.get(tuple).getState().getPosition(),
					possibleValues.get(tuple).getWeight());
			}
			
			return possibleValues.get(tuple);

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
			
			if(this.memory.getWeight(possibleValues.get(tuple).getState().getPosition()) < possibleValues.get(tuple).getWeight()) {
				this.memory.setWeight(possibleValues.get(tuple).getState().getPosition(),
					possibleValues.get(tuple).getWeight());
			}
			
			return possibleValues.get(tuple);
		}

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
		return new QSelector(this.alpha, this.gamma, this.epsilon, this.memory);
	}

	/// TODO max state value
	private double calculate(double memoryWeight, double mapStateWeight, double currentTravelWeight) {
		return (1 - this.alpha) * mapStateWeight + this.alpha * (currentTravelWeight + this.gamma * memoryWeight);
	}

}
