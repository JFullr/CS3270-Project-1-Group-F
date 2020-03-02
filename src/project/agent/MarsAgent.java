package project.agent;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

import project.environment.MarsMap;
import project.environment.MarsTile;
import qlearning.QMemory;
import qlearning.QSelector;

public class MarsAgent {
	
	private static final double END_STATE = -150;
	
	private ArrayList<MarsTile> traversed;
	private MarsMap map;
	
	private QSelector selector;
	private QMemory memory;
	
	public MarsAgent(MarsMap map) {
		this.traversed = new ArrayList<MarsTile>();
		this.map = map;
		this.memory = new QMemory(this.map.getWidth(), this.map.getHeight());
		this.selector = new QSelector(this.memory);
	}
	
	public ArrayList<MarsTile> getPath(){
		return this.traversed;
	}
	
	public void traverse() {
		
		this.traversed.clear();
		
		MarsTile currentState = this.map.getStartTile();
		double currentWeight = 0;
		QSelector sel = this.selector.makeCopy();
		
		///TODO implement loop and make sure it's correct
		do{
			currentState = (MarsTile)sel.select(Arrays.asList(this.getNeighborStates(currentState)), currentWeight);
			//.makeCopy()
			this.traversed.add(null);
		}while(currentWeight > END_STATE);
		
	}
	
	private MarsTile[] getNeighborStates(MarsTile tile) {
		return new MarsTile[] {
			this.getNorthState(tile),
			this.getEastState(tile),
			this.getSouthState(tile),
			this.getWestState(tile)
		};
	}
	
	private MarsTile getCenterState(MarsTile tile) {
		Point pos = tile.getPosition();
		return this.map.getState(pos.x, pos.y);
	}
	private MarsTile getNorthState(MarsTile tile) {
		Point pos = tile.getPosition();
		return this.map.getState(pos.x, pos.y-1);
	}
	private MarsTile getSouthState(MarsTile tile) {
		Point pos = tile.getPosition();
		return this.map.getState(pos.x, pos.y+1);
	}
	private MarsTile getEastState(MarsTile tile) {
		Point pos = tile.getPosition();
		return this.map.getState(pos.x+1, pos.y);
	}
	private MarsTile getWestState(MarsTile tile) {
		Point pos = tile.getPosition();
		return this.map.getState(pos.x-1, pos.y);
	}

}
