package project.agent;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import project.environment.MarsMap;
import project.environment.MarsTile;
import qlearning.QSelector;
import qlearning.QTuple;
import qlearning.QValue;

public class MarsAgent {
	
	private static final double FAIL_STATE = -1;
	private static final double SUCCESS_STATE = 100;

	private ArrayList<MarsTile> traversed;
	private MarsMap map;

	private QSelector selector;
	private HashMap<QValue, Iterable<QValue>> validStates;

	public MarsAgent(MarsMap map) {
		this.traversed = new ArrayList<MarsTile>();
		this.map = map;
		this.validStates = this.initStateMap();
		this.selector = new QSelector(this.validStates);
	}

	public ArrayList<MarsTile> getPath() {
		return this.traversed;
	}
	
	public void traverse(double epsilon, double gamma, double alpha) {

		this.traversed.clear();

		MarsTile currentState = this.map.getStartTile();
		double currentWeight = 0;
		double mapState = 0;
		QSelector sel = this.selector.makeCopy();
		sel.setAlpha(alpha);
		sel.setEpsilon(epsilon);
		sel.setGamma(gamma);
		
		/// TODO implement loop and make sure it's correct
		this.traversed.add(currentState);
		do {
			

			QTuple intermediateState = sel.select(currentState, currentWeight);
			
			currentState = (MarsTile) intermediateState.getState();
			currentWeight = intermediateState.getWeight();
			
			this.traversed.add(currentState);

			mapState = this.map.getState(currentState.getPosition()).getWeight();
		} while (mapState < SUCCESS_STATE && mapState > FAIL_STATE);
		
		if(! (mapState > FAIL_STATE)) {
			//sel.select(currentState, currentWeight);
		}

	}
	
	public double getEpsilon() {
		return this.selector.getEpsilon();
	}
	public void setEpsilon(double value) {
		this.selector.setEpsilon(value);
	}

	

	public MarsTile[] getNeighborStates(MarsTile tile) {
		return new MarsTile[] { this.getNorthState(tile), this.getEastState(tile), this.getSouthState(tile),
				this.getWestState(tile) };
	}

	public MarsTile getNorthState(MarsTile tile) {
		Point pos = tile.getPosition();
		return this.map.getState(pos.x, pos.y - 1);
	}

	public MarsTile getSouthState(MarsTile tile) {
		Point pos = tile.getPosition();
		return this.map.getState(pos.x, pos.y + 1);
	}

	public MarsTile getEastState(MarsTile tile) {
		Point pos = tile.getPosition();
		return this.map.getState(pos.x + 1, pos.y);
	}

	public MarsTile getWestState(MarsTile tile) {
		Point pos = tile.getPosition();
		return this.map.getState(pos.x - 1, pos.y);
	}

	public String getMemoryMap() {

		StringBuilder build = new StringBuilder();
		build.append("[");
		//*
		for (int y = 0; y < this.map.getHeight(); y++) {
			for (int x = 0; x < this.map.getWidth(); x++) {
				build.append(this.selector.getMemoryValue(this.map.getState(x, y)));
				if (x != this.map.getWidth() - 1) {
					build.append(", ");
				}
			}

			if (y != this.map.getHeight() - 1) {
				build.append("\n");
			}
		}
		/*/
		for(QValue value : this.selector.getMemoryKeys(null)) {
			
		}
		build.append( this.selector.getMemoryKeys(null));build.append("\n");
		build.append(this.map.getState(4, 0));
		//*/
		
		return build.toString();
	}
	
	private HashMap<QValue, Iterable<QValue>> initStateMap() {

		HashMap<QValue, Iterable<QValue>> stateMap = new HashMap<QValue, Iterable<QValue>>();

		for (int x = 0; x < this.map.getWidth(); x++) {
			for (int y = 0; y < this.map.getHeight(); y++) {
				MarsTile state = this.map.getState(x, y);
				List<QValue> list = Arrays.asList(this.getNeighborStates(state));
				stateMap.put(state,list);
			}
		}
		
		return stateMap;

	}

}
