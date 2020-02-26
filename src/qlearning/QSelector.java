package qlearning;

import java.util.ArrayList;
import java.util.Random;

public class QSelector {

	private double epsilon;
	private double gamma;
	private double alpha;
	
	private Random rand;
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
		this.rand = new Random();
		this.memory = memory;
	}
	
	
	public QValue select(Iterable<QValue> nextStates, double currentWeight) {
		
		class Tuple{
			public QValue state;
			public double weight;
			public Tuple(QValue state,double weight) {
				this.state = state;
				this.weight = weight;
			}
		}
		
		ArrayList<Tuple> possibleValues = new ArrayList<Tuple>();
		//epsilon is exploration probability
		
		for(QValue value : nextStates) {
			//TODO calculate values -- put values into tuple list
		}
		
		return null;
		
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
		return new QSelector(this.alpha,this.gamma,this.epsilon, this.memory);
	}
	
	
	
	///TODO max state value
	private double calculate(double memoryWeight, double mapStateWeight, double currentTravelWeight) {
		return (1-this.alpha)*mapStateWeight
				+ this.alpha*
					(currentTravelWeight + this.gamma * memoryWeight);
	}

}
