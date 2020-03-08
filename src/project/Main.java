package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
		
		/*
		MarsRover rover = new MarsRover("MarsTerrainMap1.csv");
		ArrayList<ArrayList<QValue>> paths = rover.train(TRANING_ROUNDS);
		
		rover.printQTable();

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
		
		/*/
		
		//*
			double count = 0;
			for(int i = 0; i < 100; i++) {
			MarsRover rover = new MarsRover("MarsTerrainMap1.csv");
			
			//83
			//128
			ArrayList<ArrayList<QValue>> paths = rover.train(150,0.5,0.9,0.1);
			int rounds = 0;
			for(ArrayList<QValue> path : paths) {
				rounds++;
				if(path.size()==20 && path.get(19).getWeight()==100) {
					break;
				}
			}
			System.out.println(rounds);
			count+=rounds;
			}
			System.out.println("AVG: "+count/100);
		/*/
		
		long start = System.currentTimeMillis(), interim = start;
		long highest = 0;
		while(System.currentTimeMillis() - start < 60000) {
			
			MarsRover rover = new MarsRover("MarsTerrainMap1.csv");
			//55
			//109
			ArrayList<ArrayList<QValue>> paths = rover.train(1150,0.5,0.9,.1);
			
			int rounds = 0;
			for(ArrayList<QValue> path : paths) {
				rounds++;
				if(path.size()==20 && path.get(19).getWeight()==100) {
					break;
				}
			}
			if(rounds >=1150) {
				System.out.println("CRITICAL END: >= 150");
				continue;
			}
			
			if(rounds > highest) {
				highest = rounds;
			}
			
			if(System.currentTimeMillis() - interim > 999) {
				System.out.println(highest);
				interim = System.currentTimeMillis();
			}
			
		}
		
		
		//*/
		
		
		/*
		try {
			ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(new File("training.save")));
			o.writeObject(paths);
			o.flush();
			o.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/

	}
	
	private static void question4() {
		/*
			double count = 0;
			for(int i = 0; i < 100; i++) {
			MarsRover rover = new MarsRover("MarsTerrainMap1.csv");
			
			//70
			ArrayList<ArrayList<QValue>> paths = rover.train(150,0.5,0.9,0);
			int rounds = 0;
			for(ArrayList<QValue> path : paths) {
				rounds++;
				if(path.size()==20 && path.get(19).getWeight()==100) {
					break;
				}
			}
			System.out.println(rounds);
			count+=rounds;
			}
			System.out.println("AVG: "+count/100);
		*/
		
		long start = System.currentTimeMillis(), interim = start;
		long highest = 0;
		while(System.currentTimeMillis() - start < 60000) {
			
			MarsRover rover = new MarsRover("MarsTerrainMap1.csv");
			//55
			//109
			ArrayList<ArrayList<QValue>> paths = rover.train(150,0.5,0.9,0);
			
			int rounds = 0;
			for(ArrayList<QValue> path : paths) {
				rounds++;
				if(path.size()==20 && path.get(19).getWeight()==100) {
					break;
				}
			}
			if(rounds >=150) {
				System.out.println("CRITICAL END: >= 150");
				return;
			}
			
			if(rounds > highest) {
				highest = rounds;
			}
			
			if(System.currentTimeMillis() - interim > 999) {
				System.out.println(highest);
				interim = System.currentTimeMillis();
			}
			
		}
	}
	
	private static void question3() {
		MarsRover rover = new MarsRover("MarsTerrainMap1.csv");
		
		ArrayList<ArrayList<QValue>> paths0 = rover.train(1,0.5,0.9,1);
		System.out.println(paths0.get(0));
		ArrayList<ArrayList<QValue>> paths1 = rover.train(1,0.5,0.9,1);
		System.out.println(paths1.get(0));
		
		ArrayList<ArrayList<QValue>> paths2 = rover.train(3,0.5,0.9,1);
		System.out.println(paths2.get(2));
		
		ArrayList<ArrayList<QValue>> paths3 = rover.train(5,0.5,0.9,1);
		System.out.println(paths3.get(4));
		
		ArrayList<ArrayList<QValue>> paths4 = rover.train(90,0.5,0.9,1);
		System.out.println(paths4.get(89));
		
		ArrayList<ArrayList<QValue>> paths5 = rover.train(9900,0.5,0.9,1);
		System.out.println(paths5.get(9899));
	}
	
	private static void question2() {
		MarsRover rover = new MarsRover("MarsTerrainMap1.csv");
		
		ArrayList<ArrayList<QValue>> paths = rover.train(10000,0.5,0.9,0.1);
		int rounds = 0;
		for(ArrayList<QValue> path : paths) {
			rounds++;
			if(path.size()==20 && path.get(19).getWeight()==100) {
				break;
			}
		}
		System.out.println(rounds);
	}
	
	private static void question1() {
		MarsRover rover = new MarsRover("MarsTerrainMap1.csv");
		
		ArrayList<ArrayList<QValue>> paths0 = rover.train(1);
		Double weight0 = rover.getQValue(paths0.get(0).get(0), paths0.get(0).get(1));
		
		
		
		ArrayList<ArrayList<QValue>> paths1 = rover.train(1);
		Double weight1 = rover.getQValue(paths0.get(0).get(0), paths0.get(0).get(1));
		
		System.out.println(weight0);
		System.out.println(weight1);
		
		System.out.println(paths0.get(0));
		System.out.println(paths1.get(0));
	}

}
