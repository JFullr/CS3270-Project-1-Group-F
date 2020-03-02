package project;

import java.util.ArrayList;

import qlearning.QValue;

public class Main {
	
	public static final int TRANING_ROUNDS = 10000;

	public static void main(String[] args) {
		
		MarsRover rover = new MarsRover("MarsTerrainMap1.csv");
		ArrayList<ArrayList<QValue>> paths = rover.train(TRANING_ROUNDS);
		
		int start = paths.size()-5 < 0 ? 0 : paths.size()-5;
		
		for(int i = start; i < paths.size(); i++) {
			System.out.println(paths.get(i));
		}
		
		System.out.println(rover.getLearnedMemory());
		System.out.println();
		System.out.println(rover.getTrainingMap());
		
	}

}
