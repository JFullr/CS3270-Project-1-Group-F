package project.agent;

import java.awt.Point;
import java.util.ArrayList;

import project.environment.MarsMap;
import project.environment.MarsTile;
import qlearning.QSelector;

public class MarsAgent {
	
	private ArrayList<MarsTile> traversed;
	private MarsMap map;
	private QSelector selector;
	
	public MarsAgent(MarsMap map) {
		this.traversed = new ArrayList<MarsTile>();
		this.map = map;
		this.selector = new QSelector();
	}
	
	public ArrayList<MarsTile> getPath(){
		return this.traversed;
	}
	
	public void traverse() {
		
		this.traversed.clear();
		
		MarsTile currentState = this.map.getStartTile();
		double currentWeight = 0;
		QSelector sel = this.selector.makeCopy();
		
		///TODO implement loop
		{
			
			this.traversed.add(null);
		}
		
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
