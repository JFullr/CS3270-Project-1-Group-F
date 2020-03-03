package qlearning;

import java.awt.Point;

public interface QValue {

	public Point getPosition();

	public double getWeight();

	public void setWeight(double weight);

	public QValue makeCopy();

}
