package qlearning;

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
	
	
	public QValue select(Iterable<QValue> values) {
		
		for(QValue value : values) {
			
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

}
