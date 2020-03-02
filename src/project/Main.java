package project;

public class Main {
	
	public static final int TRANING_ROUNDS = 10;

	public static void main(String[] args) {
		
		MarsRover rover = new MarsRover("MarsTerrainMap1.csv");
		rover.train(TRANING_ROUNDS);
		
	}

}
