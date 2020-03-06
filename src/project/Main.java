package project;

import java.util.ArrayList;

import qlearning.QValue;

public class Main {

	public static final int TRANING_ROUNDS = 10_000;

	public static void main(String[] args) {

		System.out.println(-8.999999999978627 < 5.4475112231597105E-12);
		
		System.out.println(-9.999999999999439 > 3.6226329294587775E-13);
		
		//1.7272476085088775E-10
		//7.780414007214563E-10
		//5.7100924681280585E-9
		
		MarsRover rover = new MarsRover("MarsTerrainMap2.csv");
		ArrayList<ArrayList<QValue>> paths = rover.train(TRANING_ROUNDS);
		int errors = 0;
		/*while(paths.get(paths.size()-1).get(paths.get(paths.size()-1).size()-1).getWeight() < 0) {
			errors++;
			paths = rover.train(TRANING_ROUNDS);
		}*/

		System.out.println("Training Map:");
		System.out.println(rover.getTrainingMap() + System.lineSeparator());
		System.out.println("Learned Memory:");
		System.out.println(rover.getLearnedMemory() + System.lineSeparator());

		int start = paths.size() - 5 < 0 ? 0 : paths.size() - 5;

		for (int i = start; i < paths.size(); i++) {
			System.out.println(paths.get(i).size()+"::  "+paths.get(i).get(paths.get(i).size()-1));
			if(paths.get(i).get(paths.get(i).size()-1).getWeight() < 0) {
				System.out.println(paths.get(i));
			}
		}
		
		if(errors > 0) {
			System.out.println("NeededMoreTraining: "+errors);
		}
		
	}

}
