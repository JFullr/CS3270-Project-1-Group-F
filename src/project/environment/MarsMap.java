package project.environment;

import java.io.IOException;
import java.util.ArrayList;

import utils.FileUtils;

public class MarsMap {
	
	private ArrayList<ArrayList<MarsTile>> tiles;
	
	public MarsMap() {
		this.tiles = new ArrayList<ArrayList<MarsTile>>();
	}
	
	public MarsTile getState(int x, int y) {
		return this.tiles.get(y).get(x);
	}
	
	public ArrayList<MarsTile> getNeighbors(MarsTile tile) {
		return null;
	}
	
	public void createMapFromCsv(String filePath) {
		
		try {
			
			String[] csvValues = FileUtils.readCsv(filePath);
			int[] dims = this.getDimensions(csvValues[0]);
			
			for(int y = 0; y < dims[1]; y++) {
				for(int x = 0; x < dims[0]; x++) {
					this.addTile(x, y);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addTile(int x, int y) {
		this.tiles.get(index)
	}
	
	private int[] getDimensions(String rawValues) throws NumberFormatException{
		
		String[] rawDims = rawValues.toLowerCase().split("x");
		
		int[] dimensions = new int[2];
		
		int width = Integer.parseInt(rawDims[0].trim());
		int height = Integer.parseInt(rawDims[1].trim());
		
		return dimensions;
	}

}
