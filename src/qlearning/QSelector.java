package qlearning;

import java.util.ArrayList;
import java.util.Random;

public class QSelector {

	private double epsilon;
	private double gamma;
	private double alpha;
	
	private Random rand;
	
	public QSelector() {
		this.init(.5, .9, .1);
	}
	
	public QSelector(double alpha, double gamma, double epsilon) {
		this.init(alpha, gamma, epsilon);
	}
	
	private void init(double alpha, double gamma, double epsilon) {
		this.gamma = gamma;
		this.epsilon = epsilon;
		this.alpha = alpha;
		this.rand = new Random();
	}
	
	
	public QValue select(Iterable<QValue> values, double currentWeight) {
		
		class Tuple{
			public QValue state;
			public double value;
			public Tuple(QValue state,double value) {
				this.state = state;
				this.value = value;
			}
		}
		
		ArrayList<Tuple> possibleValues = new ArrayList<Tuple>();
		
		for(QValue value : values) {
			double
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
		return new QSelector(this.alpha,this.gamma,this.epsilon);
	}
	
	
	
	///TODO max state value
	private double calculate(double stateWeight, double currentWeight) {
		return (1-this.alpha)*currentWeight 
				+ this.alpha*(stateWeight + this.epsilon * new Double(0));
	}

}
