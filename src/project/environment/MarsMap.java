package project.environment;

import java.awt.Point;
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
			int width = dims[1];
			int height = dims[0];
			
			int realHeight = (csvValues.length-1)/width;
			
			for(int y = 0; y < realHeight; y++) {
				for(int x = 0; x < width; x++) {
					this.addTile(x, y, csvValues[y*width+x-1]);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addTile(int x, int y, String raw) {
		while(this.tiles.size() < y) {
			this.tiles.add(new ArrayList<MarsTile>());
		}
		
		Point position = new Point(x,y);
		
		this.tiles.get(y).add(new MarsTile(position))
	}
	
	private int[] getDimensions(String rawValues) throws NumberFormatException{
		
		String[] rawDims = rawValues.toLowerCase().split("x");
		
		int[] dimensions = new int[2];
		
		int width = Integer.parseInt(rawDims[0].trim());
		int height = Integer.parseInt(rawDims[1].trim());
		
		return dimensions;
	}

}
