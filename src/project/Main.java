package project;

import java.util.ArrayList;

import qlearning.QValue;

public class Main {
	
	public static final int TRANING_ROUNDS = 10;

	public static void main(String[] args) {
		
		MarsRover rover = new MarsRover("MarsTerrainMap1.csv");
		ArrayList<ArrayList<QValue>> paths = rover.train(TRANING_ROUNDS);
		
		for(ArrayList<QValue> path : paths) {
			System.out.println(path);
		}
		
	}

}
