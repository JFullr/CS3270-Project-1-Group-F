package project;

import java.util.ArrayList;

import project.agent.MarsAgent;
import project.environment.MarsMap;
import qlearning.QValue;

/**
 * The Class MarsRover.
 * 
 * @author Amelia Reynolds, Joseph Fuller, Kyle Riggs, Timothy Brooks
 * @version Spring 2020
 */
public class MarsRover {

	private static final double DEFAULT_EPSILON = .1;
	private static final double DEFAULT_GAMMA = .9;
	private static final double DEFAULT_ALPHA = .5;

	private MarsMap map;
	private MarsAgent agent;

	/**
	 * Instantiates a new mars rover.
	 *
	 * @param csvMapFilePath the csv map file path
	 */
	public MarsRover(String csvMapFilePath) {

		this.map = new MarsMap(csvMapFilePath);
		this.agent = new MarsAgent(this.map);

	}

	/**
	 * Trains the agent with the given rounds.
	 *
	 * @param rounds the rounds
	 * @return the array list
	 */
	public ArrayList<ArrayList<QValue>> train(int rounds) {
		return this.train(rounds, DEFAULT_ALPHA, DEFAULT_GAMMA, DEFAULT_EPSILON);
	}
	
	/**
	 * Prints the QTable
	 */
	public void printQTable() {
		this.agent.printQLearnedWeights();
	}
	
	/**
	 * Gets the q value.
	 *
	 * @param start the starting state
	 * @param next the value the starting state will go into
	 * @return the q value associated with the state action pair
	 */
	public Double getQValue(QValue start, QValue next) {
		return this.agent.getQValue(start, next);
	}

	/**
	 * Trains the agent with the given rounds, alpha value, gamma value, and epsilon value.
	 *
	 * @param rounds the rounds
	 * @param alpha the alpha
	 * @param gamma the gamma
	 * @param epsilon the epsilon
	 * @return the array list
	 */
	public ArrayList<ArrayList<QValue>> train(int rounds, double alpha, double gamma, double epsilon) {

		ArrayList<ArrayList<QValue>> paths = new ArrayList<ArrayList<QValue>>();

		//double decrement = this.agent.getEpsilon() / rounds;

		for (int i = 0; i < rounds; i++) {

			this.traverse(alpha, gamma, epsilon);

			ArrayList<QValue> newPath = new ArrayList<QValue>();
			newPath.addAll(this.agent.getPath());
			paths.add(newPath);

			//this.agent.setEpsilon(this.agent.getEpsilon() - decrement);
		}

		return paths;

	}
	
	/**
	 * Gets the learned memory of the agent.
	 *
	 * @return the learned memory
	 */
	public String getLearnedMemory() {
		return this.agent.getMemoryMap();
	}

	/**
	 * Gets the training map for the agent.
	 *
	 * @return the training map
	 */
	public String getTrainingMap() {
		return this.map.getDisplayMap();
	}
	
	private void traverse(double alpha, double gamma, double epsilon) {
		this.agent.traverse(epsilon, gamma, alpha);
	}

}
