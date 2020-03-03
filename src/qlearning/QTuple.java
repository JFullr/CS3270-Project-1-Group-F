package qlearning;

public class QTuple {

	private QValue state;
	private double weight;

	public QTuple(QValue state, double weight) {
		this.state = state;
		this.weight = weight;
	}

	public QValue getState() {
		return state;
	}

	public double getWeight() {
		return weight;
	}

}
