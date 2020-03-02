package project;

import java.util.ArrayList;

import project.agent.MarsAgent;
import project.environment.MarsMap;
import qlearning.QValue;

public class MarsRover {
	
	private MarsMap map;
	private MarsAgent agent;
	
	public MarsRover(String csvMapFilePath) {
		
		this.map = new MarsMap(csvMapFilePath);
		this.agent = new MarsAgent(this.map);
		
	}
	
	public ArrayList<ArrayList<QValue>> train(int rounds) {
		
		ArrayList<ArrayList<QValue>> paths = new ArrayList<ArrayList<QValue>>();
		
		for(int i = 0; i < rounds; i++) {
			this.traverse();
			ArrayList<QValue> newPath = new ArrayList<QValue>();
			newPath.addAll(this.agent.getPath());
			paths.add(newPath);
		}
		
		return paths;
		
	}
	
	private void traverse() {
		this.agent.traverse();
	}
	
	public String getLearnedMemory() {
		return this.agent.getMemoryMap();
	}
	
	public String getTrainingMap() {
		return this.map.getDisplayMap();
	}
	
}
