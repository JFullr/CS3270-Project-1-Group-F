package project;

import java.util.ArrayList;

import project.agent.MarsAgent;
import project.environment.MarsMap;
import qlearning.QValue;

public class MarsRover {

	private static final double DEFAULT_EPSILON = .1;
	private static final double DEFAULT_GAMMA = .9;
	private static final double DEFAULT_ALPHA = .5;

	private MarsMap map;
	private MarsAgent agent;

	public MarsRover(String csvMapFilePath) {

		this.map = new MarsMap(csvMapFilePath);
		this.agent = new MarsAgent(this.map);

	}

	public ArrayList<ArrayList<QValue>> train(int rounds) {
		return this.train(rounds, DEFAULT_ALPHA, DEFAULT_GAMMA, DEFAULT_EPSILON);
	}

	public ArrayList<ArrayList<QValue>> train(int rounds, double alpha, double gamma, double epsilon) {

		ArrayList<ArrayList<QValue>> paths = new ArrayList<ArrayList<QValue>>();

		double decrement = this.agent.getEpsilon() / rounds;

		for (int i = 0; i < rounds; i++) {

			this.traverse(alpha, gamma, epsilon);

			ArrayList<QValue> newPath = new ArrayList<QValue>();
			newPath.addAll(this.agent.getPath());
			paths.add(newPath);

			this.agent.setEpsilon(this.agent.getEpsilon() - decrement);
			if (i + 2 >= rounds) {
				this.agent.setEpsilon(0);
			}
		}

		return paths;

	}

	private void traverse(double alpha, double gamma, double epsilon) {
		this.agent.traverse(epsilon, gamma, alpha);
	}

	public String getLearnedMemory() {
		return this.agent.getMemoryMap();
	}

	public String getTrainingMap() {
		return this.map.getDisplayMap();
	}

}
