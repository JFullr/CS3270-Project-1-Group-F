package project;

import java.util.ArrayList;

import qlearning.QValue;

/**
 * The Class Main.
 * 
 * @author Amelia Reynolds, Joseph Fuller, Kyle Riggs, Timothy Brooks
 * @version Spring 2020
 */
public class Main {

	public static final int TRANING_ROUNDS = 10_000;

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		MarsRover rover = new MarsRover("MarsTerrainMap1.csv");
		ArrayList<ArrayList<QValue>> paths = rover.train(TRANING_ROUNDS);

		System.out.println("Training Map:");
		System.out.println(rover.getTrainingMap() + System.lineSeparator());

		System.out.println("Learned Memory:");
		System.out.println(rover.getLearnedMemory() + System.lineSeparator());

		int start = paths.size() - 5 < 0 ? 0 : paths.size() - 5;
		
		System.out.println(paths.get(0));

		for (int i = start; i < paths.size(); i++) {
			System.out.println(paths.get(i).size() + "::  " + paths.get(i).get(paths.get(i).size() - 1));
			if (paths.get(i).get(paths.get(i).size() - 1).getWeight() < 0) {
				System.out.println(paths.get(i));
			}
		}
		
		rover.printQTable();

	}

}
