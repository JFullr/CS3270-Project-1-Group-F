package qlearning;

import java.awt.Point;

public class QMemory {

	private QValue[][] weights;

	public QMemory(int width, int height) {

		this.weights = new QValue[width][height];
		for (int x = 0; x < this.weights.length; x++) {
			for (int y = 0; y < this.weights[x].length; y++) {
				this.weights[x][y] = this.makeQValue();
			}
		}

	}

	public boolean setWeight(int x, int y, double weight) {
		try {
			this.weights[x][y].setWeight(weight);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public double getWeight(int x, int y) {
		try {
			return this.weights[x][y].getWeight();
		} catch (Exception e) {
			return Double.NEGATIVE_INFINITY;
		}
	}

	public boolean setWeight(Point pos, double weight) {
		return this.setWeight(pos.x, pos.y, weight);
	}

	public double getWeight(Point pos) {
		return this.getWeight(pos.x, pos.y);
	}

	private QValue makeQValue() {
		return new QValue() {
			private double weight = 0;

			public double getWeight() {
				return this.weight;
			}

			public void setWeight(double weight) {
				this.weight = weight;
			}

			public Point getPosition() {
				return null;
			}

			public QValue makeCopy() {
				return null;
			}
		};
	}

}
