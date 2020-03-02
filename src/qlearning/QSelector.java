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
		
		for(QValue value : nextStates) {
			if(value.getPosition() != null) {
				double calc = 
					this.calculate(this.memory.getWeight(value.getPosition()), value.getWeight(), currentWeight);
				possibleValues.add(new Tuple(value,calc));
			}
		}
		
		//no states given, or all illegal states
		if(possibleValues.size() < 1) {
			return null;
		}
		
		
		
		//FIXME check for correctness
		if(Math.random() <= this.epsilon) {
			int tuple = (int)Math.floor(Math.random()*possibleValues.size());
			this.memory.setWeight(possibleValues.get(tuple).state.getPosition(),possibleValues.get(tuple).weight);
			return possibleValues.get(tuple).state;
		}else {
			
			ArrayList<Tuple> bestValues = new ArrayList<Tuple>();
			bestValues.add(possibleValues.get(0));
			int best = 0;
			
			for(int i = 1; i < possibleValues.size(); i++) {
				if(possibleValues.get(i).weight > possibleValues.get(best).weight) {
					bestValues.clear();
					best = i;
					bestValues.add(possibleValues.get(i));
				}else if(possibleValues.get(i).weight == possibleValues.get(best).weight) {
					bestValues.add(possibleValues.get(i));
				}
			}
			
			int tuple = (int)Math.floor(Math.random()*bestValues.size());
			this.memory.setWeight(possibleValues.get(tuple).state.getPosition(),possibleValues.get(tuple).weight);
			return possibleValues.get(tuple).state;
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
		return new QSelector(this.alpha,this.gamma,this.epsilon, this.memory);
	}
	
	
	
	///TODO max state value
	private double calculate(double memoryWeight, double mapStateWeight, double currentTravelWeight) {
		return (1-this.alpha)*mapStateWeight
				+ this.alpha*
					(currentTravelWeight + this.gamma * memoryWeight);
	}

}
